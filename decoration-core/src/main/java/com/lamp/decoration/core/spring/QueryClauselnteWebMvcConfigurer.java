package com.lamp.decoration.core.spring;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lamp.decoration.core.ConstantConfig;
import com.lamp.decoration.core.databases.queryClauseInte.QueryClauseInterceptor;

public class QueryClauselnteWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new QueryClauseInterceptor(new ConstantConfig()))
        .addPathPatterns("/**");
    }
}
