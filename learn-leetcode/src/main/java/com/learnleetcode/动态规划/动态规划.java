package com.learnleetcode.动态规划;

import com.learnleetcode.LeetCode;

/**
 * 1.建立状态转移方程
 * 2.缓存并复用以往结果
 * 3.按顺序从小往大算
 *
 * @author fengqing
 * @date 2020/12/14 16:03
 */
public class 动态规划 extends LeetCode {

    public static void main(String[] args) {
        爬楼梯.run();

        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
    }

    /**
     * 152. 乘积最大子数组
     * https://leetcode-cn.com/problems/maximum-product-subarray/
     */
    final static class 乘积最大子数组 extends 动态规划 {

        public static void main(String[] args) {

            乘积最大子数组 乘积最大子数组 = new 乘积最大子数组();

            System.out.println(乘积最大子数组.maxProduct(new int[] { 2, 3, -2, 4 }));
            System.out.println(乘积最大子数组.maxProduct(new int[] { -2, 0, -1 }));
        }

        public int maxProduct(int[] nums) {
            // 初始化
            int mmax = Integer.MIN_VALUE, max = 1, min = 1;

            // 转移方程
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] < 0) {
                    int tmp = max; // 6
                    max = min; // 3
                    min = tmp; // 6
                }
                max = Math.max(nums[i], max * nums[i]); //6  -2   4
                min = Math.min(nums[i], min * nums[i]); //3  -12  -48

                mmax = Math.max(mmax, max); // 6
            }

            return mmax;
        }
    }

    /**
     * 64. 最小路径和
     * https://leetcode-cn.com/problems/minimum-path-sum/
     *
     * <pre>
     * 输入:
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * 输出: 7
     * 解释: 因为路径 1→3→1→1→1 的总和最小。
     *
     * 动态规划后数组：
     * [
     *   [1,4,5],
     *   [2,7,6],
     *   [6,8,7]
     * ]
     * </pre>
     */
    final static class 最小路径和 extends 动态规划 {

        public static void main(String[] args) {

            最小路径和 最小路径和 = new 最小路径和();

            // System.out.println(最小路径和.minPathSum(
            //     new int[][] { { 1, 3, 1 }, { 1, 5, 1 }, { 4, 2, 1 } }));
            System.out.println(
                最小路径和.minPathSum2(new int[][] { { 1, 2, 3 }, { 4, 5, 6 } }));
        }

        public int minPathSum2(int[][] grid) {

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    int tmp = Integer.MAX_VALUE;
                    if (i > 0)
                        tmp = Math.min(tmp, grid[i - 1][j]);
                    if (j > 0)
                        tmp = Math.min(tmp, grid[i][j - 1]);
                    grid[i][j] += tmp;
                }
            }
            return grid[grid.length - 1][grid[0].length - 1];
        }

        public int minPathSum(int[][] grid) {

            int dp[][] = new int[grid.length][grid[0].length];

            dp[0][0] = grid[0][0];

            for (int i = 1; i < grid.length; i++) {
                dp[i][0] = dp[i - 1][0] + grid[i][0];
            }

            for (int i = 1; i < grid[0].length; i++) {
                dp[0][i] = dp[0][i - 1] + grid[0][i];
            }

            for (int i = 1; i < grid.length; i++) {
                for (int j = 1; j < grid[0].length; j++) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1])
                        + grid[i][j];
                }
            }
            return dp[grid.length - 1][grid[0].length - 1];
        }
    }

    /**
     * 面试题 08.01. 三步问题
     * https://leetcode-cn.com/problems/three-steps-problem-lcci/
     */
    static final class 三步问题 extends 动态规划 {

        public static void main(String[] args) {
            三步问题 三步问题 = new 三步问题();
            System.out.println(三步问题.waysToStep(5));
        }

        /*
         * 当爬第 i 个阶梯时，有可能是从第 i−1 个阶梯爬上来的（选择只爬一层）；
         * 也有可能是从第 i−2个阶梯爬上来的（选择爬两层）；
         * 还有可能是从第 i−3 个阶梯爬上来的（选择爬三层）。
         */
        public int waysToStep(int n) {

            int mod = 1000000007;
            int[] ways = new int[n + 1];
            ways[0] = 1;
            for (int i = 1; i <= n; i++) {

                ways[i] = ways[i - 1];

                if (i >= 2)
                    ways[i] = (ways[i] + ways[i - 2]) % mod;
                if (i >= 3)
                    ways[i] = (ways[i] + ways[i - 3]) % mod;
            }
            return ways[n];
        }
    }

    /**
     * 70. 爬楼梯
     *
     * <pre>
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     *
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     *
     * 注意：给定 n 是一个正整数。
     *
     * 示例 1：
     *
     * 输入： 2
     * 输出： 2
     * 解释： 有两种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶
     * 2.  2 阶
     * 示例 2：
     *
     * 输入： 3
     * 输出： 3
     * 解释： 有三种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶 + 1 阶
     * 2.  1 阶 + 2 阶
     * 3.  2 阶 + 1 阶
     * </pre>
     */
    static final class 爬楼梯 extends 动态规划 {

        static void run() {
            爬楼梯 爬楼梯 = new 爬楼梯();
            System.out.println(爬楼梯.climbStairs(4));
            System.out.println(爬楼梯.climbStairs(5));
        }

        private int climbStairs(int n) {

            /*
             * 自底向上方式
             * 1 : 1 种
             * 2 : 2 种 （1,2）
             * 3 ： 3 种（1-1-1,1-2,2-1）
             * ...
             */

            int r = 1, a = 0, b = 0;
            for (int i = 1; i <= n; i++) {
                a = b;
                b = r;
                r = a + b;
            }
            return r;
        }
    }
}
