package learn. listener_mode.one;

public class third {
	private Icallback listener = null;// 2.1、在类中定义接口对象

	public void setCallbackListener(Icallback cbl) {// 2.2
		this.listener = cbl;
	}

	public void helloThird() {
		System.out.println("helloThird() is invoked.");
		if (listener != null)// 2.3、在关心的方法中，调用接口对象中定义的方法
			listener.run();
	}
} 