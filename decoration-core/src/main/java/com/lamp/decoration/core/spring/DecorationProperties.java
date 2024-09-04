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

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.lamp.decoration.core.ConstantConfig;
import com.lamp.decoration.core.result.ResultConfig;
import com.lamp.decoration.core.spring.plugs.DecorationCorsConfiguration;
import com.lamp.decoration.core.spring.plugs.Swagger2Configuration;
import com.lamp.decoration.core.spring.plugs.Swagger3Configuration;

/**
 * 
 * @author laohu
 *
 */
@ConfigurationProperties(prefix = DecorationProperties.DECORATION_PREFIX)
public class DecorationProperties {
	
	public static final String  DECORATION_PREFIX = "decoration";

	private String defaultExceptionResult;

	private List<String> exceptionResult;
	
	private boolean enabled;

	private boolean corsEnable = false;

	private ConstantConfig constantConfig  = new ConstantConfig();

	private List<DecorationCorsConfiguration> corsConfigurationList;

	private Swagger2Configuration swagger2;

	private Swagger3Configuration swagger3;

	private ResultConfig resultConfig = new ResultConfig();

	public ConstantConfig getConstantConfig() {
		return constantConfig;
	}

	public void setConstantConfig(ConstantConfig constantConfig) {
		this.constantConfig = constantConfig;
	}

	public List<DecorationCorsConfiguration> getCorsConfigurationList() {
		return corsConfigurationList;
	}

	public void setCorsConfigurationList(
			List<DecorationCorsConfiguration> corsConfigurationList) {
		this.corsConfigurationList = corsConfigurationList;
	}



	public String getDefaultExceptionResult() {
		return defaultExceptionResult;
	}

	public void setDefaultExceptionResult(String defaultExceptionResult) {
		this.defaultExceptionResult = defaultExceptionResult;
	}

	public List<String> getExceptionResult() {
		return exceptionResult;
	}

	public void setExceptionResult(List<String> exceptionResult) {
		this.exceptionResult = exceptionResult;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Swagger2Configuration getSwagger2() {
		return swagger2;
	}

	public void setSwagger2(Swagger2Configuration swagger2) {
		this.swagger2 = swagger2;
	}

	public Swagger3Configuration getSwagger3() {
		return swagger3;
	}

	public void setSwagger3(Swagger3Configuration swagger3) {
		this.swagger3 = swagger3;
	}

	public boolean isCorsEnable() {
		return corsEnable;
	}

	public void setCorsEnable(boolean corsEnable) {
		this.corsEnable = corsEnable;
	}

	public ResultConfig getResultConfig() {
		return resultConfig;
	}

	public void setResultConfig(ResultConfig resultConfig) {
		this.resultConfig = resultConfig;
	}
}
