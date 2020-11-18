package com.learnleetcode.run;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

/**
 * MyLeetCode
 *
 * @author fengqing
 * @date 2020/8/21 10:31
 */
public class MyLeetCode {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            this.val = x;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {

        System.out.println(ArrayUtils.toString(null));
        // System.out.println(有效的括号.isValid("{[]{[(){}()]}[]}"));

        // System.out.println(删除排序数组中的重复项
        //     .removeDuplicates(new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 }));
        // System.out.println(删除排序数组中的重复项
        //     .removeDuplicates1(new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 }));

        // System.out.println(搜索插入位置.searchInsert(new int[] { 1, 3, 5, 6 }, 5));
        // System.out.println(搜索插入位置.searchInsert(new int[] { 1, 3, 5, 6 }, 2));
        // System.out.println(搜索插入位置.searchInsert(new int[] { 1, 3, 5, 6 }, 7));
        // System.out.println(搜索插入位置.searchInsert(new int[] { 1, 3, 5, 6 }, 0));
        // System.out.println(搜索插入位置.searchInsert(new int[] { 1, 3, 5, 6 }, 6));

        // System.out.println(
        //     最大子序和.maxSubArray(new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 }));

        // System.out.println(最后一个单词的长度.lengthOfLastWord("Hello World"));

        // System.out.println(
        //     ArrayUtils.toString(加一.plusOne(new int[] { 1, 2, 3, 9, 999 })));

        // System.out.println(ArrayUtils.toString(x的平方根.mySqrt(2)));

        // System.out.println(二进制求和.addBinary3("1010", "101"));
        // System.out.println(二进制求和.addOctal("1510", "171"));

        // System.out.println(Arrays.toString(合并两个有序数组.merge(
        //     new int[] { 0, 1, 2, 3, 0, 0, 0 }, 4, new int[] { 2, 5, 6 }, 3)));

        // System.out
        //     .println(二叉树的层次遍历II.levelOrderBottom(二叉树的层次遍历II.getTreeNode()));

    }

    static class 合并两个有序数组 {
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

            // add missing elements from nums2
            // 将nums2中未加入到nums1中的数放到头部
            System.arraycopy(nums2, 0, nums1, 0, p2 + 1);

            return nums1;
        }

    }

    /**
     * [7]整数反转
     **/
    static class 整数反转 {
        //方式1： int类型 除10  模10 相加
        public int reverse(int x) {
            int rev = 0;
            while (x != 0) {
                int pop = x % 10;
                x = x / 10;

                if (rev > Integer.MAX_VALUE / 10
                    || (rev == Integer.MAX_VALUE / 10 && pop > 7)) {
                    return 0;
                }
                if (rev < Integer.MIN_VALUE / 10
                    || (rev == Integer.MIN_VALUE / 10 && pop < -8)) {
                    return 0;
                }

                rev = rev * 10 + pop;
            }
            return rev;
        }

        // 方式2: 字符串翻转
        public int reverse2(int x) {

            StringBuilder sb = new StringBuilder(x + "");
            if (x < 0)
                sb.replace(0, 1, "");

            sb.reverse();

            try {
                x = Integer.parseInt((x < 0 ? "-" : "") + sb.toString());
            } catch (Exception e) {
                x = 0;
            }

            return x;

        }

        // 方式3: 用long 类型存储， 最后向下转型，核心方式与方式1类似
        public int reverse3(int x) {
            return 0;
        }
    }

    /**
     * [1]两数之和
     **/
    static class 两数之和 {
        // 方式1 时间复杂度：O(n^2)
        public int[] twoSum(int[] nums, int target) {
            for (int i = 0; i < nums.length; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (target == nums[j] + nums[i]) {
                        return new int[] { i, j };
                    }
                }
            }
            throw new IllegalArgumentException("No two sum solution");
        }

        // 方式2  时间复杂度：O(n)
        public int[] twoSum2(int[] nums, int target) {
            Map map = new HashMap();
            for (int i = 0; i < nums.length; i++) {
                int data = nums[i];
                if (map.get(target - data) == null) {
                    map.put(data, i);
                } else {
                    return new int[] { (int) map.get(target - data), i };
                }
            }
            throw new IllegalArgumentException("No two sum solution");
        }
    }

}
