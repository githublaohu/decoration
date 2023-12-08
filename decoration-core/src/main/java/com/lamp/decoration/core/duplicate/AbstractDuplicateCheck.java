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
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public abstract class AbstractDuplicateCheck implements DuplicateCheck {

    private static final Log logger = LogFactory.getLog(AbstractDuplicateCheck.class);

    @Override
    public boolean isDuplicateCheck(DuplicateSubmissionData duplicateSubmissionData) {
        if (duplicateSubmissionData.isDuplicate()) {
            return check(duplicateSubmissionData);
        }
        return true;
    }

    abstract boolean check(DuplicateSubmissionData duplicateSubmissionData);

    @SuppressWarnings("unused")
    String getCheckIdentification(DuplicateSubmissionData duplicateSubmissionData) {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()));

        String key = null;
        if (Objects.equals(duplicateSubmissionData.getDuplicateType() , DuplicateIdentification.BODY)) {
            String type = servletRequestAttributes.getRequest().getHeader("content-type");
            try {
                InputStream inputStream = servletRequestAttributes.getRequest().getInputStream();
                int length = Integer.valueOf(servletRequestAttributes.getRequest().getHeader("content-length"));
                byte[] body = new byte[length];
                inputStream.read(body);
                JSONObject jsonObject = null;
                if (type.startsWith("application/json")) {
                    jsonObject = JSON.parseObject(new String(body));
                } else {

                }
                StringBuilder stringBuilder = new StringBuilder();
                for(String lockKey : duplicateSubmissionData.getLockKey()){
                    stringBuilder.append(jsonObject.getString(lockKey)).append("_");
                }
                key = stringBuilder.toString();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return null;
            }

        } else if(Objects.equals(duplicateSubmissionData.getDuplicateType() , DuplicateIdentification.NETWORK_ADDRESS)) {
            String forwarded = servletRequestAttributes.getRequest().getHeader("x-forwarded-for");
            if (Objects.isNull(forwarded)) {
                String[] ipArray = StringUtils.split(forwarded, ",");
                if (ipArray.length > 0) {
                    key = ipArray[0];
                }
            }else {
                key = servletRequestAttributes.getRequest().getRemoteAddr();
            }
        } else if(Objects.equals(duplicateSubmissionData.getDuplicateType() , DuplicateIdentification.USER_ID)){

        }
        DuplicateSubmissionHandler.getDuplicateSubmissionLocalData().key = key;
        return key;
    }
}
