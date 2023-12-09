/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.decoration.core.databases.mybatis;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 1. 编写期间可以使用 spring配置的内容。开发期间，可以识别 target 目录，读取文件
 * 2. 构建期间的 test，通过读取环境变量识别 构建期间
 * 3.
 * @author laohu
 */
public class TestMyBatis<T> {

    protected T mapper;

    {
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(getConfiguration());
        Class<?> clazz = getClazz();
        mapper = (T) sqlSessionFactory.openSession().getMapper(clazz);
    }

    /**
     * @param clazz
     * @param sqlSessionFactory
     * @throws ClassNotFoundException
     */
    private void testMapper(Class<?> clazz, SqlSessionFactory sqlSessionFactory) throws ClassNotFoundException {
        TestMapper testMapper = clazz.getAnnotation(TestMapper.class);
        for (String dependent : testMapper.dependent()) {
            try {
                sqlSessionFactory.openSession().getMapper(Class.forName(dependent));
            } catch (Exception e) {
                throw new RuntimeException("load mapper fail " + dependent, e);
            }
        }
        for (String script : testMapper.script()) {
            try {
                InputStream input = clazz.getClassLoader().getResourceAsStream(script);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[2048];
                int i = 0;
                while ((i = input.read(bytes)) > -1) {
                    outputStream.write(bytes, 0, i);
                }
                sqlSessionFactory.openSession().insert(outputStream.toString());
            } catch (Exception e) {
                throw new RuntimeException("load script fail " + script + "\r\n" + e.getMessage(), e);
            }
        }
    }

    /**
     * @return
     */
    private Configuration getConfiguration() {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment = new Environment("Production", transactionFactory, this.getDataSource());
        Configuration configuration = new Configuration(environment);
        configuration.setLazyLoadingEnabled(true);
        configuration.setUseActualParamName(false); // to test legacy style reference (#{0} #{1})
        configuration.setUseGeneratedKeys(true);
        configuration.setMapUnderscoreToCamelCase(true);
        return configuration;
    }

    /**
     * @return
     */
    private Class<?> getClazz() {
        Type superClass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    /**
     * @return
     */
    private DataSource getDataSource() {
        UnpooledDataSource dataSource = new UnpooledDataSource();
        dataSource.setDriver("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:.");
        // mem: 内存模式， rdrs 内存数据名字，
        //dataSource.setUrl("jdbc:hsqldb:mem:rdrs");
        return dataSource;
    }
}
