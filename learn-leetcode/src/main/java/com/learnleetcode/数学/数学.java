package com.learnleetcode.数学;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/17 10:10
 */
public class 数学 {
    public static void main(String[] args) {
        System.out.println('0' - '1');
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

    /**
     * [67]二进制求和
     **/
    static class 二进制求和 {

        public static String addBinary3(String a, String b) {
            StringBuilder ans = new StringBuilder();
            int ca = 0;
            // 递减
            for (int i = a.length() - 1, j = b.length() - 1; i >= 0
                || j >= 0; i--, j--) {
                int sum = ca;
                // 递减:相加 
                sum += (i >= 0 ? a.charAt(i) - '0' : 0);
                sum += (j >= 0 ? b.charAt(j) - '0' : 0);
                // 求余数
                ans.append(sum % 2);
                // 求进位,如果进位ca的值带入下个循环
                ca = sum / 2;
            }
            // 最后的ca是否进位
            ans.append(ca == 1 ? ca : "");
            // 翻转
            return ans.reverse().toString();
        }

        public static String addOctal(String a, String b) {
            StringBuilder ans = new StringBuilder();
            int ca = 0;
            for (int i = a.length() - 1, j = b.length() - 1; i >= 0
                || j >= 0; i--, j--) {
                int sum = ca;
                sum += (i >= 0 ? a.charAt(i) - '0' : 0);
                sum += (j >= 0 ? b.charAt(j) - '0' : 0);
                ans.append(sum % 8);
                ca = sum / 8;
            }
            ans.append(ca == 8 ? ca : "");
            return ans.reverse().toString();
        }

        public String addBinary(String a, String b) {
            return Integer.toBinaryString(
                Integer.parseInt(a, 2) + Integer.parseInt(b, 2));
        }

        public String addBinary2(String a, String b) {
            StringBuffer ans = new StringBuffer();

            int n = Math.max(a.length(), b.length()), carry = 0;
            for (int i = 0; i < n; ++i) {
                carry += i < a.length() ? (a.charAt(a.length() - 1 - i) - '0')
                    : 0;
                carry += i < b.length() ? (b.charAt(b.length() - 1 - i) - '0')
                    : 0;
                ans.append((char) (carry % 2 + '0'));
                carry /= 2;
            }

            if (carry > 0) {
                ans.append('1');
            }
            ans.reverse();

            return ans.toString();
        }
    }

    /**
     * [13]罗马数字转整数
     **/
    static class 罗马数字转整数 {
        public static int romanToInt(String s) {
            int sum = 0;

            // 默认第0
            int preNum = getValue(s.charAt(0));

            // 从第1个开始
            for (int i = 1; i < s.length(); i++) {
                int num = getValue(s.charAt(i));

                // 左边的数比右边的小,减 
                if (preNum < num) {
                    sum -= preNum;
                }
                // 左边的数比右边的大,加 
                else {
                    sum += preNum;
                }
                //刷新比较数据
                preNum = num;
            }

            sum += preNum;
            return sum;
        }

        private static int getValue(char ch) {
            switch (ch) {
                case 'I':
                    return 1;
                case 'V':
                    return 5;
                case 'X':
                    return 10;
                case 'L':
                    return 50;
                case 'C':
                    return 100;
                case 'D':
                    return 500;
                case 'M':
                    return 1000;
                default:
                    return 0;
            }
        }
    }

    /**
     * 9. 回文数
     * 
     * <pre>
     * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     * 示例 1:
     * 输入: 121
     * 输出: true
     * 
     * 示例 2:
     * 输入: -121
     * 输出: false
     * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
     * 
     * 示例 3:
     * 输入: 10
     * 输出: false
     * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
     * </pre>
     **/
    static class 回文数 {
        public boolean isPalindrome(int x) {
            if (x < 0) {
                return false;
            }
            int cur = 0;
            int num = x;
            while (num != 0) {
                // 把最后一位往前放
                cur = cur * 10 + num % 10;
                // 原数字减少一位
                num /= 10;
            }

            // 判断是否相等
            return cur == x;
        }
    }

}
