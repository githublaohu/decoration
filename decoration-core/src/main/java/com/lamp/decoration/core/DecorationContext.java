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
package com.lamp.decoration.core;

import com.lamp.decoration.core.databases.queryClauseInte.QueryClause;
import com.lamp.decoration.core.result.ResultObject;

/**
 * @author laohu
 */
public class DecorationContext {

    private static final ThreadLocal<DecorationContext> THREAD_LOCAL = new ThreadLocal() {

        protected DecorationContext initialValue() {
            return new DecorationContext();
        }
    };
    private ResultObject<String> resultObject;
    private QueryClause queryClause;

    private String queryClauseKey;

    public static DecorationContext get() {
        return THREAD_LOCAL.get();
    }

    public ResultObject<String> getResultObject() {
        return resultObject;
    }

    public void setResultObject(ResultObject<String> resultObject) {
        this.resultObject = resultObject;
    }

    public void close() {
        this.resultObject = null;
        this.queryClause = null;
        this.queryClauseKey = null;
    }

    public QueryClause getQueryClause() {
        return this.queryClause;
    }

    public void setQueryClause(QueryClause queryClause) {
        this.queryClause = queryClause;
    }

    public void setQueryClauseKey(String queryClauseKey ){
        this.queryClauseKey = queryClauseKey;
    }

    public String getQueryClauseKey(){
        return this.queryClauseKey;
    }
}
