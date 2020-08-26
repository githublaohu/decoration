package com.lamp.decoration.core;

import java.util.List;

import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import com.lamp.decoration.core.result.ResultHandlerMethodReturnValueHandler;

public class DecoreationWebMvcConfigurer implements WebMvcConfigurer {

    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for(int i = 0 ; i < handlers.size() ; i++) {
            HandlerMethodReturnValueHandler handlerMethodReturnValueHandler = handlers.get(i);
            if(handlerMethodReturnValueHandler.getClass().equals(ResponseBodyEmitterReturnValueHandler.class)) {
                handlers.set(i, new ResultHandlerMethodReturnValueHandler(handlerMethodReturnValueHandler));
            }
        }
    }
}
