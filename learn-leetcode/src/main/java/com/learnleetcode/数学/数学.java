package com.learnleetcode.数学;

import com.learnleetcode.LeetCode;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/17 10:10
 */
class 数学 extends LeetCode {

    public static void main(String[] args) {
        x的平方根.run();
        Excel表列名称.run();
        Excel表列序号.run();
        阶乘后的零.run();
        快乐数.run();
    }

    /**
     * 202. 快乐数
     * 
     * <pre>
     * 编写一个算法来判断一个数 n 是不是快乐数。
     *
     * 「快乐数」定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。如果 可以变为  1，那么这个数就是快乐数。
     *
     * 如果 n 是快乐数就返回 True ；不是，则返回 False 。
     *
     * 示例：
     * 输入：19
     * 输出：true
     * 解释：
     * 12 + 92 = 82
     * 82 + 22 = 68
     * 62 + 82 = 100
     * 12 + 02 + 02 = 1
     * </pre>
     */

    final static class 快乐数 extends 数学 {

        static void run() {
            快乐数 快乐数 = new 快乐数();
            System.out.println(快乐数.isHappy(2));
        }

        public boolean isHappy(int n) {
            int slow = n, fast = squareSum(n);
            while (slow != fast) {
                slow = squareSum(slow);
                fast = squareSum(fast);
                fast = squareSum(fast);
            }
            return slow == 1;
        }

        public int squareSum(int n) {
            int sum = 0;
            while (n > 0) {
                int digit = n % 10;
                sum += digit * digit;
                n = n / 10;
            }
            return sum;
        }
    }

    /**
     * 172. 阶乘后的零
     * 
     * <pre>
     * 给定一个整数 n，返回 n! 结果尾数中零的数量。
     *
     * 示例 1:
     * 输入: 3
     * 输出: 0
     * 解释: 3! = 6, 尾数中没有零。
     * 
     * 示例 2:
     * 输入: 5
     * 输出: 1
     * 解释: 5! = 120, 尾数中有 1 个零.
     * </pre>
     */
    static class 阶乘后的零 extends 数学 {

        static void run() {
            阶乘后的零 阶乘后的零 = new 阶乘后的零();
            System.out.println(阶乘后的零.trailingZeroes(5));
        }

        int trailingZeroes(int n) {
            int zeroCount = 0;
            while (n > 0) {
                n /= 5;
                zeroCount += n;
            }
            return zeroCount;
        }
    }

    /**
     * 171. Excel表列序号
     * 
     * <pre>
     * 给定一个Excel表格中的列名称，返回其相应的列序号。
     *
     * 例如，
     *     A -> 1
     *     B -> 2
     *     C -> 3
     *     ...
     *     Z -> 26
     *     AA -> 27
     *     AB -> 28 
     *     ...
     * </pre>
     */
    static class Excel表列序号 extends 数学 {
        static void run() {
            // System.out.println(new Excel表列序号().titleToNumber("ZYA"));
        }

        public int titleToNumber(String s) {

            int n = 0;
            for (int i = 0; i < s.length(); i++) {
                int t = (s.charAt(i) - 'A' + 1);
                n = n * 26 + t;
            }
            return n;
        }
    }

    /**
     * 168. Excel表列名称
     * 
     * <pre>
     * 给定一个正整数，返回它在 Excel 表中相对应的列名称。
     *
     * 例如：
    *     1 -> A
    *     2 -> B
    *     3 -> C
    *     ...
    *     26 -> Z
    *     27 -> AA
    *     28 -> AB 
    *     ...
     * </pre>
     */
    static class Excel表列名称 {

        static void run() {
            // System.out.println(new Excel表列名称().convertToTitle(486));
            // System.out.println(new Excel表列名称().convertToTitle(704));
        }

        public String convertToTitle(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            while (n != 0) {
                n--;
                stringBuilder.append((char) ('A' + n % 26));
                n /= 26;
            }
            return stringBuilder.reverse().toString();

        }
    }

    /** [69]x 的平方根 **/
    static class x的平方根 {

        static void run() {
            // System.out.println(new x的平方根().mySqrt(10));
        }

        int mySqrt(int x) {
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
