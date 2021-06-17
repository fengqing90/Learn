package com.learnleetcode.数组;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.learnleetcode.LeetCode;

/**
 * 数组
 *
 * @date 2020/10/19 10:02
 */
class 数组 extends LeetCode {

    public static void main(String[] args) {
        升序数组查找数组中绝对值最小的值.run();
        数组的相对排序.run();
        寻找两个正序数组的中位数.run();
        删除排序数组中的重复项.run();
        两数之和II_输入有序数组.run();
        多数元素.run();
        杨辉三角.run();
        杨辉三角II.run();
        旋转数组.run();
        数组形式的整数加法.run();
        子数组最大平均数I.run();

    }

    /**
     * 283. 移动零
     * https://leetcode-cn.com/problems/contains-duplicate/
     */
    final static class 移动零 extends 数组 {

        public static void main(String[] args) {
            System.out.println(Runtime.getRuntime().availableProcessors()//获取逻辑核心数，如6核心12线程，那么返回的是12
            );
            移动零 移动零 = new 移动零();

            int[] nums = { 1, 1, 1, 3, 3, 4, 3, 2, 4, 2 };
            移动零.moveZeroes(nums);
            System.out.println(ArrayUtils.toString(nums));

        }

        public void moveZeroes(int[] nums) {
            int index = 0;
            for (int num : nums) {
                if (num != 0) {
                    nums[index++] = num;
                }
            }
            for (int i = index; i < nums.length; i++) {
                nums[i] = 0;
            }
        }
    }

    /**
     * 217. 存在重复元素
     * https://leetcode-cn.com/problems/contains-duplicate/
     */
    final static class 存在重复元素 extends 数组 {

        public static void main(String[] args) {

            存在重复元素 存在重复元素 = new 存在重复元素();

            System.out.println(存在重复元素
                .containsDuplicate(new int[] { 1, 1, 1, 3, 3, 4, 3, 2, 4, 2 }));
        }

        public boolean containsDuplicate(int[] nums) {
            Arrays.sort(nums);
            int n = nums.length;
            for (int i = 0; i < n - 1; i++) {
                if (nums[i] == nums[i + 1]) {
                    return true;
                }
            }
            return false;
        }
    }

    /***
     * 1011. 在 D 天内送达包裹的能力<br>
     * https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days/
     */
    final static class 在D天内送达包裹的能力 extends 数组 {

        public static void main(String[] args) {
            在D天内送达包裹的能力 在D天内送达包裹的能力 = new 在D天内送达包裹的能力();
            System.out.println(在D天内送达包裹的能力.shipWithinDays(
                new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 5));
        }

        /** 从数组的最大元素开始遍历判断值i是否满足verification **/
        public int shipWithinDays(int[] weights, int D) {
            //二分查找 r = 数组的总和， l = 数组的最大值
            int r = Arrays.stream(weights).sum();
            int l = Arrays.stream(weights).max().getAsInt();
            //l < r
            while (l < r) {
                //取中间值
                int mid = (l + r) >> 1;
                //如果mid满足verification，则逼近右指针
                if (verification(weights, D, mid)) {
                    //包括mid
                    r = mid;
                } else {
                    //逼近左指针，mid + 1
                    l = mid + 1;
                }
            }
            //返回当前l就是最小的满足条件的值，即最低运载能力
            return l;
        }

        /** 判断最低运载能力为H的时候能否在D天内送达 **/
        public boolean verification(int[] weights, int D, int H) {
            //天数计数，初始化为1
            int count = 1;
            //每天的包裹总量
            int singleWeight = 0;
            for (int i = 0; i < weights.length; ++i) {
                //累计包裹总量
                singleWeight += weights[i];
                //如果累计包裹总量singleWeight > H，天数+1
                if (singleWeight > H) {
                    ++count;
                    singleWeight = weights[i];
                }
                //如果当前累计的天数count > D，说明当前H不满足条件，返回false
                if (count > D) {
                    return false;
                }
            }
            //说明当前H满足条件，返回true
            return true;
        }

    }

    /**
     * 643. 子数组最大平均数 I
     * <P>
     * 给定 n 个整数，找出平均数最大且长度为 k 的连续子数组，并输出该最大平均数。
     * 示例：
     * 输入：[1,12,-5,-6,50,3], k = 4
     * 输出：12.75
     * 解释：最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
     * </P>
     */
    final static class 子数组最大平均数I extends 数组 {

        static void run() {

            子数组最大平均数I 子数组最大平均数I = new 子数组最大平均数I();

            System.out.println(子数组最大平均数I
                .findMaxAverage(new int[] { 1, 12, -5, -6, 50, 3 }, 4));
            System.out.println(子数组最大平均数I.findMaxAverage(new int[] { 5 }, 1));
            System.out.println(
                子数组最大平均数I.findMaxAverage(new int[] { 0, 1, 1, 3, 3 }, 4));
            System.out.println(
                子数组最大平均数I.findMaxAverage(new int[] { 0, 4, 0, 3, 2 }, 1));
        }

