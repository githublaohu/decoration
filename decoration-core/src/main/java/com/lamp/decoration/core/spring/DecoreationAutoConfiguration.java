package com.lamp.decoration.core.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(name = {"org.springframework.web.servlet.HandlerInterceptor"})
@Configuration
@EnableConfigurationProperties(LampDecorationProperties.class)
public class DecoreationAutoConfiguration {

    @Bean
    public OperationSpringMVCBehavior OperationSpringMVCBehavior() {
        return new OperationSpringMVCBehavior();
    }
    

    
    @Bean
    public QueryClauselnteWebMvcConfigurer queryClauselnteWebMvcConfigurer() {
        return new QueryClauselnteWebMvcConfigurer();
    }
}
