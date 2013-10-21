public class TestZHISHU {
	public static void zs(int n) {
		for (int i = 3; i <= n; i++) {
			boolean iszs = true;
			for (int j = 2; j <= Math.sqrt(i); j++) {
				if (i % j == 0) {
					iszs = false;
					break;
				}
			}
			if (iszs) {
				System.out.print(i + " ");
			}
		}
	}
	public static void main(String[] args) {
		//zs(100);
		System.out.println(Math.sqrt(100));
		System.out.println(Math.sqrt(99));
	}
}	