        public double findMaxAverage(int[] nums, int k) {
            int sum = 0;
            for (int i = 0; i < k; i++) {
                sum += nums[i];
            }

            // int maxSum = sum;
            // for (int i = k; i < nums.length; i++) {
            //     sum = sum - nums[i - k] + nums[i];
            //     maxSum = Math.max(maxSum, sum);
            // }

            int maxSum = sum;
            for (int i = 0; i < nums.length - k; i++) {
                sum = maxSum - nums[i] + nums[k - i];
                // System.out.println(sum);

                maxSum = Math.max(maxSum, sum);
            }
            return maxSum * 1.0 / k;
        }
    }

    /***
     * 989. 数组形式的整数加法
     * 
     * <pre>
     *  对于非负整数 X 而言，X 的数组形式是每位数字按从左到右的顺序形成的数组。例如，如果 X = 1231，那么其数组形式为 [1,2,3,1]。
     *  给定非负整数 X 的数组形式 A，返回整数 X+K 的数组形式。
     * 示例 1：
     * 输入：A = [1,2,0,0], K = 34
     * 输出：[1,2,3,4]
     * 解释：1200 + 34 = 1234
     * 
     * 示例 2：
     * 输入：A = [2,7,4], K = 181
     * 输出：[4,5,5]
     * 解释：274 + 181 = 455
     * </pre>
     */
    final static class 数组形式的整数加法 extends 数组 {

        static void run() {
            int[] arry = new int[] { 1, 2, 3, 4, 5, 6, 7 };
            int k = 1;

            数组形式的整数加法 数组形式的整数加法 = new 数组形式的整数加法();

            System.out.println(数组形式的整数加法.addToArrayForm(arry, k));
        }

        // while ( A 没完 || B 没完))
        // A 的当前位
        // B 的当前位
        //
        // 和 = A 的当前位 + B 的当前位 + 进位carry
        //
        //         当前位 = 和 % 10;
        // 进位 = 和 / 10;
        //
        // 判断还有进位吗

        public List<Integer> addToArrayForm(int[] A, int K) {
            int n = A.length;
            List<Integer> res = new ArrayList<>();
            int i = n - 1, sum = 0, carry = 0;
            while (i >= 0 || K != 0) {  // 循环条件：两个数有一个没完
                int x = i >= 0 ? A[i] : 0;
                int y = K != 0 ? K % 10 : 0;

                sum = x + y + carry;
                carry = sum / 10;

                K = K / 10;
                i--;
                res.add(0, sum % 10);
            }
            if (carry != 0)
                res.add(0, carry);
            return res;

        }
    }

    /**
     * 189. 旋转数组
     * 
     * <pre>
     * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
     *
     * 示例 1:
     *
     * 输入: [1,2,3,4,5,6,7] 和 k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右旋转 1 步: [7,1,2,3,4,5,6]
     * 向右旋转 2 步: [6,7,1,2,3,4,5]
     * 向右旋转 3 步: [5,6,7,1,2,3,4]
     * </pre>
     */
    final static class 旋转数组 extends 数组 {
        static void run() {

            int[] arry = new int[] { 1, 2, 3, 4, 5, 6, 7 };
            int k = 1;

            旋转数组 旋转数组 = new 旋转数组();
            System.out.println(ArrayUtils.toString(arry));

            旋转数组.rotate(arry, k);
            System.out.println(k + ":" + ArrayUtils.toString(arry));
        }

        public void rotate(int nums[], int k) {
            int length = nums.length;
            int temp[] = new int[length];
            //把原数组值放到一个临时数组中，
            for (int i = 0; i < length; i++) {
                temp[i] = nums[i];
            }
            //然后在把临时数组的值重新放到原数组，并且往右移动k位
            for (int i = 0; i < length; i++) {
                nums[(i + k) % length] = temp[i];
            }
        }
    }

    final static class 买卖股票的最佳时机 extends 数组 {
        static void run() {

            买卖股票的最佳时机 买卖股票的最佳时机 = new 买卖股票的最佳时机();

            System.out.println(买卖股票的最佳时机.maxProfit(null));
        }

        int maxProfit(int[] prices) {

            return 0;
        }

    }

    /***
     * 119. 杨辉三角 II
     * 给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
     */
    final static class 杨辉三角II extends 数组 {

        static void run() {
            System.out.println(new 杨辉三角II().getRow(5));
        }

