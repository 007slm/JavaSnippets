import java.io.File;
import java.net.MalformedURLException;


public class TestURL {
	public static void main(String[] args) throws MalformedURLException {
		String url ="E:/x5_dev/x5.2.1.1900/apache-tomcat/webapps/../../runtime/DocServer/WEB-INF/lib";
		//System.out.println(new URL(url));
		System.out.println(new File(url).toURL());
	}
}
