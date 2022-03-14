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
