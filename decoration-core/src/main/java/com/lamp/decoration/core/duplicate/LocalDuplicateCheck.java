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

/**
 * 本地换成做认证
 */
public class LocalDuplicateCheck extends AbstractDuplicateCheck {

	Map<String/* 方法名 */,Map<String /*校验id*/,Long>> duplicateRecord = new ConcurrentHashMap<>();
	
	@Override
	boolean check(DuplicateSubmissionData duplicateSubmissionData) {
		Map<String /*校验id*/,Long> typeMap = duplicateRecord.get(duplicateSubmissionData.getDuplicateName());
		if(Objects.isNull(typeMap)) {
			typeMap = duplicateRecord.computeIfAbsent(duplicateSubmissionData.getDuplicateName(), key -> new ConcurrentHashMap<>() );
		}
		Long currentTime = System.currentTimeMillis();
		Long loadsTime = typeMap.computeIfPresent(getCheckIdentification(duplicateSubmissionData), (key,value) -> currentTime);
		if(Objects.isNull(loadsTime)) {
			return false;
		}
		if( currentTime > loadsTime+ duplicateSubmissionData.getIntervalTime()) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	@Override
	public void unlock(DuplicateSubmissionData duplicateSubmissionData) {
		Map<String /*校验id*/,Long> typeMap = duplicateRecord.get(duplicateSubmissionData.getDuplicateName());
		//uplicateSubmissionHandlerInterceptor
	}

}
