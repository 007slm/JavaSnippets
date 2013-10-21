package com.justep.javaExt.dao.poAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * vo和数据表对应注解.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
	/**
	 * 数据表名
	 * 
	 * @return 表名或者""
	 */
	String name() default "";

	/**
	 * 是否全字段一一对应,此字段为true时,想忽略字段必须使用None标识,此字段为false时,想使用字段必须标识为Field
	 * 
	 * @return true或者false
	 */
	boolean full() default true;
}
