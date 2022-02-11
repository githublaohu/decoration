package com.lamp.decoration.core.databases;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询限制，默认50条
 * 如果不加上Querylimit，那么对于所有返回结果进行识别，如果返回结果大于50条，直接报错。
 * 传递的limit值，大于limit值
 * 
 * @author laohu
 *
 */

@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Querylimit {

	/**
	 * 如果不传递分页条件，会自动加上
	 * 如果是 -1 那么不限制
	 * @return
	 */
	int defaultLimit() default 50;
	
	/**
	 * 传递的limit值，大于defaultLimit值,如果设为true，传递的值不能大于defaultLimit
	 */
	boolean priority() default false;
}
