package 其它;

public class Test1 implements Runnable {

    private int x;
    private int y;

    @Override
    public synchronized void run() {
        for (;;) {
            this.x++;
            this.y++;
            System.out.println(Thread.currentThread().getName() + " @ x = "
                + this.x + ",y = " + this.y);
        }
    }

    public static void main(String[] args) {
//        new Test1();
//        new Thread(that).start();
//        new Thread(that).start();
//        new Thread(that).run();
//        new Thread(that).run();
        new Thread(() -> {
            for (;;) {
                System.out.println("1" + Thread.currentThread().getName());
            }
        }).run();
        new Thread(() -> {
            for (;;) {
                System.out.println("2" + Thread.currentThread().getName());
            }
        }).run();
    }
}
