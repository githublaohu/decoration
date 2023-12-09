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

/**
 * @author laohu
 */
public class DuplicateSubmissionData {

    private boolean isDuplicate = false;

    private long intervalTime;

    private DuplicateIdentification duplicateType;

    private String duplicateName;

    private boolean lock;

    private String[] lockKey;


    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public DuplicateIdentification getDuplicateType() {
        return duplicateType;
    }

    public void setDuplicateType(DuplicateIdentification duplicateType) {
        this.duplicateType = duplicateType;
    }

    public String getDuplicateName() {
        return duplicateName;
    }

    public void setDuplicateName(String duplicateName) {
        this.duplicateName = duplicateName;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public String[] getLockKey() {
        return lockKey;
    }

    public void setLockKey(String[] lockKey) {
        this.lockKey = lockKey;
    }
}
