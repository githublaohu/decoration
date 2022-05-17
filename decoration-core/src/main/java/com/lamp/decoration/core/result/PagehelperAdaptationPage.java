package com.lamp.decoration.core.result;

import com.github.pagehelper.Page;

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
