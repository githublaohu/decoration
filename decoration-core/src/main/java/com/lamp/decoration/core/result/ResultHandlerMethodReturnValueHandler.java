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

import com.lamp.decoration.core.databases.queryClauseInte.QueryClauseCentre;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ResultHandlerMethodReturnValueHandler
        implements HandlerMethodReturnValueHandler, HandlerExceptionResolver {

    private final Map<Class<?>, Method> enumInMethodMap = new ConcurrentHashMap<>();

    private final HandlerMethodReturnValueHandler handlerMethodReturnValueHandler;

    private ResultAction<Object> resultAction;

    public ResultHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler) {
        this.handlerMethodReturnValueHandler = handlerMethodReturnValueHandler;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        // 从returnType ，获得类型返类型
        Object object = Objects.isNull(resultAction)
                ? this.defaultResult(returnValue, returnType, mavContainer, webRequest)
                : this.actionResult(returnValue, returnType, mavContainer, webRequest);
        // 封装成返回对象
        handlerMethodReturnValueHandler.handleReturnValue(object, returnType, mavContainer, webRequest);
    }

    public Object actionResult(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                               NativeWebRequest webRequest) {
        if (Objects.isNull(returnValue)) {
            return resultAction.voidResult();
        } else if (returnValue instanceof Integer) {
            return resultAction.updateResult((Integer) returnValue);
        } else if (returnValue instanceof Long) {
            return resultAction.longResult((Long) returnValue);
        } else if (Objects.equals(returnValue.getClass(), resultAction.resultClass())) {// 本生就是返回对象，就不任何处理
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object defaultResult(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest) {
        // 从returnType ，获得类型返类型
        try {
            Object object = null;
            if (Objects.isNull(returnValue)) {
                // 直接返回成功
                object = ResultObject.success();
            } else if (returnValue instanceof Integer) {// 判断是基本类型，就是修改与删除
                object = ResultObject.distinguishUpdate((Integer) returnValue);
            } else if (returnValue instanceof Long) {
                object = ResultObject.success(returnValue);
            } else if (returnValue instanceof ResultObject) {// 本生就是返回对象，就不任何处理
                object = returnValue;
            } else if (returnValue instanceof Throwable) {
                Throwable throwable = (Throwable) returnValue;
                object = new ResultObject(300, "异常", throwable.getMessage());
            } else if (returnValue.getClass().isEnum()) {
                Class<?> clazz = returnValue.getClass();
                Method method = enumInMethodMap.get(clazz);
                if (Objects.isNull(method)) {
                    method = clazz.getMethod("getResultObject", new Class[0]);
                    method = enumInMethodMap.computeIfAbsent(clazz, k -> {
                        Method mewMethod = null;
                        try {
                            mewMethod = clazz.getMethod("getResultObject", new Class[0]);
                        } catch (NoSuchMethodException | SecurityException e) {
                            throw new RuntimeException(e);
                        }
                        return mewMethod;
                    });
                }
                object = method.invoke(returnValue);
            } else {// 查询操作
                if (Objects.isNull(object)) {
                    ResultObject<Object> resultObject = ResultObject.success(returnValue);
                    QueryClauseCentre.pageData(resultObject);
                    object = resultObject;
                }
            }
            return object;
        } catch (Exception e) {
            return new ResultObject(300, "异常", e.getMessage());
        }
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
