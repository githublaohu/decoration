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

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lamp.decoration.core.duplicate.DuplicateSubmissionHandlerInterceptor.DuplicateSubmissionData;

public abstract class AbstractDuplicateCheck implements DuplicateCheck {

	private static final Log logger = LogFactory.getLog(AbstractDuplicateCheck.class);
	
	@Override
	public boolean isDuplicateCheck(DuplicateSubmissionData duplicateSubmissionData) {
		if (duplicateSubmissionData.isDuplicate) {
			return check(duplicateSubmissionData);
		}
		return true;
	}

	abstract boolean check(DuplicateSubmissionData duplicateSubmissionData);

	String getCheckIdentification(DuplicateSubmissionData duplicateSubmissionData) {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
				.getRequest();
		if (duplicateSubmissionData.lock) {
			String type = request.getHeader("content-type");
			try {
				String key = null;
				InputStream inputStream =  request.getInputStream();
				int length = Integer.valueOf(request.getHeader("content-length"));
				byte[] body = new byte[length];
				inputStream.read(body);
				if(type.indexOf("application/json") > -1) {
					JSONObject jsonObject = JSON.parseObject(new String(body));
				}else {
					
				}
				if(duplicateSubmissionData.lock) {
					DuplicateSubmissionHandlerInterceptor.getDuplicateSubmissionLocadData().key = key;
				}
				
				return null;
			} catch (IOException e) {
				logger.error(e.getMessage() , e);
				return null;
			}
			
		}else {
				
				String forwarded = request.getHeader("x-forwarded-for");
				if (Objects.isNull(forwarded)) {
					String[] ipArray = StringUtils.split(forwarded, ",");
					if (ipArray.length > 0) {
						return ipArray[0];
					}
				}
				return request.getRemoteAddr();
			
		}
	}
}
