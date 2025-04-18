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
package com.lamp.decoration.core.exception;

import java.util.Set;

public class ExceptionConfig {

    private boolean printException = false;

    private Set<Class<?>> exceptionNotPrint;

    private Set<Class<?>> exceptionPrint;


    public boolean isPrintException() {
        return printException;
    }

    public void setPrintException(boolean printException) {
        this.printException = printException;
    }

    public Set<Class<?>> getExceptionNotPrint() {
        return exceptionNotPrint;
    }

    public void setExceptionNotPrint(Set<Class<?>> exceptionNotPrint) {
        this.exceptionNotPrint = exceptionNotPrint;
    }

    public Set<Class<?>> getExceptionPrint() {
        return exceptionPrint;
    }

    public void setExceptionPrint(Set<Class<?>> exceptionPrint) {
        this.exceptionPrint = exceptionPrint;
    }
}
