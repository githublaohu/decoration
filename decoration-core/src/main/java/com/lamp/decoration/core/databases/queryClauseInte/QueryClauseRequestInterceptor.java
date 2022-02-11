package com.lamp.decoration.core.databases.queryClauseInte;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.lamp.decoration.core.databases.queryClauseInte.handler.DefaultQueryClauseHandler;

import feign.RequestInterceptor;
import feign.RequestTemplate;


public class QueryClauseRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		Map<String,Object> pageData = DefaultQueryClauseHandler.getPageData();
		if(Objects.nonNull(pageData)) {
			for(Entry<String, Object> e :  pageData.entrySet()) {
				template.header(e.getKey(), e.getValue().toString());
			}
		}
	}

}
