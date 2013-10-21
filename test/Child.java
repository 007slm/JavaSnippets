import java.lang.reflect.Method;

/**
 * 
 * @author 007slm
 * 
 * 我们发现Child继承自Parent，而Parent是abstract class，而Child并没有overriding掉Parent中的T get(T obj),这样肯定会报错
 * （其实T这种java的泛型机制是在编译器做验证的，所有的泛型在编译后都被编译器擦除,不能直接擦除的变成了Object类型）
 * 为了解决这个问题，编译器做了一些工作，当发现我们指定类T的类型时，编译器会在字节码中添加一个桥接方法来overriding掉父类的方法，
 * 可以通过查看编译器的字节码可以发现，也可以通过反射来查看类中的方法： 
 * 所以有有了Method类的isBridge()的函数的作用。
 * 
 * 所以有了这个概念
   	Covariant Return Type 
	翻译成中文是协变式返回类型，在介绍这个概念之前先介绍下jdk1.4和jdk1.5中关于函数签名的定义： 
	In Java 1.4, and earlier, one method can override another if the signatures match exactly. 
	In Java 5, a method can override another if the arguments match exactly but the return type of the overriding method, if it is a subtype of the return type of the other method. 
	也就是说，在jdk1.5中，对于“重写”的判断放宽了条件，子类中方法返回的类型是父类中方法返回类型的子类也算是重写.

 *
 */
public class Child extends Parent<String>{
	public String get(String str){
		return null;
	}
	
	public static void main(String[] args) {
		Method[] methods =Child.class.getMethods();
		for(Method method:methods){
			/**
			 * 结果告诉我们有2个get函数
			 * 
			 * 一个是bridge的 一个不是
			 * result是
			 	class java.lang.String||false
				class java.lang.Object||true
			 */
			if(method.getName().equals("get")){
				System.out.println(method.getReturnType()+"||"+method.isBridge());
			}
		}
	}
}

abstract class Parent<T>{
	abstract T get(T t);
}
