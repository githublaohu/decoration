package com.lamp.decoration.core.exception;



public class ExceptionResult {

	private String className;
	
	private Class<?> clazz;
	
	private ExceptionResultTypeEnum resultType;
	
	private Integer code;
	
	private String message;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ExceptionResultTypeEnum getResultType() {
		return resultType;
	}

	public void setResultType(ExceptionResultTypeEnum resultType) {
		this.resultType = resultType;
	}

	
	
	
}
