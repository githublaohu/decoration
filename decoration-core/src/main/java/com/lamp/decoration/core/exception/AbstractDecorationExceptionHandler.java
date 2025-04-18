package com.lamp.decoration.core.exception;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lamp.decoration.core.result.ResultAction;

public abstract class AbstractDecorationExceptionHandler {


    private static final Log logger = LogFactory.getLog("decoration exception");

    private ResultAction resultAction;

    private ExceptionConfig exceptionConfig;

    public void setExceptionConfig(ExceptionConfig exceptionConfig) {
        this.exceptionConfig = exceptionConfig;
    }

    public void setResultAction(ResultAction resultAction) {
        this.resultAction = resultAction;
    }


    private boolean isPrint(Exception ex) {
        if (this.exceptionConfig.getExceptionPrint().contains(ex.getClass())) {
            return true;
        }
        if (this.exceptionConfig.getExceptionNotPrint().contains(ex.getClass())) {
            return false;
        }
        return this.exceptionConfig.isPrintException();
    }

    protected ModelAndView handlerException(Exception ex) {
        if (this.isPrint(ex)) {
            logger.error(ex);
        }
        return this.buildModelAndView(ex);
    }

    protected ModelAndView buildModelAndView(Throwable e) {
        return new ModelAndView(new DecorationMappingJackson2JsonView(resultAction.throwableResult(e)));
    }

    static class DecorationMappingJackson2JsonView extends MappingJackson2JsonView {

        private final Object object;

        public DecorationMappingJackson2JsonView(Object object) {
            this.object = object;
        }

        protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
            ByteArrayOutputStream stream = createTemporaryOutputStream();
            writeContent(stream, object);

            response.setContentType(getContentType());
            response.setContentLength(stream.size());
            ServletOutputStream out = response.getOutputStream();
            stream.writeTo(out);
            out.flush();
        }
    }

}
