package com.lamp.decoration.core.spring;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 完成一下功能，decoration基本结束了
 * <ol>防止重复提交—— 等下ledis，两天就可以完成</ol>
 * <ol>query 字段过滤,支持其他RPC,openfeign（拦截器-RequestInterceptor） ZoneAvoidanceRule</ol>
 * <ol>ValidationUtils </ol>
 * 
 * @author laohu
 *
 */
@ConfigurationProperties(prefix = DecorationProperties.DECORATION_PREFIX)
public class DecorationProperties {
	
	public static final String  DECORATION_PREFIX = "decoration";

	private String defaultExceptionResult;
	
	private List<String> exceptionResult;

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
}
