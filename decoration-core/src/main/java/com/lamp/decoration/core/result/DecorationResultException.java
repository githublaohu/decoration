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

/**
 * @author laohu
 */
public class DecorationResultException extends RuntimeException {


    public static final void throwDecorationResultException(Integer code) {
        throwDecorationResultException(code, null, null, null);
    }

    public static final void throwDecorationResultException(Integer code, String message) {
        throwDecorationResultException(code, message, null, null);
    }

    public static final void throwDecorationResultException(Integer code, String message, String errorCode) {
        throwDecorationResultException(code, message, errorCode, null);
    }


    public static final void throwDecorationResultException(Integer code, String message, String errorCode, String errorMessage) {
        ResultObject<String> resultObject = new ResultObject<>(code, message);
        resultObject.setErrorMessages(errorMessage);
        resultObject.setErrorCode(errorCode);
        throwDecorationResultException(resultObject);
    }

    public static final void throwDecorationResultException(ResultObject<String> resultObject) {
        throw new DecorationResultException(resultObject);
    }

    private ResultObject<String> resultObject;

    public DecorationResultException(ResultObject<String> resultObject) {
        this.resultObject = resultObject;
    }

    public static Build build() {
        return new Build();
    }

    public ResultObject<String> getResultObject() {
        return resultObject;
    }


    public static class Build {

    }

}
