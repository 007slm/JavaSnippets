package utilStack;

import java.lang.reflect.Method;
import java.util.Stack;


public class TestStack {
	public static void main(String[] args) {
		System.out.println("java.utlis.Stack里面有的方法如下");
		Method[] methods =  Stack.class.getDeclaredMethods();
		for (Method method : methods) {
			String name = method.getName();
			System.out.println(name);
		}
		
		Stack<String> s = new Stack<String>();
		s.push("1");
		s.push("2");
		s.push("3");
		s.push("4");
		s.push("5");
		s.push("6");
		s.push("7");
		//不能用这个循环里面pop 这样会有快速迭代失败的问题
		//for (String string : s) 
		for (int i=1;i<7;i++) {
			System.out.println("7的位置是："+s.search("7"));
			System.out.println("栈顶元素是"+s.peek());
			System.out.println("弹出一个元素,这个元素是："+s.pop());
		}
	}
}
