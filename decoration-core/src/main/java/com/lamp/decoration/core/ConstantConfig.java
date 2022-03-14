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

public class ConstantConfig {

    private String discern = QueryClause.QUERY_CLAUSE_KEY;
    
    private String pageSize = "pageSize";
    
    private String limitCoordinate = "limitCoordinate";
    
    private String orderBy = "orderBy";
    
    private String groudBy = "groudBy";
    

    public String getDiscern() {
        return discern;
    }

    public void setDiscern(String discern) {
        this.discern = discern;
    }

    public String getGroudBy() {
        return groudBy;
    }

    public void setGroudBy(String groudBy) {
        this.groudBy = groudBy;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getLimitCoordinate() {
        return limitCoordinate;
    }

    public void setLimitCoordinate(String limitCoordinate) {
        this.limitCoordinate = limitCoordinate;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
