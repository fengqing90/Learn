package learn. Learn_synchronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yumin
 * 
 */
public class RunnableMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExecutorService exec = Executors.newFixedThreadPool(2);
		exec.execute(new RunnableTest());
		exec.execute(new RunnableTest());
		exec.shutdown();
	}
}
