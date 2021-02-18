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
