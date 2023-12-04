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

import com.lamp.decoration.core.duplicate.DuplicateCheck;
import com.lamp.decoration.core.duplicate.DuplicateSubmissionHandlerInterceptor;
import com.lamp.decoration.core.duplicate.LocadDuplicateCheck;
import com.lamp.decoration.core.exception.CustomExceptionResult;
import com.lamp.decoration.core.exception.DecorationCustomExceptionResult;
import com.lamp.decoration.core.exception.DecorationExceptionHandler;
import com.lamp.decoration.core.exception.ExceptionResult;
import com.lamp.decoration.core.result.DecorationResultAction;
import com.lamp.decoration.core.result.ResultAction;
import com.lamp.decoration.core.spring.plugs.DecorationCorsRegistry;
import com.lamp.decoration.core.spring.plugs.Swagger2Configuration;
import com.lamp.decoration.core.spring.plugs.Swagger2Plugs;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author laohu
 */
@Configuration
@ConditionalOnClass(name = {"org.springframework.web.servlet.HandlerInterceptor"})
@EnableConfigurationProperties(DecorationProperties.class)
public class DecorationAutoConfiguration {

    private static boolean IS_SWAGGER2 = false;

    private static Class<?> SWAGGER2_CLASS;

    static {
        try {
            SWAGGER2_CLASS = Swagger2Plugs.class;
            IS_SWAGGER2 = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    //AutoConfigurationPackages.get(beanFactory)
    @Autowired
    private BeanFactory beanFactory;

    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public OperationSpringMVCBehavior operationSpringMvcBehavior(DecorationProperties decorationProperties) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        ResultAction resultAction = null;
        String injection = decorationProperties.getResultObject();
        if (Objects.isNull(decorationProperties.getResultObject())) {
            resultAction = (ResultAction) new DecorationResultAction();
        } else {
            if (injection.charAt(0) != '$') {
                resultAction = (ResultAction) Class.forName(injection).newInstance();
            }
        }
        return new OperationSpringMVCBehavior(resultAction, injection);
    }

    @Bean
    public QueryClauselnteWebMvcConfigurer queryClauselnteWebMvcConfigurer() {
        return new QueryClauselnteWebMvcConfigurer();
    }

    @Bean
    @ConditionalOnProperty
    public DecorationCorsRegistry createDecorationCorsRegistry(DecorationProperties decorationProperties) {
        return new DecorationCorsRegistry(decorationProperties.isCorsEnable(),decorationProperties.getCorsConfigurationList());
    }

    @Bean
    public DuplicateSubmissionHandlerInterceptor duplicateSubmission(DecorationProperties decorationProperties) {
        DuplicateCheck duplicateCheck = new LocadDuplicateCheck();
        return new DuplicateSubmissionHandlerInterceptor(duplicateCheck);
    }

    @Bean
    @ConditionalOnProperty(prefix = "decoration", name = { "swagger2Config" })
    @ConditionalOnClass(name="springfox.documentation.spring.web.plugins.Docket")
    public Object createsSwagger(DecorationProperties decorationProperties) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?>[] params = {Swagger2Configuration.class};
        return SWAGGER2_CLASS.getMethod("getDocket", params).invoke(null, decorationProperties.getSwagger2Config());
    }




    @Bean
    public DecorationExceptionHandler decorationExceptionHandler(DecorationProperties decorationProperties)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {

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
        return new DecorationExceptionHandler(exceptionResultList, exceptionResult.getDefaultExceptionResult());
    }


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
