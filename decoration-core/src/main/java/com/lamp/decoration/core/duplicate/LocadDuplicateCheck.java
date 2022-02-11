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

	@Override
	public void unlock(DuplicateSubmissionData duplicateSubmissionData) {
		Map<String /*校验id*/,Long> typeMap = duplicateRecord.get(duplicateSubmissionData.duplicateName);
		//uplicateSubmissionHandlerInterceptor
	}

}
