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

import com.lamp.decoration.core.result.ResultAction;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * @author hahaha
 */
public class JakartaDecorationExceptionHandler implements CompatibleHandlerInterceptor {

    private static final Log logger = LogFactory.getLog("decoration exception");

    private final ResultAction resultAction;

    public JakartaDecorationExceptionHandler(ResultAction resultAction) {
        this.resultAction = resultAction;
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView exceptionHandlerResponseBody(Throwable e, HttpServletResponse response,
                                                     HttpServletRequest httpServletRequest) {
        logger.error(e.getMessage(), e);
        return new ModelAndView(new DecorationMappingJackson2JsonView(resultAction.throwableResult(e)));
    }


    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error(ex.getMessage(), ex);
        return new ModelAndView(new DecorationMappingJackson2JsonView(resultAction.throwableResult(ex)));
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
