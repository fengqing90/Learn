package learn.排序;

import org.apache.commons.lang3.ArrayUtils;

public class 冒泡排序 {

    public static void main(String[] args) {
        int[] arr = new int[] { 7, 5, 3, 1, 4, 2, 6 };
        冒泡排序.bubbleSort(arr);
        System.out.println(ArrayUtils.toString(arr));
    }

    public static void bubbleSort(int[] a) {
        int c = 0;
        int len = a.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - i - 1; j++) {//注意第二重循环的条件
                if (a[j] > a[j + 1]) {
                    int temp = a[j];

                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
                c++;
            }
        }
        System.out.println(c);
    }
}
