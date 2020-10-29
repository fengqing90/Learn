package com.learnleetcode.二分查找;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/21 19:42
 */
public class 二分查找 {

    public static void main(String[] args) {

    }

    /** [69]x 的平方根 **/
    static class x的平方根 {

        public static int mySqrt(int x) {
            // 左标记、右标记
            int l = 0, r = x, ans = -1;

            while (l <= r) {
                // 求中间值：左标记 + (左-右)/2
                int mid = l + (r - l) / 2;

                // 两个中间值 相乘 与原值比较

                // 小于等于: 1.记录值
                // 2. 缩小 左范围(从中间值开始)
                // 3. 再次循环  左右 
                if ((long) mid * mid <= x) {
                    ans = mid;
                    l = mid + 1;
                }
                // 大于等于:
                // 1.不记录值,应为超过原数值
                // 2. 缩小 右范围 = 中间值-1
                // 3. 再次循环  左右
                else {
                    r = mid - 1;
                }
            }
            return ans;
        }

    }
}
