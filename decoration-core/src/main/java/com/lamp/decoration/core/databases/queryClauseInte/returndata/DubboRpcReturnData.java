package com.lamp.decoration.core.databases.queryClauseInte.returndata;

import java.util.Map;

import org.apache.dubbo.rpc.RpcContext;

import com.lamp.decoration.core.result.ResultObject;

public class DubboRpcReturnData implements RpcReturnData {

    public void pageData(ResultObject<Object> resultObject) {
        RpcContext context = RpcContext.getContext();
        Map<String, String> attachments = context.getAttachments();
        if(attachments.containsKey("total")) {
            resultObject.setTotal(Long.valueOf(context.getAttachment("total")));
            resultObject.setCurrentPage(Integer.valueOf(context.getAttachment("currentPage")));
            resultObject.setPageSize(Integer.valueOf(context.getAttachment("pageSize")));
        }
    }

}
