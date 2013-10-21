/**
 * 
 * @author 007slm
 * @desc 说明构造方法中可以用this
 * 当进入构造方法其实对象已经创建只不过在进行创建这个对象的默认动作（构造方法中的逻辑）
 *	
 */
public class TestThisInConstractor {
	public TestThisInConstractor() {
		System.out.println(this);
		System.out.println(this.getClass());
	}
	public static void main(String[] args) {
		TestThisInConstractor tc =new TestThisInConstractor();
		System.out.println(tc);
	}
}
