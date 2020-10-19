package com.learnleetcode.数组;

/**
 * 数组
 *
 * @author fengqing
 * @date 2020/10/19 10:02
 */
public class 数组 {

    public static void main(String[] args) {

        System.out.println(升序数组查找数组中绝对值最小的值
            .min(new int[] { -5, -3, -1, 2, 3, 4, 6, 7, 8, 9 }));
        System.out.println(升序数组查找数组中绝对值最小的值
            .min(new int[] { -5, -4, -3, 2, 3, 4, 6, 7, 8, 9 }));
        System.out.println(升序数组查找数组中绝对值最小的值
            .min(new int[] { -7, -6, -5, -4, -3, -2, -1, 1, 2, 3 }));
        System.out.println(升序数组查找数组中绝对值最小的值
            .min(new int[] { -7, -6, -5, -4, -3, -2, 1, 2, 3 }));
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
