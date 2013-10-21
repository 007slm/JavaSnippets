package SimpleDateFormat;

import java.text.DecimalFormat;

public class TestDecimalFormat {
	public static void main(String[] args) {
			DecimalFormat df = new DecimalFormat("#%");
			System.out.println(df.format(0.25));
		}
}

