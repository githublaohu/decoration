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

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.lamp.decoration.core.ConstantConfig;
import com.lamp.decoration.core.databases.QueryLimitData;
import com.lamp.decoration.core.databases.Querylimit;
import com.lamp.decoration.core.utils.UtilsTool;

/**
 * @author laohu
 */
public class QueryClauseInterceptor implements HandlerInterceptor {

    private ConcurrentHashMap<Object, QueryLimitData> querylimitMap = new ConcurrentHashMap<>();

    private ConstantConfig constantConfig;

    public QueryClauseInterceptor(ConstantConfig constantConfig) {
        this.constantConfig = constantConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        QueryLimitData querylimitData = querylimitMap.get(handler);
        if (Objects.isNull(querylimitData)) {
            querylimitData = new QueryLimitData();
            querylimitMap.put(handler, querylimitData);
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                if (constantConfig.isAll()) {
                    Class<?> clazz = UtilsTool.getaClass(handlerMethod.getMethod());
                    if (UtilsTool.isCollection(clazz)) {
                        querylimitData.setQueryLimit(true);
                        querylimitData.setPageQuery(true);
                        querylimitData.setDefaultLimit(constantConfig.getDefaultLimit());
                    }
                } else {
                    Querylimit querylimit = handlerMethod.getMethodAnnotation(Querylimit.class);
                    if (Objects.nonNull(querylimit)) {
                        querylimitData.setDefaultLimit(querylimit.defaultLimit());
                        querylimitData.setQueryLimit(true);
                        querylimitData.setPageQuery(true);
                    }
                }
            }
        }

        if (querylimitData.isPageQuery()) {
            QueryClauseCentre.queryClauseHandler(getQueryClause(request), querylimitData);
        }
        return true;
    }

    public String getQueryClause(HttpServletRequest request) {
        String queryClause = request.getHeader(constantConfig.getDiscern());
        if (Objects.isNull(queryClause)) {
            String query = request.getQueryString();
            if (Objects.nonNull(query)) {
                String key = "&" + constantConfig.getDiscern() + "=";
                int index = query.indexOf(key);
                if (index > -1) {
                    queryClause = query.substring(index + key.length(), query.indexOf('&', index + key.length()));
                }
            }
        }
        return queryClause;
    }
}
