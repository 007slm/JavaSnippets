
public class TestConstractor {
	public TestConstractor(String name) {
		System.out.println("name");
	}
	
	public static void main(String[] args) {
		/**
		 * 编译报错 所以 默认的空参数构造函数在被重载之后 是没有了
		 *TestConstractor tc =new TestConstractor(); 
		 */
		
		/**
		 * 输入结果是1 只有一个有参数的构造函数了 
		 */
		System.out.println(TestConstractor.class.getConstructors().length);
		
		
		
	}
}
