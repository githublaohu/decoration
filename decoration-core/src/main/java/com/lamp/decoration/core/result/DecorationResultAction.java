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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.lamp.decoration.core.databases.queryClauseInte.QueryClauseCentre;
import com.lamp.decoration.core.exception.ExceptionResult;

/**
 * @author laohu
 */
public class DecorationResultAction implements ResultAction<ResultObject<Object>> {

    private Map<Class<?>, Method> enumInMethodMap = new ConcurrentHashMap<>();

    private Map<Class<?>, ExceptionResult> exceptionResultMap = new HashMap<>();

    private ExceptionResult defaultExceptionResult;



    @Override
    public Class<?> resultClass() {
        return ResultObject.class;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultObject success() {
        return ResultObject.success();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultObject fail() {
        return ResultObject.fail();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultObject voidResult() {
        return ResultObject.success();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultObject updateResult(Integer data) {
        return ResultObject.distinguishUpdate(data);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultObject longResult(Long data) {
        return ResultObject.success(data);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultObject objectResult(Object data) {
        ResultObject<Object> resultObject = ResultObject.success(data);
        QueryClauseCentre.pageData(resultObject);
        return resultObject;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public ResultObject throwableResult(Throwable throwable) {
        if (throwable instanceof DecorationResultException) {
            return ((DecorationResultException) throwable).getResultObject();
        }
        if (throwable instanceof MethodArgumentNotValidException) {
            FieldError fieldError = ((MethodArgumentNotValidException) throwable).getBindingResult().getFieldError();
            String errorMessage = String.format("[参数验证出错] 参数: %s  问题：<%s>", fieldError.getField(), fieldError.getDefaultMessage());
            return new ResultObject(30100, errorMessage);
        }
        if (throwable instanceof BindException) {
            FieldError fieldError = ((BindException) throwable).getFieldError();
            String errorMessage = String.format("[参数验证出错] 参数: %s  问题：<%s>", fieldError.getField(), fieldError.getDefaultMessage());
            return new ResultObject(30100, errorMessage);
        }
        ExceptionResult exceptionResult = this.exceptionResultMap.get(throwable);
        if (Objects.isNull(exceptionResult)) {
            exceptionResult = this.defaultExceptionResult;
        }
        return new ResultObject(exceptionResult.getCode(), exceptionResult.getMessage(), throwable.getMessage());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultObject enumResult(Object data) {
        if (data instanceof ResultObjectInterface) {
            return ((ResultObjectInterface<?>) data).getResultObject();
        }
        try {
            Class<?> clazz = data.getClass();
            Method method = enumInMethodMap.get(clazz);
            if (Objects.isNull(method)) {

                method = enumInMethodMap.computeIfAbsent(clazz, k -> {
                    Method mewMethod = null;
                    try {
                        mewMethod = clazz.getMethod("getResultObject", new Class[0]);
                    } catch (NoSuchMethodException | SecurityException e) {
                        new RuntimeException(e);
                    }
                    return mewMethod;
                });
            }
            return (ResultObject) method.invoke(data);
        } catch (Exception e1) {
            return this.throwableResult(e1);
        }
    }

    @Override
    public void setDefaultExceptionResult(ExceptionResult exceptionResult) {
        this.defaultExceptionResult = exceptionResult;
    }

    @Override
    public void setExceptionResultList(List<ExceptionResult> exceptionResultList) {
        for (ExceptionResult exceptionResult : exceptionResultList) {
            exceptionResultMap.put(exceptionResult.getClazz(), exceptionResult);
        }

    }


}
