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
package com.lamp.decoration.core.result;

import com.lamp.decoration.core.exception.ExceptionResult;

import java.util.List;

public interface ResultAction<T> {

	public Class<?> resultClass();
	
	public T success();
	
	public T fail();
	
	public T voidResult();
	
	public T updateResult(Integer data);
	
	public T longResult(Long data);
	
	public T objectResult(Object data);
	
	public T throwableResult(Throwable data);
	
	public default T resultObject(T data) {
		return data;
	}
	
	public T enumResult(Object data);

	public void setDefaultExceptionResult(ExceptionResult exceptionResult);

	public void setExceptionResultList(List<ExceptionResult> exceptionResultList);
}
