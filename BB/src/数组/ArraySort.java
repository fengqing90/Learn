package 数组;

import java.util.Random;

public class ArraySort {
    public static void main(String[] args) {
        int[] in = new int[10];
        System.out.println("排序前的随机数组如下：");
        Random rd = new Random();
        for (int i = 0; i < in.length; i++) {
            in[i] = rd.nextInt(50);
            System.out.print(in[i] + " ");
        }
        System.out.println();
        System.out.println("排序后的随机数组如下：");
//	Arrays.sort(in);;//使用Arrays类的sort方法排序，也可使用下述循环排序
        int index;
        for (int i = 1; i < in.length; i++) {
            index = 0;
            for (int j = 1; j < in.length - i; j++) {
                if (in[j] > in[index]) {
                    index = j;  //in[index]是最大值
                }
//			else{
//				index=0;
//			}
            }
            int temp = in[in.length - 1]; //将数组最后一个数赋值给temp
            in[in.length - 1] = in[index];//将最大值放在数组最后
            in[index] = temp;//将原数组最后一个数放到index处
            for (int a : in) {
                System.out.print(a + " ");
            }
            System.out.println();
        }

    }
}