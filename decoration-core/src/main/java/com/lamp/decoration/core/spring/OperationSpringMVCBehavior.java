package com.lamp.decoration.core.spring;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.lamp.decoration.core.result.ResultAction;
import com.lamp.decoration.core.result.ResultHandlerMethodReturnValueHandler;

public class OperationSpringMVCBehavior   implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>{

	private ApplicationContext applicationContext;
	
    @Autowired
    private RequestMappingHandlerAdapter  requestMappingHandlerAdapter;
    
    private ResultHandlerMethodReturnValueHandler resultHandlerMethodReturnValueHandler;
    
    private ResultAction<Object> resultAction;
    
    private String injection;
    
    public OperationSpringMVCBehavior(ResultAction<Object> resultAction,String injection) {
    	this.resultAction = resultAction;
    	this.injection = injection;
    }
    
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
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
            	resultHandlerMethodReturnValueHandler = new ResultHandlerMethodReturnValueHandler(handlerMethodReturnValueHandler);
            	resultHandlerMethodReturnValueHandler.setResultAction(resultAction);
            	handlers.set(i, resultHandlerMethodReturnValueHandler);
                return;
            }
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(Objects.isNull(resultAction)) {
			resultAction = (ResultAction<Object>) applicationContext.getBean(this.injection.substring(1));
			resultHandlerMethodReturnValueHandler.setResultAction(resultAction);
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}
}
