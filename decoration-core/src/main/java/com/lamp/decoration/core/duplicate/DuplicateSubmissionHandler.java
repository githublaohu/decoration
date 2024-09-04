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

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 真正执行对象，
 *
 * @author laohu
 */
public class DuplicateSubmissionHandler {

    private static final ThreadLocal<DuplicateSubmissionLocalData> LOCAL_DATA = ThreadLocal.withInitial(DuplicateSubmissionLocalData::new);
    private final DuplicateCheck duplicateCheck;
    private final Map<Method, DuplicateSubmissionData> duplicateSubmission = new ConcurrentHashMap<>();


    public DuplicateSubmissionHandler(DuplicateCheck duplicateCheck) {
        this.duplicateCheck = duplicateCheck;
    }

    static DuplicateSubmissionLocalData getDuplicateSubmissionLocalData() {
        return LOCAL_DATA.get();
    }

    public boolean preHandle(Method method) {

        DuplicateSubmissionData duplicateSubmissionData = duplicateSubmission.get(method);
        if (Objects.isNull(duplicateSubmissionData)) {
            duplicateSubmissionData = duplicateSubmission.computeIfAbsent(method, k -> {
                DuplicateSubmission duplicateSubmission = method.getAnnotation(DuplicateSubmission.class);
                DuplicateSubmissionData newDuplicateSubmissionData = new DuplicateSubmissionData();
                if (Objects.nonNull(duplicateSubmission)) {
                    newDuplicateSubmissionData.setDuplicate(true);
                    newDuplicateSubmissionData.setIntervalTime(duplicateSubmission.intervalTime());
                    newDuplicateSubmissionData.setDuplicateType(duplicateSubmission.duplicateType());
                    newDuplicateSubmissionData.setDuplicateName(method.getName());
                    newDuplicateSubmissionData.setLock(duplicateSubmission.lock());
                    newDuplicateSubmissionData.setLockKey(duplicateSubmission.lockKey());
                }
                return newDuplicateSubmissionData;
            });
        }
        if (duplicateSubmissionData.isLock()) {
            DuplicateSubmissionLocalData duplicateSubmissionLocalData = new DuplicateSubmissionLocalData();
            duplicateSubmissionLocalData.duplicateSubmissionData = duplicateSubmissionData;
            LOCAL_DATA.set(duplicateSubmissionLocalData);
            return duplicateCheck.isDuplicateCheck(duplicateSubmissionData);
        }

        return true;
    }


    public void unlock() {
        DuplicateSubmissionHandler.DuplicateSubmissionLocalData duplicateSubmissionLocalData = getDuplicateSubmissionLocalData();
        if (Objects.isNull(duplicateSubmissionLocalData)) {
            return;
        }
        if(Objects.isNull(duplicateSubmissionLocalData.duplicateSubmissionData)){
            return;
        }

        DuplicateSubmissionData duplicateSubmissionData = duplicateSubmissionLocalData.duplicateSubmissionData;
        duplicateSubmissionLocalData.key = null;
        duplicateSubmissionLocalData.duplicateSubmissionData = null;
        if (duplicateSubmissionData.isLock()) {
            duplicateCheck.unlock(duplicateSubmissionData);
        }

    }


    static class DuplicateSubmissionLocalData {
        DuplicateSubmissionData duplicateSubmissionData;

        String key;
    }

}
