package learn.文件读取;

import java.util.Scanner;

public class FileReadTest1 {

//    public static void main(String[] args) {
//        File f = new File("C:\\Users\\fengqing.xxx\\Desktop\\CA");
//        List<String> tmp = new ArrayList<>();
//
////        Stream.of(f.listFiles()).map(i -> CopyOfFileReadTest.readPath(i))
////            .collect(toCollection(() -> new LinkedList<Double>()));
//
////            .collect(Collectors.reducing(identity, op));
////            .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
//        IntStream.range(1, 4).forEach(System.out::println);
//    }
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        double sum = 0;
        int m = 0;

        while (scan.hasNextDouble()) {
            double x = scan.nextDouble();
            m = m + 1;
            sum = sum + x;
            if (x == 999.00) {
                break;
            }
        }

        System.out.println(m + "个数的和为" + sum);
        System.out.println(m + "个数的平均值是" + sum / m);

    }
}
