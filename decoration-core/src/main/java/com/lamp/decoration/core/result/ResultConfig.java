package com.lamp.decoration.core.result;

import java.util.HashSet;
import java.util.Set;

public class ResultConfig {

    /**
     * 返回结果进行处理的对象 $ 开头表示Class，否则是bean 的名字
     */
    private String resultAction;

    /**
     * resultAction 真实的对象
     */
    private ResultAction<Object> injection;

    /**
     * 下面集合的对象，会被返回对象进行返回。不进入 decoraiton 的 ResultObject
     * 为了最前兼容与一些特许常见设计
     */
    private Set<String> resultExclude = new HashSet<>();

    /**
     * 是否打印返回结果
     */
    private boolean printResults;

    /**
     * $ 开头表示Class，否则是bean 的名字
     * 都会从 spring 容器中获取
     */
    private String printResultsObject;


    /**
     * 操作这个大小的内容，将不打印
     */
    private int printBodySize = 1024*1024;

    /**
     * 打印 那些 url
     */
    private Set<String> printUrl;

    /**
     * 打印 那些 接口
     */
    private Set<String> printClasses;

    /**
     * 打印 那些 url
     */
    private Set<String> excludeUrl;

    /**
     * 打印 那些 接口
     */
    private Set<String> excludeClasses;

    public String getResultAction() {
        return resultAction;
    }

    public void setResultAction(String resultAction) {
        this.resultAction = resultAction;
    }

    public ResultAction<Object> getInjection() {
        return injection;
    }

    public void setInjection(ResultAction<Object> injection) {
        this.injection = injection;
    }

    public Set<String> getResultExclude() {
        return resultExclude;
    }

    public void setResultExclude(Set<String> resultExclude) {
        this.resultExclude = resultExclude;
    }
}
