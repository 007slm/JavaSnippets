import java.io.Serializable;

/**  
 * 能应对大多数情况的单例实现
 * 特别针对jdk5一下版本推出，否则jdk5以上版本dcl的懒汉式或者eagger模式都可以  
 */  
public class SingletonKerrigan implements Serializable {   
    
	//static final SingletonKerrigan INSTANCE = new SingletonKerrigan();
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8451260632149512727L;

	private static class SingletonHolder {   
        /**  
         * 单例对象实例  
         */
        static final SingletonKerrigan INSTANCE = new SingletonKerrigan();   
    }
    
    public static SingletonKerrigan getInstance() {   
        return SingletonHolder.INSTANCE;
    }   
    
    /**  
     * private的构造函数用于避免外界直接使用new来实例化对象  
     */  
    private SingletonKerrigan() {
    	System.out.println("创建");
    }   
    
    /**  
     * readResolve方法应对单例对象被序列化时候  
     */  
    private Object readResolve() {   
        return getInstance();   
    }
    
    public static void main(String[] args) {
		SingletonKerrigan instance =SingletonKerrigan.getInstance();
		System.out.println(instance);
	}
} 