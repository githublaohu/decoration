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

package com.lamp.decoration.core.result;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import com.lamp.decoration.core.DecorationContext;
import com.lamp.decoration.core.result.third.WangeditorResultObject;

/**
 * @author laohu
 */
public class ResultHandlerMethodReturnValueHandler
    implements HandlerMethodReturnValueHandler, HandlerExceptionResolver {

    private final static Logger log = LoggerFactory.getLogger(ResultHandlerMethodReturnValueHandler.class);

    private final Map<Class<?>, Method> enumInMethodMap = new ConcurrentHashMap<>();

    private final HandlerMethodReturnValueHandler handlerMethodReturnValueHandler;

    private ResultAction<Object> resultAction;

    private ResultConfig resultConfig;

    private Set<Class<?>> clazzSet = new HashSet<>();

    private Map<Method, Boolean> methodMap = new ConcurrentHashMap<>();

    public ResultHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler, ResultConfig resultConfig) {
        this.handlerMethodReturnValueHandler = handlerMethodReturnValueHandler;
        this.resultConfig = resultConfig;
        this.resultAction = resultConfig.getInjection();
        resultConfig.getResultExclude().forEach(t -> {
            try {
                this.clazzSet.add(Class.forName(t));
            } catch (Exception e) {
                throw new RuntimeException("class not found " + t + " " + e.getMessage());
            }

        });
        this.init();
    }


    private void init() {
        this.clazzSet.add(WangeditorResultObject.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest) throws Exception {
        // 从returnType ，获得类型返类型
        Object object = this.clazzSet.contains(returnType.getParameterType()) ? returnValue
            : this.actionResult(returnValue, returnType, mavContainer, webRequest);
        this.printReturn(object, returnType, mavContainer, webRequest);
        // 封装成返回对象
        handlerMethodReturnValueHandler.handleReturnValue(object, returnType, mavContainer, webRequest);
    }

    public Object actionResult(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest) {
        DecorationContext decorationContext = DecorationContext.get();
        ResultObject<String> resultObject = decorationContext.getResultObject();
        if (Objects.nonNull(resultObject)) {
            decorationContext.close();
            return resultObject;
        }

        if (Objects.isNull(returnValue)) {
            return resultAction.voidResult();
        } else if (returnValue instanceof Integer) {
            return resultAction.updateResult((Integer) returnValue);
        } else if (returnValue instanceof Long) {
            return resultAction.longResult((Long) returnValue);
        } else if (Objects.equals(returnValue.getClass(), resultAction.resultClass())) {
            // 本生就是返回对象，就不任何处理
            return resultAction.resultObject(returnValue);
        } else if (returnValue instanceof Throwable) {
            return resultAction.throwableResult((Throwable) returnValue);
        } else if (returnValue instanceof ResultObjectInterface) {
            ResultObjectInterface<Object> resultObjectInterface = (ResultObjectInterface<Object>) returnValue;
            return resultObjectInterface.getResultObject();
        } else if (returnValue.getClass().isEnum()) {
            return resultAction.enumResult(returnValue);
        }
        return resultAction.objectResult(returnValue);
    }

    private void printReturn(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest) {
        if (this.isPrint(webRequest, returnType)) {
            log.info(returnValue.toString());
        }
    }

    private boolean isPrint(NativeWebRequest webRequest, MethodParameter returnType) {
        Boolean result = this.methodMap.get(returnType.getMethod());
        if (Objects.isNull(result)) {
            result = this.doIsPrint(webRequest, returnType);
            this.methodMap.put(returnType.getMethod(), result);
        }
        return result;
    }

    private boolean doIsPrint(NativeWebRequest webRequest, MethodParameter returnType) {
        if (!this.resultConfig.getPrintUrl().isEmpty()) {
            DispatcherServletWebRequest request = (DispatcherServletWebRequest) webRequest;
            String uri = request.getRequest().getRequestURI();
            if (this.resultConfig.getPrintUrl().contains(uri)) {
                return true;
            }
        }
        if (!this.resultConfig.getPrintClasses().isEmpty()) {
            String className = Objects.requireNonNull(returnType.getMethod()).getDeclaringClass().getName();
            if (this.resultConfig.getPrintClasses().contains(className)) {
                return true;
            }
        }
        if (!this.resultConfig.getExcludeUrl().isEmpty()) {
            DispatcherServletWebRequest request = (DispatcherServletWebRequest) webRequest;
            String uri = request.getRequest().getRequestURI();
            if (this.resultConfig.getExcludeUrl().contains(uri)) {
                return false;
            }
        }
        if (!this.resultConfig.getExcludeClasses().isEmpty()) {
            String className = Objects.requireNonNull(returnType.getMethod()).getDeclaringClass().getName();
            if (this.resultConfig.getExcludeClasses().contains(className)) {
                return false;
            }
        }
        return this.resultConfig.isPrintResults();
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return handlerMethodReturnValueHandler.supportsReturnType(returnType);
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) {
        return null;
    }

    public void setResultAction(ResultAction<Object> resultAction) {
        this.resultAction = resultAction;
    }

}
