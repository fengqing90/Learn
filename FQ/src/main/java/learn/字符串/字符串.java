package learn.字符串;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/8 14:14
 */
public class 字符串 {

    public static void main(String[] args) {
        test();
        test1();
        test2();
        test3();
    }

    private static void test() {
        String s0 = "kvill";
        String s00 = "kvill";
        System.out.println(s0 == s00);

        String s1 = new String("kvill");
        String s2 = new String("kvill");
        System.out.println(s0 == s1); // false

        s2 = s2.intern();
        System.out.println(s0 == s1); // false

        System.out.println(s0 == s1.intern());  // true
        System.out.println(s0 == s2);           // true

        System.out.println("********************************");
    }

    private static void test1() {
        String a = "ab";
        String bb = "b";

        String b = "a" + bb;
        System.out.println(a == b);
    }

    private static void test2() {
        String a = "ab";
        final String bb = "b";

        String b = "a" + bb;
        System.out.println(a == b);
    }

    private static void test3() {
        String a = "ab";
        final String bb = getBB();

        String b = "a" + bb;
        System.out.println(a == b);
    }

    private static String getBB() {
        return "b";
    }
}
