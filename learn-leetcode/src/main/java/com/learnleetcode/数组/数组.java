package com.learnleetcode.数组;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

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

        // System.out.println(寻找两个正序数组的中位数
        //     .findMedianSortedArrays(new int[] { 1, 2 }, new int[] { 3, 4 }));

        System.out.println(删除排序数组中的重复项
            .removeDuplicates(new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 }));
    }

    /**
     * [66]加一
     **/
    static class 加一 {
        public static int[] plusOne(int[] digits) {
            int len = digits.length;
            // 从末尾开始加1
            for (int i = len - 1; i >= 0; i--) {

                digits[i]++;

                // 是否进位
                digits[i] %= 10;

                // 判断是否进位,未进位直接返回
                if (digits[i] != 0) {
                    return digits;
                }
            }

            // 执行到此处说明进位
            digits = new int[len + 1];

            digits[0] = 1;
            return digits;
        }
    }

    /**
     * [53]最大子序和
     **/
    static class 最大子序和 {
        /**
         * 方案:分治
         */
        public static class Status {
            public int lSum, rSum, mSum, iSum;

            /**
             * @param lSum
             *        表示 [l, r] 内以 l 为左端点的最大子段和
             * @param rSum
             *        表示 [l, r] 内以 r 为右端点的最大子段和
             * @param mSum
             *        表示 [l, r] 内的最大子段和
             * @param iSum
             *        表示 [l, r] 的区间和
             */
            public Status(int lSum, int rSum, int mSum, int iSum) {
                this.lSum = lSum;
                this.rSum = rSum;
                this.mSum = mSum;
                this.iSum = iSum;
            }
        }

        /**
         * 方案:分治
         */
        public static int maxSubArray(int[] nums) {
            return getInfo(nums, 0, nums.length - 1).mSum;
        }

        /**
         * 方案:分治
         */
        public static Status getInfo(int[] a, int l, int r) {
            if (l == r) {
                return new Status(a[l], a[l], a[l], a[l]);
            }
            int m = (l + r) >> 1;
            Status lSub = getInfo(a, l, m);
            Status rSub = getInfo(a, m + 1, r);
            return pushUp(lSub, rSub);
        }

        /**
         * 方案:分治
         */
        public static Status pushUp(Status l, Status r) {
            int iSum = l.iSum + r.iSum;
            int lSum = Math.max(l.lSum, l.iSum + r.lSum);
            int rSum = Math.max(r.rSum, r.iSum + l.rSum);
            int mSum = Math.max(Math.max(l.mSum, r.mSum), l.rSum + r.lSum);
            return new Status(lSum, rSum, mSum, iSum);
        }

        /**
         * 方案：动态规划，
         **/
        public static int maxSubArray3(int[] nums) {
            int max = nums[0];
            int sum = 0;
            for (int num : nums) {
                if (sum > 0) {
                    sum += num;
                } else {
                    sum = num;
                }
                max = Math.max(max, sum);
            }
            return max;
        }

        /**
         * 方案：滚动变量优化
         **/
        public static int maxSubArray2(int[] nums) {
            // 初始化第一次结果值 和 最大值
            int sum = nums[0], max = nums[0];
            for (int i = 1; i < nums.length; i++) {
                // 第i个 和 第i+(i-1) 取大
                sum = Math.max(nums[i], sum + nums[i]);
                // 取大后与现在最大比较
                max = Math.max(max, sum);
            }
            return max;
        }
    }

    /**
     * [35]搜索插入位置
     **/
    static class 搜索插入位置 {

        /**
         * 方案一：二分查找，动态变动 s、e 下标，缩小范围 O(logN)
         **/
        public static int searchInsert(int[] nums, int target) {
            int left = 0, right = nums.length;  // 注意
            while (left < right) {  // 注意
                int mid = left + (right - left) / 2; // 注意

                if (nums[mid] < target) {
                    left = mid + 1; // 注意
                } else if (nums[mid] > target) {
                    right = mid; // 注意
                } else {
                    return mid; // 注意
                }
            }
            return left;
        }

        /**
         * 方案二：O(m) 效率低
         **/
        public static int searchInsert数组(int[] nums, int target) {
            int length = nums.length;
            int[] array = new int[length + 1];

            for (int i = 0; i < length; i++) {
                array[i] = nums[i];
                if (array[i] == target) {
                    return i;
                } else if (array[i] > target) {
                    return i;
                }
                if (target > nums[length - 1]) {
                    return length;
                }
            }
            System.out.println(ArrayUtils.toString(array));
            return 0;
        }

    }

    /**
     * [27]移除元素
     **/
    static class 移除元素 {

        /**
         * 方案一：拷贝覆盖，不相等就覆盖旧数组值，index++
         **/
        public int removeElement1(int[] nums, int val) {
            int ans = 0;
            for (int num : nums) {
                // 不相等就覆盖,覆盖到最后那个元素就没了
                if (num != val) {
                    nums[ans] = num;
                    ans++;
                }
            }
            return ans;
        }

        /**
         * 方案二：交换移除，相等当前数值与最后的值替换，不相等 i++
         **/
        public int removeElement2(int[] nums, int val) {
            int ans = nums.length;
            for (int i = 0; i < ans;) {
                if (nums[i] == val) {
                    nums[i] = nums[ans - 1];
                    ans--;
                } else {
                    i++;
                }
            }
            return ans;
        }
    }

    /**
     * [26]删除排序数组中的重复项
     **/
    static class 删除排序数组中的重复项 {
        public static int removeDuplicates(int[] nums) {
            int i = 1;
            for (int j = 1; j < nums.length; j++) {
                if (nums[j] != nums[i - 1]) {
                    nums[i++] = nums[j];
                }
            }
            System.out.println(ArrayUtils.toString(nums));
            return i;
        }

        public static int removeDuplicates1(int[] nums) {
            int length = nums.length;
            int j = 1;
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i] == nums[i + 1]) // 如果两个相邻的元素相同,那么进行压缩，长度减少
                {
                    length--;
                } else {
                    nums[j++] = nums[i + 1]; //若两个相邻元素不同，那么对另外一个元素进行存储
                }

            }
            System.out.println(ArrayUtils.toString(nums));
            return length;
        }

        // 方式2 ： 双指针
        public static int removeDuplicates2(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            int p = 0;
            int q = 1;
            while (q < nums.length) {
                if (nums[p] != nums[q]) {
                    if (q - p > 1) {
                        nums[p + 1] = nums[q];
                    }
                    p++;
                }
                q++;
            }
            return p + 1;
        }

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
