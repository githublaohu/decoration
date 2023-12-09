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

import com.alibaba.fastjson.JSON;
import com.lamp.decoration.core.DecorationContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Objects;


/**
 * @author laohu
 */
public class QueryClauseRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		QueryClause pageData = DecorationContext.get().getQueryClause();
		if(Objects.nonNull(pageData)) {
			template.header(DecorationContext.get().getQueryClauseKey(), JSON.toJSONString(pageData));
		}
	}

}
