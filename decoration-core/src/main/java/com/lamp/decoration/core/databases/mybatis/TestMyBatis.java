package com.lamp.decoration.core.databases.mybatis;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.sql.DataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
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

    private Class<?> getClazz() {
        Type superClass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    private DataSource getDataSource() {
        UnpooledDataSource dataSource = new UnpooledDataSource();
        dataSource.setDriver("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:.");
        // mem: 内存模式， rdrs 内存数据名字，
        //dataSource.setUrl("jdbc:hsqldb:mem:rdrs");
        return dataSource;
    }
}
