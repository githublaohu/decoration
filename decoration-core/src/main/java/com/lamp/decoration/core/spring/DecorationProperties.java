package com.lamp.decoration.core.spring;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
	
	
}
