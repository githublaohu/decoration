package com.lamp.decoration.core.result;

/**
 * 1. 返回 null ， 从上下文
 * 2. 返回 ResultObject<Object>
 * @author laohu
 */
public interface ResultObjectInterface<T> {

    ResultObject<T> getResultObject();
}
