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
package com.lamp.decoration.core.databases.queryClauseInte.handler;

import org.apache.dubbo.rpc.RpcContext;

import com.github.pagehelper.Page;
import com.lamp.decoration.core.databases.queryClauseInte.QueryClause;

/**
 * @author laohu
 */
public class DuddboQueryClauseHandler implements QueryClauseHandler {

    @Override
    public void handler(String queryClause) {
        RpcContext.getContext().setAttachment(QueryClause.QUERY_CLAUSE_KEY, queryClause);
    }

    @Override
    public void pageHandler(Object object) {
        if(object instanceof Page) {
            @SuppressWarnings("unchecked")
            com.github.pagehelper.Page<Object> page = (com.github.pagehelper.Page<Object>)object;
            RpcContext serverContext = RpcContext.getServerContext();
            serverContext.setAttachment("total", Long.toString(page.getTotal()));
            serverContext.setAttachment("pageSize", Long.toString(page.getPageSize()));
            serverContext.setAttachment("currentPage", Long.toString(page.getPageNum()));
        }
    }

}
