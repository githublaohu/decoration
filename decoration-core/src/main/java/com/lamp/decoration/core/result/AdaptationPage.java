package com.lamp.decoration.core.result;

public interface AdaptationPage {

	
	public Class<?> getPageObject();
	
	public void adaptation(ResultObject<Object> resultObject , Object object) ;
}
