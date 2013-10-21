import java.io.UnsupportedEncodingException;

import org.loon.test.encoding.ParseEncoding;

public class StringEncodeTester {
	public static void main(String[] args) {
		ParseEncoding parse;
		parse = new ParseEncoding();
		System.out.println("中国大陆：");
		System.out.println("测试字符串，编码格式=" + parse.getEncoding("?μ?èˉ?é??????????1 ".getBytes()));
		/*System.out.println("测试站点，编码格式="
				+ parse.getEncoding("http://www.baidu.com"));
		System.out.println();
		System.out.println("中国台湾：");
		System.out
				.println("测试字符串，编码格式=" + parse.getEncoding("璧锋X5鍙栧洖瀵嗙爜 ".getBytes()));
		System.out.println("测试站点，编码格式="
				+ parse.getEncoding("http://tw.yahoo.com/"));
		System.out.println("测试站点(繁体字，UTF编码)，编码格式="
				+ parse.getEncoding("http://www.javaworld.com.tw/jute"));
		System.out.println();
		System.out.println("日本：");
		System.out
				.println("测试字符串，编码格式=" + parse.getEncoding("璧锋X5鍙栧洖瀵嗙爜 ".getBytes()));
		System.out.println("测试站点，编码格式="
				+ parse.getEncoding("http://www.4gamer.net"));
		System.out.println();
		System.out.println("自称蚩尤后代那群……：");
		System.out.println("测试站点，编码格式="
				+ parse.getEncoding("http://www.easyjava.co.kr/"));*/
		try {
			System.out.println(new String(new String("测试邮件主题".getBytes("utf-8"),"ISO8859-1")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
