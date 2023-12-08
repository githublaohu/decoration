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

import com.alibaba.fastjson.JSON;
import com.lamp.decoration.core.DecorationContext;
import com.lamp.decoration.core.databases.queryClauseInte.QueryClause;

/**
 * @author laohu
 */
public class DefaultQueryClauseHandler implements QueryClauseHandler {

	@Override
	public void handler(String queryClause) {
		QueryClause jsonObject = JSON.parseObject(queryClause, QueryClause.class);
		DecorationContext.get().setQueryClause(jsonObject);
	}

	@Override
	public void pageHandler(Object object) {
		

	}

}
