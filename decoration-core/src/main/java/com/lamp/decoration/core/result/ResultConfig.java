package com.lamp.decoration.core.result;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultConfig {

    /**
     * 返回结果进行处理的对象 $ 开头表示Class，否则是bean 的名字
     */
    private String resultAction;

    private String defaultExceptionResult;

    private List<String> exceptionResult;


    /**
     * resultAction 真实的对象
     */
    private ResultAction<Object> injection;

    /**
     * 下面集合的对象，会被返回对象进行返回。不进入 decoraiton 的 ResultObject 为了最前兼容与一些特许常见设计
     */
    private Set<String> resultExclude = new HashSet<>();

    private boolean resultErrorStack = false;

    /**
     * 是否打印返回结果
     */
    private boolean printResults;

    /**
     * $ 开头表示Class，否则是bean 的名字 都会从 spring 容器中获取
     */
    private String printResultsObject;


    /**
     * 操作这个大小的内容，将不打印
     */
    private int printBodySize = 1024 * 1024;

    /**
     * 就算 printResults 为 false 也会打印 这些  url
     */
    private Set<String> printUrl;

    /**
     * 就算 printResults 为 false 也会打印 这些  url
     */
    private Set<String> printClasses;

    /**
     * 就算 printResults 为 true 也不会打印 这些 url 的记过
     */
    private Set<String> excludeUrl;

    /**
     * 就算 printResults 为 true 也不会打印 这些 url 的记过
     */
    private Set<String> excludeClasses;


    public List<String> getExceptionResult() {
        return exceptionResult;
    }

    public void setExceptionResult(List<String> exceptionResult) {
        this.exceptionResult = exceptionResult;
    }

    public String getDefaultExceptionResult() {
        return defaultExceptionResult;
    }

    public void setDefaultExceptionResult(String defaultExceptionResult) {
        this.defaultExceptionResult = defaultExceptionResult;
    }

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

    public boolean isResultErrorStack() {
        return resultErrorStack;
    }

    public void setResultErrorStack(boolean resultErrorStack) {
        this.resultErrorStack = resultErrorStack;
    }

    public boolean isPrintResults() {
        return printResults;
    }

    public void setPrintResults(boolean printResults) {
        this.printResults = printResults;
    }

    public String getPrintResultsObject() {
        return printResultsObject;
    }

    public void setPrintResultsObject(String printResultsObject) {
        this.printResultsObject = printResultsObject;
    }

    public int getPrintBodySize() {
        return printBodySize;
    }

    public void setPrintBodySize(int printBodySize) {
        this.printBodySize = printBodySize;
    }

    public Set<String> getPrintUrl() {
        return printUrl;
    }

    public void setPrintUrl(Set<String> printUrl) {
        this.printUrl = printUrl;
    }

    public Set<String> getPrintClasses() {
        return printClasses;
    }

    public void setPrintClasses(Set<String> printClasses) {
        this.printClasses = printClasses;
    }

    public Set<String> getExcludeUrl() {
        return excludeUrl;
    }

    public void setExcludeUrl(Set<String> excludeUrl) {
        this.excludeUrl = excludeUrl;
    }

    public Set<String> getExcludeClasses() {
        return excludeClasses;
    }

    public void setExcludeClasses(Set<String> excludeClasses) {
        this.excludeClasses = excludeClasses;
    }
}
