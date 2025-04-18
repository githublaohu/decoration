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
package com.lamp.decoration.core.databases.queryClauseInte.returndata;

import org.apache.dubbo.rpc.RpcContext;

import java.util.Map;

import com.lamp.decoration.core.result.ResultObject;

public class DubboRpcReturnData implements RpcReturnData {

    @Override
    public void pageData(ResultObject<Object> resultObject) {
        Map<String, String> attachments = RpcContext.getServerContext().getAttachments();
        if(attachments.containsKey("total")) {
            resultObject.setTotal(Long.valueOf(attachments.get("total")));
            resultObject.setCurrentPage(Integer.valueOf(attachments.get("currentPage")));
            resultObject.setPageSize(Integer.valueOf(attachments.get("pageSize")));

        }
    }



}