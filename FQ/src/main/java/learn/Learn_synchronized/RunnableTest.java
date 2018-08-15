package learn. Learn_synchronized;

/**
 * @author yumin
 * 
 */
public class RunnableTest implements Runnable {

	private static boolean flag = true;

	/**
	 * 使用synchronized方法
	 */
	private static synchronized void testSyncMethod() { // 注意static
		for (int i = 0; i < 100; i++) {
			System.out.println("testSyncMethod:" + i);
		}
	}

	/**
	 * 使用synchronized代码块
	 */
	private void testSyncBlock() {
		synchronized (this) { // 注意this
			for (int i = 0; i < 100; i++) {
				System.out.println("testSyncBlock:" + i);
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		// 每个线程执行不同的方法
		if (flag) {
			flag = false;
			testSyncMethod();
		} else {
			flag = true;
			testSyncBlock();
		}
	}
}
