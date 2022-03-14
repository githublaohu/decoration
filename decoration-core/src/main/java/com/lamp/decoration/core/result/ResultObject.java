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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 三个默认执行的处理
 * ResultObject <BR>
 *
 * @author laohu
 * @version 1.0.0: ResultObject.java,v 0.1 2020年08月25 01:07
 */
public class ResultObject<T> {

    private static final ResultObject<String> DEFAULT_SUCCESS = new ResultObject<String>(200, "执行成功");

    private static final ResultObject<String> DEFAULT_FAIL = new ResultObject<String>(20000, "执行失败");

    private static final ResultObject<String> DEFAULT_UPATE_FAIL = new ResultObject<String>(20000, "修改数据失败");

    private Integer code;

    private String message;

    // @JsonInclude(value = Include.NON_NULL)
    private T data;

    @JsonInclude(value = Include.NON_NULL)
    private String errorMessages;

    @JsonInclude(value = Include.NON_NULL)
    private Integer currentPage;

    @JsonInclude(value = Include.NON_NULL)
    private Integer pageSize;

    @JsonInclude(value = Include.NON_NULL)
    private Long total;

    public static ResultObject<String> success() {
        return DEFAULT_SUCCESS;
    }

    public static <T> ResultObject<T> success(T data) {
        return new ResultObject<T>(200, "执行成功", data);
    }

    public static <T> ResultObject<T> getResultObject(int code, T data) {
        return new ResultObject<T>(code, "执行成功", data);
    }

    public static ResultObject<String> getResultObjectMessgae(int code, String message) {
        return new ResultObject<String>(code, message);
    }

    public static ResultObject<String> fail() {
        return DEFAULT_FAIL;
    }

    public static ResultObject<String> distinguishUpdate(int i) {
        return i > 0 ? DEFAULT_SUCCESS : DEFAULT_UPATE_FAIL;
    }

    public ResultObject(Integer code, String message) {
        if (code == null) {
            this.code = 200;
        } else {
            this.code = code;
        }
        this.message = message;
    }

    public ResultObject(Integer code, String message, T data) {
        if (code == null) {
            this.code = 200;
        } else {
            this.code = code;
        }
        this.message = message;
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public ResultObject(Integer code, String message, T data, String errorMessages, Integer currentPage,
        Integer pageSize, Long total) {
        if (code == null) {
            this.code = 200;
        } else {
            this.code = code;
        }
        this.message = message;
        this.data = data;
        this.errorMessages = errorMessages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        // 适配别人对象？
        if (data instanceof com.github.pagehelper.Page) {
            com.github.pagehelper.Page<T> page = (com.github.pagehelper.Page<T>)data;
            this.total = page.getTotal();
            this.pageSize = page.getPageSize();
            this.currentPage = page.getPageNum();
        } 
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}