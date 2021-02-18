package com.lamp.decoration.core.databases.queryClauseInte;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.lamp.decoration.core.ConstantConfig;

public class QueryClauseInterceptor implements HandlerInterceptor {

    private ConstantConfig constantConfig;

    public QueryClauseInterceptor(ConstantConfig constantConfig) {
        this.constantConfig = constantConfig;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        QueryClauseCentre.queryClauseHandler(getQueryClause(request));
        return true;
    }

    public String getQueryClause(HttpServletRequest request) {
        String queryClause = request.getHeader(constantConfig.getDiscern());
        return queryClause;
    }
}
