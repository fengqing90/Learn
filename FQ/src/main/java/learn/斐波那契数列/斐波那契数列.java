package learn.斐波那契数列;

import java.util.stream.Stream;

public class 斐波那契数列 {

    public static void main(String[] args) {
        System.out.println(斐波那契数列.fibonacci(5));
        System.out.println(斐波那契数列.fibonacci2(5));

        Stream.iterate(new int[] { 0, 1 }, t -> {
            int temp = t[1];
            t[1] = t[0] + t[1];
            t[0] = temp;
            return t;
        }).limit(10).map(t -> t[0]).forEach(System.out::println);
    }

    public static int fibonacci(int n) {
        switch (n) {
            case 1:
                return 1;
            case 2:
                return 1;
            default:
                int i = 斐波那契数列.fibonacci(n - 1) + 斐波那契数列.fibonacci(n - 2);
                // System.out.println(i);
                return i;
        }
    }

    public static int fibonacci2(int n) {
        int c = 1;
        // 从2开始。2前面都直接返回1
        for (int a = 1, b = 1, i = 2; i < n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }
}
