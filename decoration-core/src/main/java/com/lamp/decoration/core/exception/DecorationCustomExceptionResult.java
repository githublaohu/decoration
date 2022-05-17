package com.lamp.decoration.core.exception;

import java.util.List;

public class DecorationCustomExceptionResult implements CustomExceptionResult {
	ExceptionResult exceptionResult;

    {
        exceptionResult = new ExceptionResult();
        exceptionResult.setCode(500);
        exceptionResult.setMessage("系统出现异常");
        exceptionResult.setResultType(ExceptionResultTypeEnum.JSON);

    }

    @Override
    public ExceptionResult getDefaultExceptionResult() {
        return exceptionResult;
    }

    @Override
    public List<ExceptionResult> getExceptionResultList() {
        return null;
    }
}
