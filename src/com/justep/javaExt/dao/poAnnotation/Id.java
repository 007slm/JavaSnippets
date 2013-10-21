package com.justep.javaExt.dao.poAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * id字段对应,一个vo只允许出现一个此标识
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
	/**
	 * 字段名或者""
	 * 
	 * @return 字段名或者""
	 */
	String value() default "";
}
