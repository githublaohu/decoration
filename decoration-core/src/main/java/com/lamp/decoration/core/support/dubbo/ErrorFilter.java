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
package com.lamp.decoration.core.support.dubbo;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.*;

import java.util.Objects;

import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

/**
 * @author hahaha
 */
@Activate(group = {PROVIDER}, order = -10000)
public class ErrorFilter extends ListenableFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorFilter.class);

	public ErrorFilter() {
		super.listener = new ErrorListener();
	}
	
	
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		Result object = invoker.invoke(invocation);
		AsyncContext asyncContext =  RpcContext.getContext().getAsyncContext();
		if(Objects.nonNull(object.getException()) && (asyncContext == null || (!asyncContext.isAsyncStarted() && asyncContext.stop()))) {
			logger.error(object.getException().getMessage() , object.getException());
		}
		return object;
	}

	
	static class ErrorListener implements Listener{

		@Override
		public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
			
		}

		@Override
		public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
			logger.error(t.getMessage() , t);
		}
		
	}
}
