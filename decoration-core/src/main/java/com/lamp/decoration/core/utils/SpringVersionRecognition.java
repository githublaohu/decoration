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
package com.lamp.decoration.core.utils;

import org.springframework.core.SpringVersion;
import org.springframework.util.StringUtils;

/**
 * @author laohu
 */
public class SpringVersionRecognition {

    private static final boolean isJakarta = getJakarta();

    private static boolean getJakarta(){
        String springVersion = SpringVersion.getVersion();
        String[] value = StringUtils.split(springVersion, ".");
        Integer version = Integer.valueOf(value[0]);
        return version > 5;
    }

    public static boolean isJakarta() {
        return isJakarta;
    }
}
