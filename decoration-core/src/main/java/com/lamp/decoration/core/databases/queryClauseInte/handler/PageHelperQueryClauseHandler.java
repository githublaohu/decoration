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
package com.lamp.decoration.core.databases.queryClauseInte.handler;

import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.lamp.decoration.core.databases.queryClauseInte.QueryClause;

public class PageHelperQueryClauseHandler implements QueryClauseHandler {

	public PageHelperQueryClauseHandler() {
		PageHelper.clearPage();
	}
	
    public void handler(String queryClauseString) {
        QueryClause queryClause = JSON.parseObject(queryClauseString, QueryClause.class);
        if (Objects.nonNull(queryClause.getLimitStart()) && Objects.nonNull(queryClause.getLimitSize())) {
            PageHelper.offsetPage(queryClause.getLimitStart(), queryClause.getLimitSize());
        }
        if (Objects.nonNull(queryClause.getLimitPageNum()) && Objects.nonNull(queryClause.getLimitSize())) {
            int pageNum = queryClause.getLimitPageNum();
            int limitStart = pageNum == 0? 0 : (pageNum - 1)*queryClause.getLimitSize();
            PageHelper.offsetPage(limitStart, queryClause.getLimitSize());
        }
        if(Objects.nonNull(queryClause.getOrderBy())) {
            PageHelper.orderBy(queryClause.getOrderBy());
        }     
    }

    public void pageHandler(Object object) {
       
    	PageHelper.clearPage();
    }

}
