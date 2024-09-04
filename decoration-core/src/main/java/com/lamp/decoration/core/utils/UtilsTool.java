package com.lamp.decoration.core.utils;


import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Objects;

import com.lamp.decoration.core.result.ResultObject;


public class UtilsTool {


    public static boolean isCollection(Class<?> clazz) {
        Class[] clazzArray = clazz.getInterfaces();
        if(Objects.isNull(clazzArray) || clazzArray.length == 0 ){
            return false;
        }
        for (Class<?> c : clazz.getInterfaces()) {
            if (c.equals(Collection.class)) {
               return true;
            }
            if(isCollection(c)){
                return true;
            }
        }
        return false;
    }

    public static Class<?> getaClass(Method method) throws Exception {
        Class<?> clazz = null;
        if (Objects.equals(method.getReturnType(), ResultObject.class)) {
            ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
            Type type = parameterizedType.getActualTypeArguments()[0];
            try {
                clazz = (Class<?>) ((ParameterizedType)type).getRawType();
            }catch (Throwable t){
                t.printStackTrace();
            }
            if(Objects.isNull(clazz)){
                try {
                    clazz = (Class<?>) type;
                }catch (Throwable t){
                    t.printStackTrace();
                }
            }
        } else {
            clazz = method.getReturnType();
        }
        return clazz;
    }

}
