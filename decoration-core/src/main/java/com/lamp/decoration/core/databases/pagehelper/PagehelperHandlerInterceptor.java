package com.lamp.decoration.core.databases.pagehelper;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.github.pagehelper.PageHelper;
import com.lamp.decoration.core.ConstantConfig;

public class PagehelperHandlerInterceptor implements HandlerInterceptor {

    private ConstantConfig constantConfig;

    private int serverType;

    public PagehelperHandlerInterceptor(ConstantConfig constantConfig) {
        this.constantConfig = constantConfig;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        // request.getHeader(name)
        // request.getParameterMap();
        // request.getQueryString();
        // 从url，请求头，form 获得数据
        String pageSize = null;
        String limitCoordinate = null;
        String orderBy = null;
        if (Objects.nonNull(pageSize) && Objects.nonNull(limitCoordinate)) {
            PageHelper.offsetPage(Integer.valueOf(limitCoordinate), Integer.valueOf(pageSize));
        }
        if (Objects.nonNull(orderBy)) {
            PageHelper.orderBy(orderBy);
        }

        // 判断是简单服务

        // dubbo

        // spring cloud

        return true;
    }
}
