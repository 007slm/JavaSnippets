import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;


public class PrintClassLoader {
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, URISyntaxException {
		//给父classLader添加url
		URLClassLoader parent =new URLClassLoader(new URL[0], PrintClassLoader.class.getClassLoader());
		
		addURLM.invoke(parent, new Object[] { new URL("file:/E:/x5_dev/x5.2.1.1900/runtime/DocServer/WEB-INF/lib/")});
		//测试是否添加了
		URL[] purls =parent.getURLs();
		for (int i = 0; i < purls.length; i++) {
			URL url = purls[i];
		}
		
		URLClassLoader cl =new URLClassLoader(new URL[0], parent);
		
		Properties properties = new Properties();
		
		
		/*URL[] urls =cl.getURLs();
		for (int i = 0; i < urls.length; i++) {
			URL url = urls[i];
		}*/
		Thread.currentThread().setContextClassLoader(cl);
		Enumeration<URL> res = cl.getResources("META-INF/spring.schemas");
		while (res.hasMoreElements()) {
			URL url = res.nextElement();
			InputStream is = null;
			try {
				URLConnection con = url.openConnection();
				System.out.println(url);
				con.setUseCaches(false);
				is = con.getInputStream();
				properties.load(is);
			}
			finally {
				if (is != null) {
					is.close();
				}
			}
		}
		//printURLs(cl);
	}
	
	private static void printURLs(ClassLoader cl){
		String currentClValue =cl.toString();
		if(cl instanceof URLClassLoader){
			URL[] pclUrls =((URLClassLoader)cl).getURLs();
			for (int i = 0; i < pclUrls.length; i++) {
				URL url = pclUrls[i];
				try {
					System.out.println("classLoader["+currentClValue+"]有的url是："+url.toURI().toString());
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
			if(pclUrls.length==0){
				System.out.println("classLoader["+currentClValue+"]没有的url");
			}
		}else{
			System.out.println("classLoader["+currentClValue+"]不是URLClassLoader");
		}
		ClassLoader parent = cl.getParent();
		if(parent==null){
			System.out.println("classLoader["+currentClValue+"]是bootClass 不用在继续查找了 ");
			return;
		}
		
		if(!currentClValue.equals(parent.toString())){
			System.out.println("classLoader["+currentClValue+"]的父classLoader是：["+parent.toString()+"]");
			printURLs(parent);
		}
		
	}
	
	private static Method addURLM = initAddMethod();


    /**
     * 初始化addUrl 方法.
     * @return 可访问addUrl方法的Method对象
     */
    private static Method initAddMethod() {
        try {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
            add.setAccessible(true);
            return add;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
