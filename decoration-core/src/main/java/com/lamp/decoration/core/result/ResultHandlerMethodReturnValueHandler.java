package com.lamp.decoration.core.result;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lamp.decoration.core.databases.queryClauseInte.QueryClauseCentre;

public class ResultHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler ,HandlerExceptionResolver {

	private HandlerMethodReturnValueHandler handlerMethodReturnValueHandler;

	public ResultHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler) {
		this.handlerMethodReturnValueHandler = handlerMethodReturnValueHandler;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest) throws Exception {
        // 从returnType ，获得类型返类型
        Object object = null;
        if(Objects.isNull(returnValue)) {
            // 直接返回成功
            object = ResultObject.success();
        }else if(object instanceof Integer) {// 判断是基本类型，就是修改与删除
            object = ResultObject.distinguishUpdate((Integer)returnValue);
        }else  if(object instanceof Long) {
            object = ResultObject.success(returnValue);
        } if(Objects.nonNull(returnValue) && object instanceof ResultObject){// 本生就是返回对象，就不任何处理
            object = returnValue;
        } else if(returnValue instanceof Throwable) {
        	Throwable throwable = (Throwable)returnValue;
        	object = new ResultObject(300 , "异常" , throwable.getMessage());
        }else{// 查询操作
        	if(Objects.isNull(object)) {
	            ResultObject<Object> resultObject = ResultObject.success(returnValue);
	            QueryClauseCentre.pageData(resultObject);
	            object = resultObject;
        	}
        }
        // 封装成返回对象
        handlerMethodReturnValueHandler.handleReturnValue(object, returnType, mavContainer, webRequest);
    }

	public boolean supportsReturnType(MethodParameter returnType) {
		return handlerMethodReturnValueHandler.supportsReturnType(returnType);
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		return null;
	}
	
	
	
}
