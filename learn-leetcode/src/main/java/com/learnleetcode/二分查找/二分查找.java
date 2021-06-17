package com.learnleetcode.二分查找;

import com.learnleetcode.LeetCode;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/21 19:42
 */
public class 二分查找 extends LeetCode {
    /**
     * 278. 第一个错误的版本
     * https://leetcode-cn.com/problems/first-bad-version/
     */
    final static class 第一个错误的版本 extends 二分查找 {

        public static void main(String[] args) {

            第一个错误的版本 第一个错误的版本 = new 第一个错误的版本();

            System.out.println(第一个错误的版本.firstBadVersion(5));
        }

        public int firstBadVersion(int n) {
            int left = 1;
            int right = n;
            while (left < right) {
                int mid = left + (right - left) / 2;
                // if (isBadVersion(mid)) {
                //     right = mid;
                // } else {
                //     left = mid + 1;
                // }
            }
            return left;
        }
    }
}
