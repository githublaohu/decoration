package com.lamp.decoration.core.support.dubbo;

import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

import java.util.Objects;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.ListenableFilter;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

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
