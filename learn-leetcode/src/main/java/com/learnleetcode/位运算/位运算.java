package com.learnleetcode.位运算;

import com.learnleetcode.LeetCode;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/15 15:12
 */
public class 位运算 extends LeetCode {

    public static void main(String[] args) {

        System.out.println(8 & 1);
    }

    /**
     * 191. 位1的个数
     * 
     * <pre>
     *     编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中数字位数为 '1' 的个数（也被称为汉明重量）。
     * </pre>
     */
    final static class 位1的个数 extends 位运算 {

        public static void main(String[] args) {

            位1的个数 位1的个数 = new 位1的个数();

            System.out.println(
                Integer.toBinaryString(8) + " : " + 位1的个数.hammingWeight(8));
            System.out.println(
                Integer.toBinaryString(-3) + " : " + 位1的个数.hammingWeight(-3));
            System.out.println(
                Integer.toBinaryString(10) + " : " + 位1的个数.hammingWeight(10));
            System.out.println(
                Integer.toBinaryString(10) + " : " + 位1的个数.hammingWeight3(10));
        }

        public int hammingWeight3(int n) {
            int count = 0;
            while (n != 0) {
                System.out.println(Integer.toBinaryString(n));
                System.out.println(Integer.toBinaryString(n - 1));
                n = n & (n - 1);
                System.out.println(Integer.toBinaryString(n));
                System.out.println("*********");
                count++;
            }
            return count;
        }

        /**
         * n 变
         */
        int hammingWeight(int n) {
            int r = 0;
            for (int i = 0; i < 32; i++) {
                if (n == 0) {
                    break;
                }

                if ((n & 1) == 1) {
                    r++;
                }
                n >>= 1;
            }

            return r;

        }

        /**
         * n 不变
         */
        int hammingWeight2(int n) {
            int r = 0;
            int c = 1;
            for (int i = 0; i < 32; i++) {
                if ((n & c) != 0) {
                    r++;
                }
                c <<= 1;
            }

            return r;
        }
    }
}
