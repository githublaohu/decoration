package com.lamp.decoration.core.databases.queryClauseInte.returndata;

import java.util.Map;

import org.apache.dubbo.rpc.RpcContext;

import com.lamp.decoration.core.result.ResultObject;

public class DubboRpcReturnData implements RpcReturnData {

    public void pageData(ResultObject<Object> resultObject) {
        Map<String, String> attachments = RpcContext.getServerContext().getAttachments();
        if(attachments.containsKey("total")) {
            resultObject.setTotal(Long.valueOf(attachments.get("total")));
            resultObject.setCurrentPage(Integer.valueOf(attachments.get("currentPage")));
            resultObject.setPageSize(Integer.valueOf(attachments.get("pageSize")));
        }
    }

}
