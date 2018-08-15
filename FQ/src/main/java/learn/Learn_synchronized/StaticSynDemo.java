package learn. Learn_synchronized;

public class StaticSynDemo {

	private static String a = "test";

	// 等同于方法print2
	public synchronized void print1(String b) { // 调用前要取得StaticSynDemo实例化后对象的锁
		System.out.println(b + a);
	}

	public void print2(String b) {
		synchronized (this) {// 取得StaticSynDemo实例化后对象的锁
			System.out.println(b + a);
		}
	}

	// 等同于方法print4
	public synchronized static void print3(String b) {// 调用前要取得StaticSynDemo.class类的锁
		System.out.println(b + a);
	}

	public static void print4(String b) {
		synchronized (StaticSynDemo.class) { // 取得StaticSynDemo.class类的锁
			System.out.println(b + a);
		}
	}
}