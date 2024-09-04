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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lamp.decoration.core.ConstantConfig;
import com.lamp.decoration.core.duplicate.DuplicateCheck;
import com.lamp.decoration.core.duplicate.LocadDuplicateCheck;
import com.lamp.decoration.core.exception.CustomExceptionResult;
import com.lamp.decoration.core.exception.DecorationCustomExceptionResult;
import com.lamp.decoration.core.exception.ExceptionResult;
import com.lamp.decoration.core.result.DecorationResultAction;
import com.lamp.decoration.core.result.ResultAction;
import com.lamp.decoration.core.result.ResultConfig;
import com.lamp.decoration.core.spring.plugs.DecorationCorsRegistry;
import com.lamp.decoration.core.spring.plugs.FastJsonMessageConverters;
import com.lamp.decoration.core.spring.plugs.Swagger2Plugs;
import com.lamp.decoration.core.spring.plugs.Swagger3Plugs;
import com.lamp.decoration.core.utils.SpringVersionRecognition;

/**
 * @author laohu
 */

@Configuration
@ConditionalOnProperty(prefix = DecorationProperties.DECORATION_PREFIX, name = "enabled", matchIfMissing = true)
@AutoConfigureBefore(name = {"org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration"})
@ConditionalOnClass(name = {"org.springframework.web.servlet.HandlerInterceptor"})
@EnableConfigurationProperties(DecorationProperties.class)
public class DecorationAutoConfiguration {

    @Autowired
    private BeanFactory beanFactory;

    private final ResultAction resultAction = new DecorationResultAction();

