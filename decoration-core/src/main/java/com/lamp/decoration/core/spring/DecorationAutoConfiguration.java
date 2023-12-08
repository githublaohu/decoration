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

import com.lamp.decoration.core.ConstantConfig;
import com.lamp.decoration.core.duplicate.DuplicateCheck;
import com.lamp.decoration.core.duplicate.LocadDuplicateCheck;
import com.lamp.decoration.core.exception.CustomExceptionResult;
import com.lamp.decoration.core.exception.DecorationCustomExceptionResult;
import com.lamp.decoration.core.exception.ExceptionResult;
import com.lamp.decoration.core.result.DecorationResultAction;
import com.lamp.decoration.core.result.ResultAction;
import com.lamp.decoration.core.spring.plugs.DecorationCorsRegistry;
import com.lamp.decoration.core.spring.plugs.FastJsonMessageConverters;
import com.lamp.decoration.core.spring.plugs.Swagger2Configuration;
import com.lamp.decoration.core.spring.plugs.Swagger2Plugs;
import com.lamp.decoration.core.utils.SpringVersionRecognition;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author laohu
 */
@ConditionalOnProperty(prefix = DecorationProperties.DECORATION_PREFIX, name = "enabled", matchIfMissing = true)
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

    private ResultAction resultAction = new DecorationResultAction();

    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public OperationSpringMVCBehavior operationSpringMvcBehavior(DecorationProperties decorationProperties) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        ResultAction resultAction = null;
        String injection = decorationProperties.getResultObject();
        if (Objects.isNull(decorationProperties.getResultObject())) {
            resultAction = this.resultAction;
        } else {
            if (injection.charAt(0) != '$') {
                resultAction = (ResultAction) Class.forName(injection).newInstance();
            }
        }
        return new OperationSpringMVCBehavior(resultAction, injection);
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
     * @param decorationProperties
     * @return
     */
    @Bean
    public HandlerInterceptor duplicateSubmission(DecorationProperties decorationProperties) throws  Exception {
        DuplicateCheck duplicateCheck = new LocadDuplicateCheck();
        Class<?> clazz;
        if (SpringVersionRecognition.isJakarta()) {
            clazz = Class.forName("com.lamp.decoration.core.duplicate.JakartaDuplicateSubmissionHandlerInterceptor");
        } else {
            clazz = Class.forName("com.lamp.decoration.core.duplicate.DuplicateSubmissionHandlerInterceptor");
        }
        return (HandlerInterceptor) clazz.getConstructor(DuplicateCheck.class).newInstance(duplicateCheck);
    }

    @Bean
    public HandlerInterceptor queryClauselnteWebMvcConfigurer(DecorationProperties decorationProperties) throws Exception {
        String className = SpringVersionRecognition.isJakarta() ?
                "com.lamp.decoration.core.databases.queryClauseInte.JakartaQueryClauseInterceptor" :
                "com.lamp.decoration.core.databases.queryClauseInte.QueryClauseInterceptor";

        Class<?> clazz = Class.forName(className);
        return (HandlerInterceptor) clazz.getConstructor(ConstantConfig.class).newInstance(decorationProperties.getConstantConfig());
    }

    @Bean
    @ConditionalOnProperty(prefix = "decoration.swagger2Config", name = "swagger2Enable", havingValue = "true", matchIfMissing = false)
    @ConditionalOnClass(name = "springfox.documentation.spring.web.plugins.Docket")
    public Object createsSwagger(DecorationProperties decorationProperties) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?>[] params = {Swagger2Configuration.class};
        List<String> packagesList = AutoConfigurationPackages.get(beanFactory);
        decorationProperties.getSwagger2Config().getApiSelector().getPaths().addAll(packagesList);
        return SWAGGER2_CLASS.getMethod("getDocket", params).invoke(null, decorationProperties.getSwagger2Config());
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

        String className = "com.lamp.decoration.core.exception."+(SpringVersionRecognition.isJakarta()?"JakartaDecorationExceptionHandler":"DecorationExceptionHandler");
        Class<?> clazz = Class.forName(className);
        return clazz.getConstructor(ResultAction.class).newInstance(this.resultAction);
    }


    @Bean
    public FastJsonMessageConverters createFastJsonMessageConverters() {
        return new FastJsonMessageConverters();
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
