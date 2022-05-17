package com.lamp.decoration.example;

import com.lamp.decoration.core.exception.CustomExceptionResult;
import com.lamp.decoration.core.exception.ExceptionResult;

import java.util.List;

public class ExampleExceptionResult implements CustomExceptionResult {

    ExceptionResult exceptionResult = new ExceptionResult();

    @Override
    public ExceptionResult getDefaultExceptionResult() {
        exceptionResult.setCode(400);
        exceptionResult.setMessage("系统异常");
        return exceptionResult;
    }

    @Override
    public List<ExceptionResult> getExceptionResultList() {
        return null;
    }
}
