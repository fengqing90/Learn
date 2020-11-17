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

        // System.out.println(Arrays.toString(
        //     数组的相对排序.relativeSortArray(数组的相对排序.arr1(), 数组的相对排序.arr2())));

        System.out.println(寻找两个正序数组的中位数
            .findMedianSortedArrays(new int[] { 1, 2 }, new int[] { 3, 4 }));

    }

    /**
     * 4. 寻找两个正序数组的中位数
     *
     * <pre>
     * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
     * 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
     *
     * 示例 1：
     * 输入：nums1 = [1,3], nums2 = [2]
     * 输出：2.00000
     * 解释：合并数组 = [1,2,3] ，中位数 2
     *
     * 示例 2：
     * 输入：nums1 = [1,2], nums2 = [3,4]
     * 输出：2.50000
     * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
     *
     * 示例 3：
     * 输入：nums1 = [0,0], nums2 = [0,0]
     * 输出：0.00000
     *
     * 示例 4：
     * 输入：nums1 = [], nums2 = [1]
     * 输出：1.00000
     *
     *  示例 5：
     * 输入：nums1 = [2], nums2 = []
     * 输出：2.00000
     * </pre>
     **/
    static class 寻找两个正序数组的中位数 {
        public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int m = nums1.length;
            int n = nums2.length;
            int[] nums = new int[m + n];

            // 总长度
            int count = 0;
            // i = 数据1下标， 就= 数据2下标
            int i = 0, j = 0;
            // count 不等于总长度
            while (count != (m + n)) {
                // i 等于 数组1长度，说明数据1 已经排完,后续把数组2 追加后面
                if (i == m) {
                    while (j != n) {
                        nums[count++] = nums2[j++];
                    }
                    break;
                }

                // 类似 上面
                if (j == n) {
                    while (i != m) {
                        nums[count++] = nums1[i++];
                    }
                    break;
                }

                // 排序
                if (nums1[i] < nums2[j]) {
                    nums[count++] = nums1[i++];
                } else {
                    nums[count++] = nums2[j++];
                }
            }

            System.out.println(Arrays.toString(nums));

            // 偶数 = 2中数相加 /2.0
            if (count % 2 == 0) {
                return (nums[count / 2 - 1] + nums[count / 2]) / 2.0;
            }
            // 奇数= 中数
            else {
                return nums[count / 2];
            }

        }
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
