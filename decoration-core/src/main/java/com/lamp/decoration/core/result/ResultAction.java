package com.lamp.decoration.core.result;

public interface ResultAction<T> {

	public Class<?> resultClass();
	
	public T success();
	
	public T fail();
	
	public T voidResult();
	
	public T updateResult(Integer data);
	
	public T LongResult(Long data);
	
	public T objectResult(Object data);
	
	public T throwableResult(Throwable data);
	
	public default T resultObject(T data) {
		return data;
	}
	
	public T enumResult(Object data);
}
