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
package com.lamp.decoration.core.exception;

import java.util.List;

public interface CustomExceptionResult {

	/**
	 * 默认返回，如果异常类没有对应的ExceptionResult，会使用默认的ExceptionResult
	 * @return
	 */
	public ExceptionResult getDefaultExceptionResult() ;
	
	/**
	 * 异常返回
	 * @return
	 */
	public List<ExceptionResult> getExceptionResultList();
}
