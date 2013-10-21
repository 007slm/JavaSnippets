
public class TestHasHcode {
	/**
	 * hashcode 相同 不一定equals 
	 * equals 那么hashcode一定相等
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("hello".hashCode());
		Integer helloHashCode =99162322;
		System.out.println(helloHashCode.hashCode());
		System.out.println("hello".hashCode()==helloHashCode.hashCode());
		System.out.println("hello".equals(helloHashCode));
	}
}
