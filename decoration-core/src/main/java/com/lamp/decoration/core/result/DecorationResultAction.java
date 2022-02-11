package com.lamp.decoration.core.result;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.decoration.core.databases.queryClauseInte.QueryClauseCentre;

public class DecorationResultAction implements ResultAction<ResultObject<Object>> {

	private Map<Class<?>, Method> enumInMethodMap = new ConcurrentHashMap<>();
	
	@Override
	public Class<?> resultClass() {
		return ResultObject.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultObject success() {
		return ResultObject.success();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultObject fail() {
		return ResultObject.fail();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultObject voidResult() {
		return ResultObject.success();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultObject updateResult(Integer data) {
		return ResultObject.distinguishUpdate( data);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultObject LongResult(Long data) {
		return ResultObject.success(data);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultObject objectResult(Object data) {
		ResultObject<Object> resultObject = ResultObject.success(data);
		QueryClauseCentre.pageData(resultObject);
		return resultObject;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultObject throwableResult(Throwable throwable) {
		return new ResultObject(300, "异常", throwable.getMessage());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultObject enumResult(Object data) {
			try {
				Class<?> clazz = data.getClass();
				Method method = enumInMethodMap.get(clazz);
				if (Objects.isNull(method)) {
				method = clazz.getMethod("getResultObject", new Class[0]);
				method = enumInMethodMap.computeIfAbsent(clazz, k -> {
					Method mewMethod = null;
					try {
						mewMethod = clazz.getMethod("getResultObject", new Class[0]);
					} catch (NoSuchMethodException | SecurityException e) {
						new RuntimeException(e);
					}
					return mewMethod;
				});
				}
				return (ResultObject)method.invoke(data);
			} catch (Exception e1) {
				return this.throwableResult(e1);
			}
	}

	

}
