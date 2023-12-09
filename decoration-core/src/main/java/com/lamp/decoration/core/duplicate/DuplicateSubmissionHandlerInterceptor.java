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
package com.lamp.decoration.core.duplicate;

import com.alibaba.fastjson.JSON;
import com.lamp.decoration.core.result.ResultObject;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author laohu
 */
public class DuplicateSubmissionHandlerInterceptor implements HandlerInterceptor {


	private DuplicateSubmissionHandler duplicateSubmissionHandler;
	
	public DuplicateSubmissionHandlerInterceptor(DuplicateCheck duplicateCheck) {
		this.duplicateSubmissionHandler = new DuplicateSubmissionHandler(duplicateCheck);
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod method = (HandlerMethod) handler;
		boolean result = duplicateSubmissionHandler.preHandle(method.getMethod());
		if(!result){
			response.addHeader("content-type","json");
			response.getWriter().write(JSON.toJSONString(ResultObject.DEFAULT_DUPLICATE_FAIL));
		}
		return result;
	}
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		duplicateSubmissionHandler.unlock();
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		duplicateSubmissionHandler.unlock();
	}
}
