public class TestClassLoader {
	public static int i=0;
	
	static {
		System.out.println("静态方法块只被调用一次吗？");
	}
	
	public void getValue(){
		System.out.println("当前类变量i的值是"+i);
	}
	
	public int addOne() throws Throwable{
		i++;
		return i;
	}
}
