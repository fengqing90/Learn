package 数组;

import java.util.Random;

public class ArraySortTwo {
    public static void main(String[] args) {
        Random rd = new Random();
        int[] arr = new int[10];
        System.out.println("排序前的数组为");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextInt(50);
            System.out.print(arr[i] + " ");
        }
        System.out.println("排序后的数组为");
//        for (int i = 1; i < arr.length; i++) {
//            for (int j = 0; j < arr.length - i; j++) {
//                int temp = 0;
//                if (arr[i] > arr[i + 1]) {
//                    temp = arr[i + 1];
//                    arr[i + 1] = arr[i];
//                    arr[i] = temp;
//                }
//            }
//            System.out.print(arr[i] + " ");
//        }
        
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                int temp = 0;
                if (arr[i] > arr[i + 1]) {
                    temp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = temp;
                }
            }
            System.out.print(arr[i] + " ");
        }
    }
}
