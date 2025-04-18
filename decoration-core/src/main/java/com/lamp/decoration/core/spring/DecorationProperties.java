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

package com.lamp.decoration.core.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.lamp.decoration.core.ConstantConfig;
import com.lamp.decoration.core.exception.ExceptionConfig;
import com.lamp.decoration.core.result.ResultConfig;

/**
 * @author laohu
 */
@ConfigurationProperties(prefix = DecorationProperties.DECORATION_PREFIX)
public class DecorationProperties {

    public static final String DECORATION_PREFIX = "decoration";

    private boolean enabled;

    private ConstantConfig constantConfig = new ConstantConfig();

    private ResultConfig resultConfig = new ResultConfig();

    private ExceptionConfig errorConfig = new ExceptionConfig();

    private PlugsConfig plugsConfig = new PlugsConfig();

    public ExceptionConfig getErrorConfig() {
        return errorConfig;
    }

    public void setErrorConfig(ExceptionConfig errorConfig) {
        this.errorConfig = errorConfig;
    }

    public ConstantConfig getConstantConfig() {
        return constantConfig;
    }

    public void setConstantConfig(ConstantConfig constantConfig) {
        this.constantConfig = constantConfig;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public ResultConfig getResultConfig() {
        return resultConfig;
    }

    public void setResultConfig(ResultConfig resultConfig) {
        this.resultConfig = resultConfig;
    }

    public PlugsConfig getPlugsConfig() {
        return plugsConfig;
    }

    public void setPlugsConfig(PlugsConfig plugsConfig) {
        this.plugsConfig = plugsConfig;
    }
}
