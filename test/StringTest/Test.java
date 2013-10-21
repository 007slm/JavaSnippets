package StringTest;

public class Test {
	/**
	 * 测试String.Intern的用法
	 * 
	 * 
	 * 因为池的内容是编译时写到class文件中去的，运行的时候jvm会加载到内存里。运行的时候你可以动态的把一些串加入到常量池中去，intern提供了一个途径
	 * 
	 * 当调用 intern 方法时，如果池已经包含一个等于此 String 对象的字符串（该对象由 equals(Object) 方法确定），则返回池中的字符串。
	 * 否则，将此 String 对象添加到池中，并且返回此 String 对象的引用。  


	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String str ="hello world";
		String str2 =args[0];
		String str2Intern=args[0].intern();
		System.out.println(str2);
		if("hello world"==str){
			System.out.println("字符串是存常量池的 ，内存地址相等");
		}
		System.out.println("外部输入的字符串是运行时的，那么这个字符串和常量池中的字符串地址"+(str2 == str?"":"不")+"相等");
	}
}
