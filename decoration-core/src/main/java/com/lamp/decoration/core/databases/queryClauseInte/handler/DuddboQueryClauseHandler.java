package com.lamp.decoration.core.databases.queryClauseInte.handler;

import org.apache.dubbo.rpc.RpcContext;

import com.github.pagehelper.Page;
import com.lamp.decoration.core.databases.queryClauseInte.QueryClause;

public class DuddboQueryClauseHandler implements QueryClauseHandler {

    public void handler(String queryClause) {
        RpcContext.getContext().setAttachment(QueryClause.QUERY_CLAUSE_KEY, queryClause);
    }

    public void pageHandler(Object object) {
        if(object instanceof Page) {
            @SuppressWarnings("unchecked")
            com.github.pagehelper.Page<Object> page = (com.github.pagehelper.Page<Object>)object;
            RpcContext rpcContext = RpcContext.getContext();
            rpcContext.setAttachment("total", Long.toString(page.getTotal()));
            rpcContext.setAttachment("pageSize", Long.toString(page.getPageSize()));
            rpcContext.setAttachment("currentPage", Long.toString(page.getPageNum()));
        }
    }

}