        /**
         * 同一个List 变化：
         * 
         * <pre>
         *  [1          ],
         *  [1,1        ],
         *  [1,2,1      ],
         *  [1,3,3,1    ],
         *  [1,4,6,4,1  ]
         * </pre>
         */
        List<Integer> getRow(int rowIndex) {

            List<Integer> res = new ArrayList<>(rowIndex + 1);

            for (int i = 0; i <= rowIndex; i++) {
                res.add(1);

                for (int j = i - 1; j > 0; j--) {
                    res.set(j, res.get(j) + res.get(j - 1));
                }
            }

            return res;
        }

        /**
         * 复用118
         */
        public List<Integer> getRow2(int rowIndex) {
            List<List<Integer>> result = new ArrayList<>();

            for (int i = 0; i < rowIndex + 1; i++) {
                List<Integer> sub = new ArrayList<>();
                for (int j = 0; j <= i; j++) {

                    // 如果是第一个或最后一个 
                    if (j == 0 || j == i) {
                        sub.add(1);
                    } else {
                        sub.add(result.get(i - 1).get(j - 1)
                            + result.get(i - 1).get(j));
                    }
                }
                result.add(sub);
            }
            return result.get(rowIndex);
        }
    }

    /**
     * 118. 杨辉三角
     * 
     * <pre>
     * 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
     * 在杨辉三角中，每个数是它左上方和右上方的数的和。
     *
     * 示例:
     * 输入: 5
     * 输出:
     * [
     *      [1],
     *     [1,1],
     *    [1,2,1],
     *   [1,3,3,1],
     *  [1,4,6,4,1]
     * ]
     * </pre>
     */
    final static class 杨辉三角 extends 数组 {

        static void run() {
            System.out.println(new 杨辉三角().generate(5));
        }

        public List<List<Integer>> generate(int numRows) {
            List<List<Integer>> result = new ArrayList<>();

            for (int i = 0; i < numRows; i++) {
                List<Integer> sub = new ArrayList<>();
                for (int j = 0; j <= i; j++) {

                    // 如果是第一个或最后一个 
                    if (j == 0 || j == i) {
                        sub.add(1);
                    } else {
                        sub.add(result.get(i - 1).get(j - 1)
                            + result.get(i - 1).get(j));
                    }
                }
                result.add(sub);
            }
            return result;
        }
    }

    /**
     * 169. 多数元素
     * 
     * <pre>
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     *
     * 示例 1:
     * 输入: [3,2,3]
     * 输出: 3
     * 
     * 示例 2:
     * 输入: [2,2,1,1,1,2,2]
     * 输出: 2
     * </pre>
     */
    final static class 多数元素 extends 数组 {
        static void run() {

            System.out.println(new 多数元素()
                .majorityElement(new int[] { 2, 2, 1, 1, 1, 2, 2, 1, 1 }));
        }

        /**
         * 题目要求超过一半,排序后那个数肯定超过一半,取中间即可
         */
        int majorityElement2(int[] nums) {
            Arrays.sort(nums);
            return nums[nums.length / 2];
        }

        /**
         * 投票
         */
        int majorityElement(int[] nums) {
            int count = 0;
            int candidate = 0;

            for (int num : nums) {

                if (count == 0) {
                    candidate = num;
                }
                count += (num == candidate) ? 1 : -1;
            }
            return candidate;
        }
    }

    /**
     * 167. 两数之和 II - 输入有序数组
     * 
     * <pre>
     * 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
     *
     * 函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。
     *
     * 说明:
     * 返回的下标值（index1 和 index2）不是从零开始的。
     * 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
     * 示例:
     *
     * 输入: numbers = [2, 7, 11, 15], target = 9
     * 输出: [1,2]
     * 解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
     * </pre>
     */
    final static class 两数之和II_输入有序数组 extends 数组 {
        static void run() {
            两数之和II_输入有序数组 两数之和II_输入有序数组 = new 两数之和II_输入有序数组();

            System.out.println(ArrayUtils
                .toString(两数之和II_输入有序数组.twoSum(new int[] { 2, 7, 11, 15 }, 9)));

            System.out.println(ArrayUtils.toString(
                两数之和II_输入有序数组.twoSum2(new int[] { 2, 7, 11, 15 }, 9)));
        }

        /**
         * 二分
         */
        int[] twoSum3(int[] numbers, int target) {
            return null;
        }

        /**
         * 双指针,有序数组
         */
        int[] twoSum2(int[] numbers, int target) {
            int low = 0, hight = numbers.length - 1;

            while (low < hight) {
                int sum = numbers[low] + numbers[hight];
                if (sum == target) {
                    return new int[] { low + 1, hight + 1 };
                } else if (sum > target) {
                    hight--;
                } else {
                    low++;
                }
            }
            return null;
        }

