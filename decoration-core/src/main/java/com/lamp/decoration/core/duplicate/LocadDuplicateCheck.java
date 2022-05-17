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

import com.lamp.decoration.core.duplicate.DuplicateSubmissionHandlerInterceptor.DuplicateSubmissionData;

public class LocadDuplicateCheck extends AbstractDuplicateCheck {

	Map<String/* 方法名 */,Map<String /*校验id*/,Long>> duplicateRecord = new ConcurrentHashMap<>();
	
	@Override
	boolean check(DuplicateSubmissionData duplicateSubmissionData) {
		Map<String /*校验id*/,Long> typeMap = duplicateRecord.get(duplicateSubmissionData.duplicateName);
		if(Objects.isNull(typeMap)) {
			typeMap = duplicateRecord.computeIfAbsent(duplicateSubmissionData.duplicateName, key -> new ConcurrentHashMap<>() );
		}
		Long currentTime = System.currentTimeMillis();
		Long loasTime = typeMap.computeIfPresent(getCheckIdentification(duplicateSubmissionData), (key,value) -> currentTime);
		if(Objects.isNull(loasTime)) {
			return false;
		}
		if( currentTime > loasTime+ duplicateSubmissionData.intervalTime) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	@Override
	public void unlock(DuplicateSubmissionData duplicateSubmissionData) {
		Map<String /*校验id*/,Long> typeMap = duplicateRecord.get(duplicateSubmissionData.duplicateName);
		//uplicateSubmissionHandlerInterceptor
	}

}
