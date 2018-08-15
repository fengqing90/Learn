package learn. listener_mode.one;

/*public class Tools {
	public static void main(String[] args) {
		third td = new third();
		td.setCallbackListener(new Icallback() {
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("detected third function is called.");
			}
		});
		td.helloThird();
	}
}*/
public class Tools implements Icallback {
	public static void main(String[] args) {
		Tools tools = new Tools();
		third td = new third();
		td.setCallbackListener(tools);
		td.helloThird();
	}

	@Override
	public void run() {
		System.out.println("detected third function is called.");
	}
}