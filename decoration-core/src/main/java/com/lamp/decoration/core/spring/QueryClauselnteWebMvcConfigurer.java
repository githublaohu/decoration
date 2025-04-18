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

package com.lamp.decoration.core.spring;

import java.lang.reflect.Constructor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lamp.decoration.core.ConstantConfig;
import com.lamp.decoration.core.utils.SpringVersionRecognition;

/**
 * @author laohu
 */
public class QueryClauselnteWebMvcConfigurer implements WebMvcConfigurer {


    private static final Constructor<?> CONSTRUCTOR;

    static {

        String className = SpringVersionRecognition.isJakarta() ?
            "com.lamp.decoration.core.databases.queryClauseInte.JakartaQueryClauseInterceptor" :
            "com.lamp.decoration.core.databases.queryClauseInte.QueryClauseInterceptor";
        try {
            Class<?> clazz = Class.forName(className);
            CONSTRUCTOR = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        try {
            HandlerInterceptor handlerInterceptor = (HandlerInterceptor) CONSTRUCTOR.newInstance(new ConstantConfig());

            registry.addInterceptor(handlerInterceptor).addPathPatterns("/**");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
