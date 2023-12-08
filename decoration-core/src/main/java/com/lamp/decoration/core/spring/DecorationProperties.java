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

import com.lamp.decoration.core.ConstantConfig;
import com.lamp.decoration.core.spring.plugs.DecorationCorsConfiguration;
import com.lamp.decoration.core.spring.plugs.Swagger2Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

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
	
	private String resultObject;
	
	private boolean enable;

	private boolean corsEnable = false;

	private ConstantConfig constantConfig  = new ConstantConfig();

	private List<DecorationCorsConfiguration> corsConfigurationList;

	private Swagger2Configuration swagger2Config;


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

	public String getResultObject() {
		return resultObject;
	}

	public void setResultObject(String resultObject) {
		this.resultObject = resultObject;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Swagger2Configuration getSwagger2Config() {
		return swagger2Config;
	}

	public void setSwagger2Config(Swagger2Configuration swagger2Config) {
		this.swagger2Config = swagger2Config;
	}

	public boolean isCorsEnable() {
		return corsEnable;
	}

	public void setCorsEnable(boolean corsEnable) {
		this.corsEnable = corsEnable;
	}
}
