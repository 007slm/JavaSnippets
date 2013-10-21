package com.justep.slm.test.visitorPattern;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestVisitor {
	/**
	 *   本例子模拟  采摘不同果子
	 *   
	 *   对象群 是果子
	 *   
	 *   动作现在模拟了一个是采摘动作
	 *   
	 *   果类的基类里面
	 *   采用了反射来动态accept
	 *   这样不用在每个果类里面实现方法
	 *   
	 *   accept(Visitor v){
	 *      v.visit(this);
	 *   }
	 */
	public static void main(String[] args) {
		Fruit a = new Apple();
		Fruit o = new Orange();
		Fruit b =new Banana();
		
		Visitor v = new PickVisitor();
		a.accept(v);
		o.accept(v);
		b.accept(v);
		
		
		
	}
}

class Fruit{
	void accept(Visitor v){
		Method[] methods = v.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if ("visit".equals(method.getName())&&method.getParameterTypes().length==1&&this.getClass().isAssignableFrom(method.getParameterTypes()[0])) {
				try {
					method.invoke(v, new Object[]{this});
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	};
}

class Apple extends Fruit{
}

class Orange extends Fruit{
}
class Banana extends Fruit{
}

interface Visitor{
	void visit(Apple fruit);
	void visit(Orange fruit);
	void visit(Banana fruit);
}

class PickVisitor implements Visitor{

	public void visit(Apple fruit) {
		System.out.println("摘苹果");
	}

	public void visit(Orange fruit) {
		System.out.println("摘橘子");
	}

	public void visit(Banana fruit) {
		System.out.println("摘香蕉");
	}
}

