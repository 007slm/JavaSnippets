import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;


public class TestStaticMethod  implements Runnable{
	
	public void run() {
		synchronized (lhm) {
			System.out.println("lhm.size()="+lhm.size());
			System.out.println("=============="+Thread.currentThread().getId()+"==============");
			for(Iterator<String> it =lhm.keySet().iterator();it.hasNext();){
				String threadId =it.next();
				System.out.println("*****"+threadId+"****"+lhm.get(threadId));
			}
			/*for(String threadId:lhm.keySet()){
				System.out.println("*****"+threadId+"****"+lhm.get(threadId));
			}*/
			addValue();	
		}
		int m =i;
		try {
			Thread.sleep(200L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m++;
		i=m;
		System.out.println(i);
	}
	public static int i=0;
	public static ConcurrentHashMap<String,String> lhm=new ConcurrentHashMap<String,String>(); 
	public static void addValue(){	
		lhm.put(String.valueOf(Thread.currentThread().getId()),Thread.currentThread().getName());
	}
}
