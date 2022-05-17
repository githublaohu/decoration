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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
            
            Field[] fields = Field.class.getDeclaredFields();
            
            Field modifersField = field.getClass().getDeclaredField("modifiers");
            modifersField.setAccessible(true);
            modifersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            
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
