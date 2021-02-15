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
