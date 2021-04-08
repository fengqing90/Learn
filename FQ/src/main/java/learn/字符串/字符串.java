package learn.字符串;

/**
 * https://mp.weixin.qq.com/s?__biz=MzUxMDk2OTAzMQ==&mid=2247483998&idx=1&sn=646731ccdfb58986893544e8a5609336&chksm=f97b97f6ce0c1ee071ab4212680072ea9f1d4e5f1669073f513829de98394e96958a54ec365a&token=756465261&lang=zh_CN#rd
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
        String s000 = "kv" + "ill";
        System.out.println(s0 == s00);              // true
        System.out.println(s0 == ("kv" + "ill"));   // true
        System.out.println(s0 == s000);             // true

        String s1 = new String("kvill");
        System.out.println(s0 == s1); // false

        String s2 = new String("kvill");
        System.out.println(s1 == s2); // false

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
