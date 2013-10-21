package com.justep.javaExt.dao.poAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识字段为数据库字段<br>
 * 此注解出现在Table的full=false时
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	/**
	 * 数据库字段名
	 * 
	 * @return 字段名或者""
	 */
	String value() default "";
}
