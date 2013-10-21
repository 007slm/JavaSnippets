class ThreadA {
	/**
	 * wait和notify的测试
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		ThreadB b = new ThreadB();
		b.start();
		System.out.println("b is start....");
		synchronized (b)
		/**
		 * 对b对象加锁。所有以后准备先锁b对象（同步b）的逻辑都等待我释放对b的锁之后在执行，先排队去把
		 * 
		 */
		{
				System.out.println("Waiting for b to complete...");
				/**
				 * 可能逻辑比较大说着锁太长事件不合适,先释放对b的锁，
				 * 但是我会极其等待或者b锁的然发通知notify或者notifyAll后我继续下面的逻辑
				 */
				b.wait();
				
				System.out.println("Completed.Now back to main thread");
		}
		System.out.println("Total is :" + b.total);
	}
}

class ThreadB extends Thread {
	int total;

	public void run() {
		try {
			System.out.println("虽然我先启动，但是我要先做别的逻辑1000ms,然后在同步this做第二步分逻辑");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (this) {
			System.out.println("ThreadB is running..");
			
			for (int i = 0; i < 100; i++) {
				total += i;
				System.out.println("total is " + total);
			}
			notify();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("我发了notify的通知，期望一个wait的线程结果对b的锁定，现在我退出这个对b的锁定块");
		}
	}
}