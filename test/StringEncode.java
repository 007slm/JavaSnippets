import java.io.UnsupportedEncodingException;


public class StringEncode {
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(new String(new String("中文.doc".getBytes("utf-8"), "iso8859-1").getBytes("iso8859-1"),"utf-8"));
	}
}
