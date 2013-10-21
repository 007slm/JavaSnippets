package SimpleDateFormat;

import java.text.SimpleDateFormat;

public class TestSimpleDateFormat {
	public static void main(String[] args) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		System.out.println(dateFormat.parse("2009-01-01T12:01:00.000"));
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
		System.out.println(dateFormat2.format(dateFormat.parse("2009-01-01T12:01:00.000")));
	}
}	
