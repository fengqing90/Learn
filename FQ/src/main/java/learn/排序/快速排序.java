package learn.排序;

import org.apache.commons.lang3.ArrayUtils;

public class 快速排序 {
    public static void main(String[] args) {
        int[] arr = new int[] { 4, 5, 3, 1, 7, 2, 6 };
        System.out.println(ArrayUtils.toString(arr));
        快速排序.quickSort(arr, 0, 6);
        System.out.println(ArrayUtils.toString(arr));
        /////////////////////////////////////
        System.out.println("**********************************");
        arr = new int[] { 4, 5, 3, 1, 7, 2, 6 };
        System.out.println(ArrayUtils.toString(arr));
        快速排序.quickSort2(arr, 0, arr.length - 1);
        System.out.println(ArrayUtils.toString(arr));

    }

    public static void quickSort(int[] arry, int start, int end) {
        if (start > end) {
            return;
        }

        int baseNum = arry[start];//选基准值
        int midNum;//记录中间值
        int s = start;
        int e = end;
        do {
            while (arry[s] < baseNum && s < end) {
                s++;
            }
            while (arry[e] > baseNum && e > start) {
                e--;
            }
            if (s <= e) {
                midNum = arry[s];
                arry[s] = arry[e];
                arry[e] = midNum;
                s++;
                e--;
                // System.out.println();
                // System.out.println(ArrayUtils.toString(arry));
            }
        } while (s <= e);

        if (start < e) {
            快速排序.quickSort(arry, start, e);
        }
        if (end > s) {
            快速排序.quickSort(arry, s, end);
        }
    }

    public static void quickSort2(int R[], int lo, int hi) {
        int ll = lo, hh = hi;
        int temp;

        if (ll > hh) {  //左指针大于右 结束
            return;
        }

        temp = R[ll];  // 获取基准

        while (ll != hh) {
            // 高标值大于基准,高标值无需移动,下标前移
            while (hh > ll && R[hh] > temp) {
                --hh;
            }

            // 高标值低于基准时,高低交换
            R[ll] = R[hh];

            // 低标值小于基准,低标值无需移动,下标后移
            while (ll < hh && R[ll] < temp) {
                ++ll;
            }

            // 低标值大于基准时,高低交换
            R[hh] = R[ll];
        }

        R[ll] = temp;
        quickSort2(R, lo, ll - 1);
        quickSort2(R, ll + 1, hi);
    }

}
