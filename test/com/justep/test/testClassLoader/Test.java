package com.justep.test.testClassLoader;
import java.lang.reflect.InvocationTargetException;


@SuppressWarnings({"rawtypes","unchecked"})
public class Test {
	public static void main(String[] args) throws Throwable{
		
		for(int i=0;i<3;i++){
			MyClassLoader loader=new MyClassLoader("./bin/");
			Class klass =((Class) loader.findClass("com.justep.test.testClassLoader.TestClassLoader"));
			Object obj =klass.newInstance();
			execMethod("getValue",klass, obj);
			System.out.println("对类变量值加一");
			execMethod("addOne",klass, obj);
			execMethod("getValue",klass, obj);
			System.out.println("\n\n\n");
		}
		
	}

	private static Object execMethod(String methodName,Class klass, Object obj)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Object invoke = klass.getMethod(methodName, new Class[]{}).invoke(obj, new Object[]{});
		return invoke;
		
	}

	


}
