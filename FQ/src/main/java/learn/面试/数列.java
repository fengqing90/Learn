package learn.面试;

public class 数列 {

    public static void shulie1(int a, int b, int max_b) {

        int result = a + b;

        if (result > max_b) {
            return;
        }
        //
//        System.out.print(a);
//        System.out.print("+");
//        System.out.print(b);
//        System.out.print(" ");
//        System.out.print(result);
//        System.out.println();
        //
        System.out.print(result);
        System.out.print(" ");
        数列.shulie1(b, result, max_b);
    }

    public static void shulie2(int count) {
        int a = 1, b = 1, c = 0;
        System.out.println("斐波那契数列前" + count + "项为：");
        System.out.print(a + "\t" + b + "\t");
        for (int i = 0; i <= count; i++) {
            c = a + b;
            a = b;
            b = c;
            System.out.print(c + "\t");
            if (i % 5 == 0) {
                System.out.println();
            }
        }
    }

    public static void shulie3(int count) {
        int arr[] = new int[count];
        arr[0] = arr[1] = 1;
        for (int i = 2; i < arr.length; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        System.out.println("斐波那契数列的前" + count + "项如下所示：");
        for (int i = 0; i < arr.length; i++) {
            if (i % 5 == 0) {
                System.out.println();
            }
            System.out.print(arr[i] + "\t");
        }
    }

    public static void main(String[] args) {
        //方式1
        数列.shulie1(0, 1, 4000);
        System.out.println();

        //方式2
        数列.shulie2(10);
        System.out.println();

        //方式3
        数列.shulie3(10);
        System.out.println();

    }
}
