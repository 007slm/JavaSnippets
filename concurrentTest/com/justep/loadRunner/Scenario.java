package com.justep.loadRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author 007slm
 * 模拟loadRunnalbe的场景类
 * 
 */
public class Scenario {
	
	
	private static ScheduledThreadPoolExecutor ste = null;
	
	/**
	 * 
	 * @param iteratorNum 动作要执行的次数
	 * @param action 要并发测试的动作
	 * @param groupSize 每测试组的大小
	 * @param period 每过多长时间加压下一组测试
	 * @param unit 每组之间加压时间的单位
	 */
	public Scenario(int iteratorNum,final Runnable action,final int groupSize,long period,TimeUnit unit) {
		int poolSize =iteratorNum*groupSize;
		ste =new ScheduledThreadPoolExecutor(poolSize);
		ste.scheduleAtFixedRate(new Runnable(){
				public void run() {
					ExecutorService exec = Executors.newCachedThreadPool();      
					Semaphore semp = new Semaphore(groupSize);
					for (int i = 0; i < groupSize; i++) {
						try {
							semp.acquire();
							exec.execute(action);
							semp.release();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					exec.shutdown();
				}
			}, 0, period, unit);
		boolean flag =true;
		while(flag){
			if(iteratorNum-1 < ste.getCompletedTaskCount()){
				flag =false;
				ste.shutdown();
			}
					
		}
	}
	
	public static void main(String[] args) {
		new Scenario(2, new Runnable(){
			public void run() {
				try {
					Thread.sleep(1000);
					System.out.println("哈哈"+Thread.currentThread().getName());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 10, 10, TimeUnit.MILLISECONDS);
	}
}