        /**
         * hash
         */
        int[] twoSum(int[] numbers, int target) {

            Map<Integer, Integer> map = new HashMap<>();

            for (int i = 1; i <= numbers.length; i++) {
                if (map.containsKey(target - numbers[i - 1])) {
                    return new int[] { map.get(target - numbers[i - 1]), i };
                } else {
                    map.put(numbers[i - 1], i);
                }
            }

            return new int[] {};
        }
    }

    /**
     * 88. 合并两个有序数组
     * 
     * <pre>
     * 给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
     *
     * 说明：
     * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
     * 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
     *  
     *
     * 示例：
     * 输入：
     * nums1 = [1,2,3,0,0,0], m = 3
     * nums2 = [2,5,6],       n = 3
     * 输出：[1,2,2,3,5,6]
     *  
     *
     * 提示：
     * -10^9 <= nums1[i], nums2[i] <= 10^9
     * nums1.length == m + n
     * nums2.length == n
     * </pre>
     **/
    final static class 合并两个有序数组 extends 数组 {
        // 前提：nums1 足够大，2个有序数组
        public static int[] merge(int[] nums1, int m, int[] nums2, int n) {
            // two get pointers for nums1 and nums2
            int p1 = m - 1;
            int p2 = n - 1;
            // set pointer for nums1
            // 结果数组总长度
            int p = m + n - 1;

            // while there are still elements to compare
            // 下表 递减
            while ((p1 >= 0) && (p2 >= 0))
            // compare two elements from nums1 and nums2
            // and add the largest one in nums1
            {
                // 倒序 比较值大小，给nums1赋值=  n1 < n2 : 用n2, 反之用n1
                nums1[p--] = (nums1[p1] < nums2[p2]) ? nums2[p2--]
                    : nums1[p1--];
            }

            // 将nums2中未加入到nums1中的数放到头部
            System.arraycopy(nums2, 0, nums1, 0, p2 + 1);

            return nums1;
        }

    }

    /**
     * [66]加一
     **/
    final static class 加一 {
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
    final static class 最大子序和 {
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
    final static class 搜索插入位置 {

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
    final static class 移除元素 {

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
    final static class 删除排序数组中的重复项 extends 数组 {
        static void run() {
            System.out.println(new 删除排序数组中的重复项()
                .removeDuplicates(new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 }));
        }

        int removeDuplicates(int[] nums) {
            int i = 1;
            for (int j = 1; j < nums.length; j++) {
                if (nums[j] != nums[i - 1]) {
                    nums[i++] = nums[j];
                }
            }
            return i;
        }

        int removeDuplicates1(int[] nums) {
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
        int removeDuplicates2(int[] nums) {
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
    final static class 寻找两个正序数组的中位数 extends 数组 {
        static void run() {
            System.out.println(new 寻找两个正序数组的中位数().findMedianSortedArrays(
                new int[] { 1, 2 }, new int[] { 3, 4 }));
        }

        double findMedianSortedArrays(int[] nums1, int[] nums2) {
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
    final static class 数组的相对排序 extends 数组 {

        public static void run() {
            数组的相对排序 数组的相对排序 = new 数组的相对排序();

            System.out.println(Arrays.toString(
                数组的相对排序.relativeSortArray(数组的相对排序.arr1(), 数组的相对排序.arr2())));
        }

        int[] arr1() {
            return new int[] { 2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19 };
        }

        int[] arr2() {
            return new int[] { 2, 1, 4, 3, 9, 6 };
        }

        int[] relativeSortArray(int[] arr1, int[] arr2) {

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

    final static class 升序数组查找数组中绝对值最小的值 extends 数组 {

        static void run() {
            升序数组查找数组中绝对值最小的值 升序数组查找数组中绝对值最小的值 = new 升序数组查找数组中绝对值最小的值();
            System.out.println(升序数组查找数组中绝对值最小的值
                .min(new int[] { -5, -3, -1, 2, 3, 4, 6, 7, 8, 9 }));
            System.out.println(升序数组查找数组中绝对值最小的值
                .min(new int[] { -5, -4, -3, 2, 3, 4, 6, 7, 8, 9 }));
            System.out.println(升序数组查找数组中绝对值最小的值
                .min(new int[] { -7, -6, -5, -4, -3, -2, -1, 1, 2, 3 }));
            System.out.println(升序数组查找数组中绝对值最小的值
                .min(new int[] { -7, -6, -5, -4, -3, -2, 1, 2, 3 }));
        }

        int min(int[] arr) {
            int left = 0, mid = 0, right = arr.length;
            while (left < right) {

                // System.out.println("left:" + left);
                // System.out.println("right:" + right);
                mid = left + (right - left) / 2;
                // System.out.println("mid:" + mid);
                // System.out.println("******************");

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
