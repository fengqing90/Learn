package com.learnleetcode.数组;

import java.util.Arrays;

/**
 * 数组
 *
 * @author fengqing
 * @date 2020/10/19 10:02
 */
public class 数组 {

    public static void main(String[] args) {

        // System.out.println(升序数组查找数组中绝对值最小的值
        //     .min(new int[] { -5, -3, -1, 2, 3, 4, 6, 7, 8, 9 }));
        // System.out.println(升序数组查找数组中绝对值最小的值
        //     .min(new int[] { -5, -4, -3, 2, 3, 4, 6, 7, 8, 9 }));
        // System.out.println(升序数组查找数组中绝对值最小的值
        //     .min(new int[] { -7, -6, -5, -4, -3, -2, -1, 1, 2, 3 }));
        // System.out.println(升序数组查找数组中绝对值最小的值
        //     .min(new int[] { -7, -6, -5, -4, -3, -2, 1, 2, 3 }));

        System.out.println(Arrays.toString(
            数组的相对排序.relativeSortArray(数组的相对排序.arr1(), 数组的相对排序.arr2())));

    }

    /**
     * 1122. 数组的相对排序
     *
     * <pre>
     * 给你两个数组，arr1 和 arr2，
     *
     * arr2 中的元素各不相同
     * arr2 中的每个元素都出现在 arr1 中
     * 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
     *
     *
     * 示例：
     * 输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
     * 输出：[2,2,2,1,4,3,3,9,6,7,19]
     * </pre>
     **/
    static class 数组的相对排序 {
        static int[] arr1() {
            return new int[] { 2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19 };
        }

        static int[] arr2() {
            return new int[] { 2, 1, 4, 3, 9, 6 };
        }

        static int[] relativeSortArray(int[] arr1, int[] arr2) {

            int[] array = new int[arr1.length];
            int index = 0;

            for (int i = 0; i < arr2.length; i++) {
                int tmp = arr2[i];
                for (int j = 0; j < arr1.length; j++) {
                    if (arr1[j] == tmp) {
                        array[index] = arr1[j];
                        index++;
                    }
                }
            }
            int[] tmp = new int[arr1.length - index];
            int b = 0;

            for (int i = 0; i < arr1.length; i++) {
                boolean in = false;
                for (int j = 0; j < array.length; j++) {
                    if (arr1[i] == array[j]) {
                        in = true;
                    }
                }
                if (!in) {
                    tmp[b] = arr1[i];
                    b++;
                }
            }
            Arrays.sort(tmp);

            for (int i = 1; i <= tmp.length; i++) {
                array[array.length - i] = tmp[tmp.length - i];
            }

            return array;
        }
    }

    static class 升序数组查找数组中绝对值最小的值 {
        public static int min(int[] arr) {
            int left = 0, mid = 0, right = arr.length;
            while (left < right) {

                System.out.println("left:" + left);
                System.out.println("right:" + right);
                mid = left + (right - left) / 2;
                System.out.println("mid:" + mid);
                System.out.println("******************");

                if (arr[mid + 1] < 0) {
                    left = mid + 1;
                } else if (arr[mid] > 0) {
                    right = mid - 1;
                } else if (arr[mid] + arr[mid + 1] >= 0) {
                    return arr[mid];
                } else {
                    return arr[mid + 1];
                }
            }
            return -1;
        }

        public static int min2(int[] arr) {
            int left = 0, mid = 0, right = arr.length;
            while (true) {

                mid = (left + right) / 2;

                if (arr[mid + 1] < 0) {
                    left = mid + 1;
                } else if (arr[mid] > 0) {
                    right = mid - 1;
                } else if (arr[mid] + arr[mid + 1] >= 0) {
                    return arr[mid];
                } else {
                    return arr[mid + 1];
                }
            }
        }
    }
}
