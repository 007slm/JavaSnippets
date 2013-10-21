import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;


public class ClassPathPrint {
	
	static Log logger =new ClassPathPrint.Log();
	
	static class Log{
		void debug(String info){
			System.out.println(info);
		}
	}
	public static void main(String[] args) {
		printURLs(Thread.currentThread().getContextClassLoader());
	}
	private static void printURLs(ClassLoader cl){
		String currentClValue =cl.toString();
		if(cl instanceof URLClassLoader){
			URL[] pclUrls =((URLClassLoader)cl).getURLs();
			for (int i = 0; i < pclUrls.length; i++) {
				URL url = pclUrls[i];
				try {
					logger.debug("classLoader["+currentClValue+"]有的url是："+url.toURI().toString());
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
			if(pclUrls.length==0){
				logger.debug("classLoader["+currentClValue+"]没有的url");
			}
		}else{
			logger.debug("classLoader["+currentClValue+"]不是URLClassLoader");
			return;
		}
		ClassLoader parent = cl.getParent();
		if(parent==null){
			logger.debug("classLoader["+currentClValue+"]是bootClass 不用在继续查找了 ");
			return;
		}
		
		if(!currentClValue.equals(parent.toString())){
			logger.debug("classLoader["+currentClValue+"]的父classLoader是：["+parent.toString()+"]");
			printURLs(parent);
		}
		
	}
}
