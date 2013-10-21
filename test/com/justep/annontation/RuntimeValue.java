package com.justep.annontation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//默认是RetentionPolicy.Class，只能在编译期使用，在runtime无法通过反射获得
@Retention(RetentionPolicy.RUNTIME)
// 默认能修饰所有的ElementType中枚举的所有内容，这里让他只能修饰类型和方法
@Target({ElementType.TYPE, ElementType.METHOD}) 
public @interface RuntimeValue {
	String value();
	String copyRight();
}
