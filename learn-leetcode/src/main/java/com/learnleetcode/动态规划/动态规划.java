package com.learnleetcode.动态规划;

import com.learnleetcode.LeetCode;

/**
 * TODO
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
