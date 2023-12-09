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

/**
 * @author laohu
 */
public class QueryClause {

    public final static String QUERY_CLAUSE_KEY = "queryClause";
    
    private Integer limitStart;
    
    private Integer limitSize;
    
    private Integer limitPageNum;
    
    private String groupBy;
    
    private String orderBy;

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitSize() {
        return limitSize;
    }

    public void setLimitSize(Integer limitSize) {
        this.limitSize = limitSize;
    }

    public Integer getLimitPageNum() {
        return limitPageNum;
    }

    public void setLimitPageNum(Integer limitPageNum) {
        this.limitPageNum = limitPageNum;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "QueryClause [limitStart=" + limitStart + ", limitSize=" + limitSize + ", limitPageNum=" + limitPageNum
            + ", groupBy=" + groupBy + ", orderBy=" + orderBy + "]";
    }
    
    
    
}
