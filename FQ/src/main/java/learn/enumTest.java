package learn;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/10 11:29
 */
public class enumTest {
    enum MyEnum{
        D,A,B,C
    }

    public static void main(String[] args) {
        // System.out.println(MyEnum.A.ordinal());
        // System.out.println(MyEnum.B.ordinal());
        // System.out.println(MyEnum.C.ordinal());
        System.out.println(MyEnum.D.ordinal());

        // 0
        // 1
        // 2
        // 3

        // 1
        // 2
        // 3
        // 0
    }
}
