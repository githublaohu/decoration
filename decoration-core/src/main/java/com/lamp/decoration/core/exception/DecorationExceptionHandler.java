package com.lamp.decoration.core.exception;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lamp.decoration.core.result.ResultObject;

@ControllerAdvice
public class DecorationExceptionHandler {

	private static final Log logger = LogFactory.getLog("decoration exception");
	
	private ExceptionResult defaultExceptionResult;

	private Map<Class<?>, ExceptionResult> exceptionResultMap = new HashMap<>();

	public DecorationExceptionHandler(List<ExceptionResult> exceptionResultList,
			ExceptionResult defaultExceptionResult) {
		this.defaultExceptionResult = defaultExceptionResult;
		for (ExceptionResult exceptionResult : exceptionResultList) {
			exceptionResultMap.put(exceptionResult.getClazz(), exceptionResult);
		}
	}

	@ExceptionHandler(Throwable.class)
	public ModelAndView exceptionHandlerResponseBody(Throwable e, HttpServletResponse response,
			HttpServletRequest httpServletRequest) {
		logger.error( e.getMessage(), e);
		ExceptionResult exceptionResult = exceptionResultMap.get(e.getClass());
		if (Objects.isNull(exceptionResult)) {
			exceptionResult = defaultExceptionResult;
		}
		String errorMessage = null;
        if(e instanceof MethodArgumentNotValidException){
            FieldError fieldError = ((MethodArgumentNotValidException)e).getBindingResult().getFieldError();
            errorMessage = String.format("[参数验证出错] %s <%s>", fieldError.getDefaultMessage(), fieldError.getField());
        }else  if(e instanceof BindException){
            FieldError fieldError = ((BindException)e).getFieldError();
            errorMessage = String.format("[参数验证出错] %s <%s>", fieldError.getDefaultMessage(), fieldError.getField());
        }else {
        	errorMessage = e.getMessage();
        }
		
		// 第一个换行\n
		ResultObject<String> object = new ResultObject<String>(exceptionResult.getCode(),
				exceptionResult.getMessage(), errorMessage);
		return new ModelAndView(new DecorationMappingJackson2JsonView(object));
	}

	static class DecorationMappingJackson2JsonView extends MappingJackson2JsonView {

		private Object object;

		public DecorationMappingJackson2JsonView(Object object) {
			this.object = object;
		}

		protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ByteArrayOutputStream stream = createTemporaryOutputStream();
			writeContent(stream, object);
			writeToResponse(response, stream);
		}
	}

}
