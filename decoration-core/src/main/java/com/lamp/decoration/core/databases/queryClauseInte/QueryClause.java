package com.lamp.decoration.core.databases.queryClauseInte;

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
