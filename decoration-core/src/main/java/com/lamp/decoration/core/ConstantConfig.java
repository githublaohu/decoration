package com.lamp.decoration.core;

public class ConstantConfig {

    private String discern;
    
    private String pageSize = "pageSize";
    
    private String limitCoordinate = "limitCoordinate";
    
    private String orderBy = "orderBy";

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
