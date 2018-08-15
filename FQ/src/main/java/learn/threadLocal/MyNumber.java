package learn. threadLocal;

public class MyNumber {
    // 使用ThreadLocal 维护唯一的变量
    private static ThreadLocal<Integer> number = new ThreadLocal<Integer>() {
        // 去看ThreadLcoal的源代码,重写initialVal()方法，否则返回null
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    // 拿到下一个号码
    public Integer getNext() {
        // ThreadLocal变量在调用set或者是get方法的时候，才回去调用initialValue() 且调用一次
        MyNumber.number.set(MyNumber.number.get() + 1);
        return MyNumber.number.get();
    }

    public static void main(String[] args) {
        MyNumber mynumber = new MyNumber();
        // 这里 传入的 mynumber 是同一个对象 但是打印的结果却是相互不影响
        // 因为ThreadLocal保证了每个访问该变量的线程都有一个独立的副本
        MyThread mt1 = new MyNumber().new MyThread(mynumber);
        new Thread(mt1, "one").start();

        MyThread mt2 = new MyNumber().new MyThread(mynumber);
        new Thread(mt2, "two").start();
    }

    class MyThread implements Runnable {
        private MyNumber my_number;

        public MyThread(MyNumber my_number) {
            this.my_number = my_number;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()
                    + "-number:" + this.my_number.getNext());
            }
        }

        public void invokeGetNext() {
            this.my_number.getNext();
        }
    }
}