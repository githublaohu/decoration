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

import java.util.ArrayList;
import java.util.Objects;

import com.lamp.decoration.core.databases.queryClauseInte.handler.DuddboQueryClauseHandler;
import com.lamp.decoration.core.databases.queryClauseInte.handler.PageHelperQueryClauseHandler;
import com.lamp.decoration.core.databases.queryClauseInte.handler.QueryClauseHandler;
import com.lamp.decoration.core.databases.queryClauseInte.returndata.DubboRpcReturnData;
import com.lamp.decoration.core.databases.queryClauseInte.returndata.RpcReturnData;
import com.lamp.decoration.core.result.ResultObject;

public class QueryClauseCentre {
    public static final ArrayList<QueryClauseHandler> QUERY_CLAUSE_HANDLER_LIST = new ArrayList<>();

    public static final ArrayList<RpcReturnData> RPC_RETURN_DATA_LIST = new ArrayList<>();
    
    static {

        try {
            PageHelperQueryClauseHandler helperQueryClauseHandler = new PageHelperQueryClauseHandler();
            QUERY_CLAUSE_HANDLER_LIST.add(helperQueryClauseHandler);
        } catch (Throwable e) {

        }
        try {
            DuddboQueryClauseHandler helperQueryClauseHandler = new DuddboQueryClauseHandler();
            QUERY_CLAUSE_HANDLER_LIST.add(helperQueryClauseHandler);
        } catch (Throwable e) {

        }
        try {
            RpcReturnData rpcReturnData = new DubboRpcReturnData();
            RPC_RETURN_DATA_LIST.add(rpcReturnData);
            rpcReturnData.toString();
        }catch(Throwable E) {
            
        }
    }
    
    public static void queryClauseHandler(String queryClause) {
        if(Objects.isNull(queryClause) || QUERY_CLAUSE_HANDLER_LIST.isEmpty()) {
            return;
        }
        for(QueryClauseHandler queryClauseHandler : QUERY_CLAUSE_HANDLER_LIST) {
            queryClauseHandler.handler(queryClause);
        }
    }
    
    public static void pageHandler(Object object) {
        if(Objects.isNull(object)) {
            return ;
        }
        for(QueryClauseHandler queryClauseHandler : QUERY_CLAUSE_HANDLER_LIST) {
            queryClauseHandler.pageHandler(object);
        }
    }
    
    public static void pageData(ResultObject<Object> resultObject) {
        for(RpcReturnData rpcReturnData : RPC_RETURN_DATA_LIST) {
            rpcReturnData.pageData(resultObject);
        }
    }
}
