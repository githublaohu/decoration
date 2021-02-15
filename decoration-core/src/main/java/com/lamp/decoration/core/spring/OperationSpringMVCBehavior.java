package com.lamp.decoration.core.spring;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.lamp.decoration.core.result.ResultHandlerMethodReturnValueHandler;

public class OperationSpringMVCBehavior   {

    @Autowired
    private RequestMappingHandlerAdapter  requestMappingHandlerAdapter;
    
    
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void into() {
        List<HandlerMethodReturnValueHandler> handlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        try {
            
            Class<?> clazz = Class.forName("java.util.Collections$UnmodifiableList");
            Field field = clazz.getDeclaredField("list");
            field.setAccessible(true);
            handlers = (List<HandlerMethodReturnValueHandler>)field.get(handlers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0 ; i < handlers.size() ; i++) {
            HandlerMethodReturnValueHandler handlerMethodReturnValueHandler = handlers.get(i);
            if(handlerMethodReturnValueHandler.getClass().equals(RequestResponseBodyMethodProcessor.class)) {
                handlers.set(i, new ResultHandlerMethodReturnValueHandler(handlerMethodReturnValueHandler));
                return;
            }
        }
    }
}
