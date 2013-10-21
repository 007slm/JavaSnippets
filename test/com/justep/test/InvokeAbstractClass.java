package com.justep.test;

public class InvokeAbstractClass {
	public static void main(String[] args) {
		TestAbstractClass.sayHello();
		
		
		TestAbstractClass tc =new TestAbstractClass(){
			
		};
		
		tc.sayHello();
		
		
		new TestInterface() {
			public void sayHello() {
				// TODO Auto-generated method stub
				System.out.println("say Hello in  interface");
			}
		}.sayHello();
		
	}
}
