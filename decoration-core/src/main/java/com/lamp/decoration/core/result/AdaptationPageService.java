package com.lamp.decoration.core.result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AdaptationPageService {

	private static final AdaptationPageService instance = new AdaptationPageService();
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private Map<Class<?> , AdaptationPage>  adaptationPageCache = new HashMap<>();

	static {
		instance.register(new PagehelperAdaptationPage());
	}
	
	public AdaptationPageService() {}
	
	public static final AdaptationPageService getInstance() {
		return instance;
	}
	
	public void register(AdaptationPage adaptationPage) {
		try {
			adaptationPageCache.put(adaptationPage.getClass(), adaptationPage);
			logger.info(" page adaptation register success " + adaptationPage.getClass());
		}catch(Exception e) {
			logger.info(" page adaptation register fail " + adaptationPage.getClass());
		}
	}
	
	public void adaptation(ResultObject<Object> resultObject , Object object) {
		AdaptationPage adaptationPage = adaptationPageCache.get(object.getClass());
		if(Objects.nonNull(adaptationPage)) {
			adaptationPage.adaptation(resultObject, object);
		}
	}
}
