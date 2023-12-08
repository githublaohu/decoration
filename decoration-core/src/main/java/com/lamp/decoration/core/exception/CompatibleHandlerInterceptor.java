package com.lamp.decoration.core.exception;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public interface CompatibleHandlerInterceptor extends HandlerExceptionResolver {

    default ModelAndView resolveException(
            javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, @Nullable Object handler, Exception ex){
        return null;
    }
}
