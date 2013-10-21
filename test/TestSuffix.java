
public class TestSuffix {
	public static void main(String[] args) {
		Long id =123456L;
		System.out.println(String.format("%016X", 123456).replaceAll("\\p{XDigit}{2}", "/$0"));
		
	}
}
