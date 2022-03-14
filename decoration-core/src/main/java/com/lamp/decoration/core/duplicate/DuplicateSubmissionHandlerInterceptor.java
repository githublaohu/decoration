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

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DuplicateSubmissionHandlerInterceptor implements HandlerInterceptor {

	private static final ThreadLocal<DuplicateSubmissionLocadData> LOCAD_DATA = new ThreadLocal<>();
	
	private Map<HandlerMethod, DuplicateSubmissionData> duplicateSubmission = new ConcurrentHashMap<>();
	
	private DuplicateCheck duplicateCheck;
	
	
	public static DuplicateSubmissionLocadData getDuplicateSubmissionLocadData() {
		return LOCAD_DATA.get();
	}
	
	public DuplicateSubmissionHandlerInterceptor(DuplicateCheck duplicateCheck) {
		this.duplicateCheck = duplicateCheck;
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if( !(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod method = (HandlerMethod) handler;
		DuplicateSubmissionData duplicateSubmissionData = duplicateSubmission.get(method);
		if(Objects.isNull(duplicateSubmissionData)) {
			duplicateSubmission.computeIfAbsent(method, k ->{
				DuplicateSubmission duplicateSubmission = method.getMethodAnnotation(DuplicateSubmission.class);
				DuplicateSubmissionData newDuplicateSubmissionData = new DuplicateSubmissionData();
				if(Objects.nonNull(newDuplicateSubmissionData)) {
					newDuplicateSubmissionData.isDuplicate = true;
					newDuplicateSubmissionData.intervalTime = duplicateSubmission.intervalTime();
					newDuplicateSubmissionData.duplicateType = duplicateSubmission.duplicateType();
					newDuplicateSubmissionData.duplicateName = method.getMethod().getName();
					newDuplicateSubmissionData.lock = duplicateSubmission.lock();
					newDuplicateSubmissionData.lockKey = duplicateSubmission.lockKey();
				}
				return newDuplicateSubmissionData;
			});
		}
		if(duplicateSubmissionData.lock) {
			DuplicateSubmissionLocadData duplicateSubmissionLocadData = new DuplicateSubmissionLocadData();
			duplicateSubmissionLocadData.duplicateSubmissionData = duplicateSubmissionData;
			LOCAD_DATA.set(duplicateSubmissionLocadData);
		}
		if(duplicateCheck.isDuplicateCheck(duplicateSubmissionData)) {
			// 输出结果
		}
		return true;
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		unlock(request, response, handler);
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		unlock(request, response, handler);
	}
	
	
	private void unlock(HttpServletRequest request, HttpServletResponse response, Object handler) {
		DuplicateSubmissionLocadData duplicateSubmissionLocadData = LOCAD_DATA.get();
		if(Objects.isNull(duplicateSubmissionLocadData)) {
			return;
		}
		LOCAD_DATA.remove();
		if(duplicateSubmissionLocadData.duplicateSubmissionData.lock) {
			duplicateCheck.unlock(duplicateSubmissionLocadData.duplicateSubmissionData);
		}
		
	}
	
	
	static class DuplicateSubmissionData{
		boolean isDuplicate = false;
		
		long intervalTime;
		
		DuplicateIdentification duplicateType;
		
		String duplicateName;
		
		boolean lock;
		
		String[] lockKey;
	}
	
	
	static class  DuplicateSubmissionLocadData{
		DuplicateSubmissionData duplicateSubmissionData;
		
		String key;
	}
}
