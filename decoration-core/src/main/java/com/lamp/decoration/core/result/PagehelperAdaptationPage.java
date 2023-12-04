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
package com.lamp.decoration.core.result;

import com.github.pagehelper.Page;

/**
 * @author laohu
 */
public class PagehelperAdaptationPage implements AdaptationPage {

	@Override
	public Class<?> getPageObject() {
		return Page.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void adaptation(ResultObject<Object> resultObject, Object object) {
        Page<Object> page = (Page<Object>)object;
        resultObject.setTotal(page.getTotal());
        resultObject.setPageSize(page.getPageSize());
        resultObject.setData(page.getResult());
        resultObject.setCurrentPage(page.getPageNum());
	}

}
