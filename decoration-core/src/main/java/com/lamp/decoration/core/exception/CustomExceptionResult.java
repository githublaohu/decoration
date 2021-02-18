package com.lamp.decoration.core.exception;

import java.util.List;

public interface CustomExceptionResult {

	public ExceptionResult getDefaultExceptionResult() ;
	
	public List<ExceptionResult> getExceptionResultList();
}
