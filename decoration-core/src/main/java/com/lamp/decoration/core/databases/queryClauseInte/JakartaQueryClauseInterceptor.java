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

import com.lamp.decoration.core.ConstantConfig;
import com.lamp.decoration.core.databases.Querylimit;
import com.lamp.decoration.core.databases.QuerylimitData;
import com.lamp.decoration.core.databases.queryClauseInte.QueryClauseCentre;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laohu
 */
public class JakartaQueryClauseInterceptor implements HandlerInterceptor {

    private ConcurrentHashMap<Object, QuerylimitData> querylimitMap = new ConcurrentHashMap<>();

    private ConstantConfig constantConfig;

    public JakartaQueryClauseInterceptor(ConstantConfig constantConfig) {
        this.constantConfig = constantConfig;
    }


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if(constantConfig.isAll()){
            QueryClauseCentre.queryClauseHandler(getQueryClause(request),null);
        }
        QuerylimitData querylimitData = querylimitMap.get(handler);
        if(Objects.isNull(querylimitData)) {
            querylimitData = new QuerylimitData();
            querylimitMap.put(handler,querylimitData);
            if (handler instanceof HandlerExecutionChain) {
                HandlerExecutionChain handlerExecutionChain = (HandlerExecutionChain) handler;
                Object handler1 = handlerExecutionChain.getHandler();
                Querylimit querylimit = null;
                if (handler1 instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler1;
                    querylimit = handlerMethod.getMethodAnnotation(Querylimit.class);
                }
                if(Objects.nonNull(querylimit)){
                    querylimitData.setQueryLimit(true);
                }
            }
        }
        if(querylimitData.isQueryLimit()){
            QueryClauseCentre.queryClauseHandler(getQueryClause(request),querylimitData);
        }
        return true;
    }

    public String getQueryClause(HttpServletRequest request) {
        String queryClause = request.getHeader(constantConfig.getDiscern());
        if (Objects.isNull(queryClause)) {
            String key = "&" + constantConfig.getDiscern() + "=";
            String query = request.getQueryString();
            int index = query.indexOf(key);
            if (index > -1) {
               queryClause = query.substring(index+key.length() ,query.indexOf('&',index+key.length()));
            }
        }
        return queryClause;
    }
}
