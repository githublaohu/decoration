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

import java.util.Map;

import com.alibaba.fastjson.JSON;

public class DefaultQueryClauseHandler implements QueryClauseHandler {

	private static final  ThreadLocal<Map<String,Object>>  QUERY_CLAUSE_LOCAL = new ThreadLocal<>();
	
	@Override
	public void handler(String queryClause) {
		Map<String,Object> jsonObject = JSON.parseObject(queryClause);
		QUERY_CLAUSE_LOCAL.set(jsonObject);
	}

	@Override
	public void pageHandler(Object object) {
		

	}
	
	public static Map<String,Object> getPageData(){
		return QUERY_CLAUSE_LOCAL.get();
	}

}