    /**
     * 对返回结果进行拦截
     *
     * @param decorationProperties
     * @return 1
     * @throws InstantiationException 3
     * @throws IllegalAccessException 2
     * @throws ClassNotFoundException 1
     */
    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public OperationSpringMVCBehavior operationSpringMvcBehavior(DecorationProperties decorationProperties) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        ResultAction injection = null;
        ResultConfig resultConfig = decorationProperties.getResultConfig();
        String resultAction = resultConfig.getResultAction();
        if (Objects.isNull(resultAction)) {
            injection = this.resultAction;
        } else {
            if (resultAction.charAt(0) != '$') {
                injection = (ResultAction) Class.forName(resultAction).newInstance();
            }
        }
        resultConfig.setInjection(injection);
        return new OperationSpringMVCBehavior(decorationProperties.getResultConfig());
    }


    @Bean
    @ConditionalOnProperty(prefix = "decoration", name = {"corsConfigurationList"})
    public DecorationCorsRegistry createDecorationCorsRegistry(DecorationProperties decorationProperties) {
        return new DecorationCorsRegistry(decorationProperties.isCorsEnable(), decorationProperties.getCorsConfigurationList());
    }

    @Bean
    @ConditionalOnProperty(prefix = "decoration", name = {"corsEnable"}, havingValue = "true")
    public DecorationCorsRegistry createAllDecorationCorsRegistry(DecorationProperties decorationProperties) {
        return new DecorationCorsRegistry(decorationProperties.isCorsEnable(), null);
    }

    /**
     * 限制重复请求，不需要任何配置
     *
     * @return 自定义 WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer duplicateSubmission()  {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                try {
                    DuplicateCheck duplicateCheck = new LocadDuplicateCheck();
                    Class<?> clazz;
                    if (SpringVersionRecognition.isJakarta()) {
                        clazz = Class.forName("com.lamp.decoration.core.duplicate.JakartaDuplicateSubmissionHandlerInterceptor");
                    } else {
                        clazz = Class.forName("com.lamp.decoration.core.duplicate.DuplicateSubmissionHandlerInterceptor");
                    }
                    HandlerInterceptor handlerInterceptor = (HandlerInterceptor) clazz.getConstructor(DuplicateCheck.class).newInstance(duplicateCheck);
                    registry.addInterceptor(handlerInterceptor)
                            .addPathPatterns("/**");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Bean
    public WebMvcConfigurer queryClauselnteWebMvcConfigurer(DecorationProperties decorationProperties) throws Exception {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                try {
                    String className = SpringVersionRecognition.isJakarta() ?
                            "com.lamp.decoration.core.databases.queryClauseInte.JakartaQueryClauseInterceptor" :
                            "com.lamp.decoration.core.databases.queryClauseInte.QueryClauseInterceptor";
                    Class<?> clazz = Class.forName(className);
                    HandlerInterceptor handlerInterceptor = (HandlerInterceptor) clazz.getConstructor(ConstantConfig.class).newInstance(decorationProperties.getConstantConfig());
                    registry.addInterceptor(handlerInterceptor)
                            .addPathPatterns("/**");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        };
    }

    @Bean
    @ConditionalOnProperty(prefix = "decoration.swagger2", name = "enabled", havingValue = "true", matchIfMissing = false)
    @ConditionalOnClass(name = "springfox.documentation.spring.web.plugins.Docket")
    public Object createsSwagger2(DecorationProperties decorationProperties) {
        List<String> packagesList = AutoConfigurationPackages.get(beanFactory);
        decorationProperties.getSwagger2().getApiSelector().getPaths().addAll(packagesList);
        return Swagger2Plugs.getDocket(decorationProperties.getSwagger2());
    }

    @Bean
    @ConditionalOnProperty(prefix = "decoration.swagger3", name = "enabled", havingValue = "true", matchIfMissing = false)
    @ConditionalOnClass(name = "io.swagger.v3.oas.models.OpenAPI")
    public Object createsSwagger3(DecorationProperties decorationProperties) {
        String filePath = "classpath:" + decorationProperties.getSwagger3().getFilePath();
        return Swagger3Plugs.createSwagger(filePath);
    }


    @Bean
    public Object decorationExceptionHandler(DecorationProperties decorationProperties)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {

        CustomExceptionResult exceptionResult;
        if (Objects.nonNull(decorationProperties.getDefaultExceptionResult())) {
            exceptionResult = (CustomExceptionResult) Class.forName(decorationProperties.getDefaultExceptionResult()).newInstance();
        } else {
            exceptionResult = new DecorationCustomExceptionResult();
        }

        List<ExceptionResult> exceptionResultList = new ArrayList<>();
        if (Objects.nonNull(exceptionResult.getExceptionResultList())) {
            exceptionResultList.addAll(exceptionResult.getExceptionResultList());
        }

        List<String> exceptionResultClassNameList = decorationProperties.getExceptionResult();

        if (Objects.nonNull(exceptionResultClassNameList) && !exceptionResultClassNameList.isEmpty()) {
            for (String className : exceptionResultClassNameList) {
                exceptionResult = (CustomExceptionResult) Class.forName(className).newInstance();
                if (Objects.nonNull(exceptionResult.getExceptionResultList())) {
                    exceptionResultList.addAll(exceptionResult.getExceptionResultList());
                }
            }
        }
        this.resultAction.setDefaultExceptionResult(exceptionResult.getDefaultExceptionResult());
        this.resultAction.setExceptionResultList(exceptionResultList);

        String className = "com.lamp.decoration.core.exception." + (SpringVersionRecognition.isJakarta() ? "JakartaDecorationExceptionHandler" : "DecorationExceptionHandler");
        Class<?> clazz = Class.forName(className);
        return clazz.getConstructor(ResultAction.class).newInstance(this.resultAction);
    }


    @Bean
    public FastJsonMessageConverters createFastJsonMessageConverters() {
        return new FastJsonMessageConverters();
    }

    /**
     * 暂时没有实现
     * @return
     */
    @Bean
    @ConditionalOnClass(name = {"feign.RequestInterceptor"})
    public Object createRequestInterceptor() {
        try {
            Class<?> clazz = Class.forName("com.lamp.decoration.core.databases.queryClauseInte");
            return clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
