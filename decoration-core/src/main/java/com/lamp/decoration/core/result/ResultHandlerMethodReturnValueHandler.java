package com.lamp.decoration.core.result;

import java.util.Map;
import java.util.Objects;

import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ResultHandlerMethodReturnValueHandler
    implements HandlerMethodReturnValueHandler {
    
   
    private HandlerMethodReturnValueHandler handlerMethodReturnValueHandler;
    
    public ResultHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler) {
        this.handlerMethodReturnValueHandler = handlerMethodReturnValueHandler;
    }

    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest) throws Exception {
        // 从returnType ，获得类型返类型
        Object object = null;
        if(Objects.isNull(returnValue)) {
            // 直接返回成功
            object = ResultObject.success();
        }else if(object instanceof Integer) {// 判断是基本类型，就是修改与删除
            object = ResultObject.distinguishUpdate((Integer)returnValue);
        }else if(object instanceof ResultObject){// 本生就是返回对象，就不任何处理
            object = returnValue;
        } else {// 查询操作
            object = ResultObject.success(returnValue);
        }
        // 封装成返回对象
        handlerMethodReturnValueHandler.handleReturnValue(object, returnType, mavContainer, webRequest);
    }




    public boolean supportsReturnType(MethodParameter returnType) {
        return handlerMethodReturnValueHandler.supportsReturnType(returnType);
    }
}
