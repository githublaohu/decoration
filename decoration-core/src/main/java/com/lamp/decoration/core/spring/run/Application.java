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
package com.lamp.decoration.core.spring.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;


/**
 * 本体系的目的
 * 1. 减少启动类代码量
 * 2. 提供服务生命周期的能力
 * 3. 支持发送到群（飞书群，企业微信群，钉钉群，或则其他群）
 * 4. 直接发送到MQ
 * 细节
 * 1. 配置如何读取。如果发现操作类。
 *  1. 从环境变量读取，
 *  2. 从启动变量读取，
 *  3. 从配置文件读取。
 *  4. 从默认地址读
 * @author tiger
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static  final void run(){
        try {
            String className = Thread.currentThread().getStackTrace()[1].getClassName();
            SpringApplication.run(Class.forName(className));
            logger.info("{} 启动成功", className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }

    }
}
