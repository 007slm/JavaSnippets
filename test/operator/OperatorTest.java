package operator;

import java.util.HashMap;


public class OperatorTest{
	private HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
	public static void main(String[] args) {
		/*HashMapTest ht = new HashMapTest();
		ht.test();*/
		
		/*System.out.println(2 | 3);  //  或   
		System.out.println(2 & 3);  //  与 
		System.out.println(2 ^ 3);  //  异或
		System.out.println(2 % 3);  //  余数
		System.out.println(2 / 3);  //  商
		System.out.println(2 >> 3);  // 右移
		System.out.println(2 >>> 3); // 右移，高位补0
		System.out.println(Math.pow(2, 3));  // power 幂
		System.out.println(Math.cbrt(8));  // 2 立方根
		System.out.println(Math.ceil(-1.1));  // 大于参数的最小double
		System.out.println(Math.round(-1.1));  // 四舍五入  +0.5取整
*/		
		
		/*Class[] interfaces = HashMap.class.getInterfaces();
		for (Class intef : interfaces) {
			System.out.println(intef.getName());
		}*/
		
		/*Class[] interfaces = HashMapTest.class.getInterfaces();
		for (Class intef : interfaces) {
			System.out.println(intef.getName());
		}
		System.out.println("end");*/
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			System.out.println(hash(i));
		}
		
	}
	
	static int hash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
	
	
}
