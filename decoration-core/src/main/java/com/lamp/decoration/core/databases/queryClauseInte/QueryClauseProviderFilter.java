package com.lamp.decoration.core.databases.queryClauseInte;

import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.ListenableFilter;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

@Activate(group = PROVIDER, order = -10000)
public class QueryClauseProviderFilter extends ListenableFilter {


	public QueryClauseProviderFilter() {
		super.listener = new QueryClauseProviderListener();
	}

	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		RpcContext context = RpcContext.getContext();
		QueryClauseCentre.queryClauseHandler(context.getAttachment(QueryClause.QUERY_CLAUSE_KEY));
		Result object = invoker.invoke(invocation);
		QueryClauseCentre.pageHandler(object.getValue());
		return object;
	}

	static class QueryClauseProviderListener implements Listener {

		@Override
		public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
			RpcContext context = RpcContext.getContext();
			QueryClauseCentre.queryClauseHandler(context.getAttachment(QueryClause.QUERY_CLAUSE_KEY));
			Result object = invoker.invoke(invocation);
			QueryClauseCentre.pageHandler(object.getValue());

		}

		@Override
		public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

		}

	}

}