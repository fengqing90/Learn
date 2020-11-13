package learn;

public class Test {

    class A {
        int a = 1;
        {
            System.out.println("a");
            // printB();
        }

        void printB() {
            // System.out.println("b");
            {
                System.out.println("c");
            }
        }
    }

    class B {
        void print() {
            打法:
            {
                System.out.println("1");
            }
        }

        void BBB() {
        }
    }

    public static void main(String[] args) {
        //		new Test().new A();
        ///////////////////////////
        int b = 0;
        A:
        {
            int a = 1;
            System.out.println(50 << 3);
            System.out.println(b);
        }

        B:
        {
            //			System.out.println(a);
            System.out.println(b);
        }

        System.out.println(10 & 15);
        System.out.println(11 & 15);
        System.out.println(9 & 15);
        System.out.println(String.valueOf(10).hashCode() & 15);
    }
}
