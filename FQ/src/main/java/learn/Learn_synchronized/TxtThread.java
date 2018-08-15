package learn.Learn_synchronized;

class TxtThread implements Runnable {
    int num = 100;
    String str = new String();

    @Override
    public void run() {
        synchronized (this.str) {
            while (this.num > 0) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.getMessage();
                }
                System.out.println(Thread.currentThread().getName()
                    + " this is " + this.num--);
            }
        }
    }

    public static void main(String[] args) {
        TxtThread tt = new TxtThread();
        new Thread(tt).start();
        new Thread(tt).start();
        new Thread(tt).start();
        new Thread(tt).start();
    }

}