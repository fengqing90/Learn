package learn.排序;

import org.apache.commons.lang3.ArrayUtils;

public class 简单选择排序 {

    public static void selectSort(int[] a) {
        System.out.println(ArrayUtils.toString(a));
        int len = a.length;
        for (int i = 0; i < len; i++) {//循环次数
            int value = a[i];
            int position = i;
            for (int j = i + 1; j < len; j++) {//找到最小的值和位置
                if (a[j] < value) {
                    value = a[j];
                    position = j;
                }
            }
            a[position] = a[i];//进行交换
            a[i] = value;
            System.out.println();
            System.out.println(ArrayUtils.toString(a) + "@" + i);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 7, 5, 3, 1, 4, 2, 6 };
        简单选择排序.selectSort(arr);
        System.out.println(ArrayUtils.toString(arr));
    }
}
