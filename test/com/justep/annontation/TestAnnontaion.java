package com.justep.annontation;

import java.lang.reflect.Method;

@RuntimeValue(value="valuedsfsdfsdfds",copyRight="2011")

public class TestAnnontaion {
	
	public static void main(String[] args) {
		TestAnnontaion ta =new TestAnnontaion();
		ta.testAAA();
	}
	
	@RuntimeValue(value="sdafsdfdsfsdddddddddddddddddddddddddddddd",copyRight="2011dddddddddddddddddddddd")
	
	public String testAAA(){
		
		System.out.println(TestAnnontaion.class.getAnnotation(RuntimeValue.class).copyRight());
		
		System.out.println(TestAnnontaion.class.getAnnotation(RuntimeValue.class).value());
		
		System.out.println(this.getClass().getAnnotation(RuntimeValue.class).value());
		
		try {
			Method method = this.getClass().getMethod("testAAA", new Class[]{});
			
			System.out.println(method.getAnnotation(RuntimeValue.class).value());
			System.out.println(method.getAnnotation(RuntimeValue.class).copyRight());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
