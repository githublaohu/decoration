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

import java.util.ArrayList;
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
import com.lamp.decoration.core.result.ResultConfig;
import com.lamp.decoration.core.result.ResultHandlerMethodReturnValueHandler;

/**
 * 用于得到 RequestResponseBodyMethodProcessor， 并且代理 RequestResponseBodyMethodProcessor
 * @author hahaha
 */
public class OperationSpringMVCBehavior implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private ResultHandlerMethodReturnValueHandler resultHandlerMethodReturnValueHandler;
    private ResultAction<Object> resultAction;

    private ResultConfig resultConfig;

    public OperationSpringMVCBehavior(ResultConfig resultConfig) {
        this.resultConfig = resultConfig;
        this.resultAction = resultConfig.getInjection();

    }

    @PostConstruct
    public void init() {
        List<HandlerMethodReturnValueHandler> handlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        if(Objects.isNull(handlers)){
            return;
        }
        List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<>(handlers.size());
        for (HandlerMethodReturnValueHandler handlerMethodReturnValueHandler : handlers) {
            if (handlerMethodReturnValueHandler.getClass().equals(RequestResponseBodyMethodProcessor.class)) {
                resultHandlerMethodReturnValueHandler =
                        new ResultHandlerMethodReturnValueHandler(handlerMethodReturnValueHandler, resultConfig);
                resultHandlerMethodReturnValueHandler.setResultAction(resultAction);
                handlerMethodReturnValueHandler = resultHandlerMethodReturnValueHandler;
            }
            newHandlers.add(handlerMethodReturnValueHandler);
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(newHandlers);
    }

    /**
     *  如果是 spring 内部 resultAction ， 需要等待 spring application 启动成功
     * @param event the event to respond to
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (Objects.isNull(resultAction)) {
            resultAction = (ResultAction<Object>) applicationContext.getBean(this.resultConfig.getResultAction().substring(1));
            resultHandlerMethodReturnValueHandler.setResultAction(resultAction);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
}
