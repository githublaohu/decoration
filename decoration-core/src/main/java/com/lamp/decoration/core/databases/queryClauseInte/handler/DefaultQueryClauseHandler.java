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
