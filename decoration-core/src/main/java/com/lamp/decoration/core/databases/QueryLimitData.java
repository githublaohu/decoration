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
package com.lamp.decoration.core.databases;

/**
 * @author laohu
 */
public class QueryLimitData {

    private boolean queryLimit = false;

    private boolean pageQuery = false;

    private int defaultLimit;

    public boolean isQueryLimit() {
        return queryLimit;
    }

    public void setQueryLimit(boolean queryLimit) {
        this.queryLimit = queryLimit;
    }

    public boolean isPageQuery() {
        return pageQuery;
    }

    public void setPageQuery(boolean pageQuery) {
        this.pageQuery = pageQuery;
    }

    public int getDefaultLimit() {
        return defaultLimit;
    }

    public void setDefaultLimit(int defaultLimit) {
        this.defaultLimit = defaultLimit;
    }
}
