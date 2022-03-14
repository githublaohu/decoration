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
package com.lamp.decoration.core.databases.queryClauseInte;

import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.ListenableFilter;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

@Activate(group = PROVIDER, order = 0)
public class QueryClauseProviderFilter extends ListenableFilter {


	public QueryClauseProviderFilter() {
		//super.listener = new QueryClauseProviderListener();
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
			

		}

		@Override
		public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

		}

	}

}