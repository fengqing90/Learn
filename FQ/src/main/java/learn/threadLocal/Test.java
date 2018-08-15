package learn. threadLocal;

public class Test {
    public static void main(String[] args) {
        Thread[] t = new Thread[3];
        for (int i = 0; i < 3; i++) {
            t[i] = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 3; i++) {
                        System.out.printf("%s, id=%d, times=%s\n",
                            this.getName(), SampleThreadLocal.getId(), i);
                    }
                }
            };
            t[i].start();
        }
    }
}

class SampleThreadLocal {
    private static int count = 0;
    private static final ThreadLocal<Integer> id = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return SampleThreadLocal.count++;
        }
    };

    public static int getId() {
        return SampleThreadLocal.id.get();
    }
}