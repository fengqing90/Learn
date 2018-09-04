package learn.排序;

import org.apache.commons.lang3.ArrayUtils;

public class 快速排序 {
    public static void main(String[] args) {
        int[] arr = new int[] { 4, 5, 3, 1, 7, 2, 6 };
        System.out.println(ArrayUtils.toString(arr));
        快速排序.quickSort(arr, 0, 6);
        System.out.println(ArrayUtils.toString(arr));
    }

    public static void quickSort(int[] a, int start, int end) {
        if (start < end) {
            int baseNum = a[start];//选基准值
            int midNum;//记录中间值
            int s = start;
            int e = end;
            do {
                while (a[s] < baseNum && s < end) {
                    s++;
                }
                while (a[e] > baseNum && e > start) {
                    e--;
                }
                if (s <= e) {
                    midNum = a[s];
                    a[s] = a[e];
                    a[e] = midNum;
                    s++;
                    e--;
                    System.out.println();
                    System.out.println(ArrayUtils.toString(a));
                }
            } while (s <= e);
            if (start < e) {
                快速排序.quickSort(a, start, e);
            }
            if (end > s) {
                快速排序.quickSort(a, s, end);
            }
        }
    }
}
