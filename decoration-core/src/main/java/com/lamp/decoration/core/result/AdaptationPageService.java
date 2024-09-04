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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author laohu
 */
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
			adaptationPageCache.put(adaptationPage.getPageObject(), adaptationPage);
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
