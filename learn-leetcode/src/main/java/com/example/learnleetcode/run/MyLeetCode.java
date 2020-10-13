package com.example.learnleetcode.run;

import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * MyLeetCode
 *
 * @author fengqing
 * @date 2020/8/21 10:31
 */
public class MyLeetCode {

    public static void main(String[] args) {

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
        System.out.println(二进制求和.addOctal("1510", "171"));
    }

    /**
     * [69]x 的平方根
     **/
    static class x的平方根 {

        public static int mySqrt(int x) {
            // 左标记、右标记
            int l = 0, r = x, ans = -1;

            while (l <= r) {
                // 求中间值：左标记+ (左-右)/2
                int mid = l + (r - l) / 2;
                if ((long) mid * mid <= x) {
                    ans = mid;
                    l = mid + 1;
                } else {
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
     * [66]加一
     **/
    static class 加一 {
        public static int[] plusOne(int[] digits) {
            int len = digits.length;
            for (int i = len - 1; i >= 0; i--) {
                digits[i]++;
                digits[i] %= 10;
                if (digits[i] != 0)
                    return digits;
            }
            digits = new int[len + 1];
            digits[0] = 1;
            return digits;
        }
    }

    /**
     * [58]最后一个单词的长度
     **/
    static class 最后一个单词的长度 {
        static int lengthOfLastWord(String s) {
            int len = s.length(), lastWordLen = 0;
            if (len == 0)
                return 0;

            for (int i = len - 1; i >= 0; i--) {
                if (s.charAt(i) != ' ')
                    lastWordLen++;
                if (s.charAt(i) == ' ' && lastWordLen > 0)
                    break;
            }
            return lastWordLen;
        }
    }

    /**
     * [53]最大子序和
     **/
    static class 最大子序和 {
        public static class Status {
            public int lSum, rSum, mSum, iSum;

            public Status(int lSum, int rSum, int mSum, int iSum) {
                this.lSum = lSum;
                this.rSum = rSum;
                this.mSum = mSum;
                this.iSum = iSum;
            }
        }

        public static int maxSubArray(int[] nums) {
            return getInfo(nums, 0, nums.length - 1).mSum;
        }

        public static Status getInfo(int[] a, int l, int r) {
            if (l == r) {
                return new Status(a[l], a[l], a[l], a[l]);
            }
            int m = (l + r) >> 1;
            Status lSub = getInfo(a, l, m);
            Status rSub = getInfo(a, m + 1, r);
            return pushUp(lSub, rSub);
        }

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
            int ans = nums[0];
            int sum = 0;
            for (int num : nums) {
                if (sum > 0) {
                    sum += num;
                } else {
                    sum = num;
                }
                ans = Math.max(ans, sum);
            }
            return ans;
        }

        /**
         * 方案：滚动变量优化
         **/
        public static int maxSubArray2(int[] nums) {
            // 初始化第一次结果值 和 最大值
            int sum = nums[0], ans = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sum = Math.max(nums[i], sum + nums[i]);
                ans = Math.max(ans, sum);
            }
            return ans;
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

                if (nums[mid] < target)
                    left = mid + 1; // 注意
                else if (nums[mid] > target)
                    right = mid; // 注意
                else {
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
     * [28]实现 strStr()
     * 方法一：子串逐一比较 - 线性时间复杂度
     **/
    static class 实现strStr {
        public int strStr(String haystack, String needle) {
            int L = needle.length(), n = haystack.length();

            for (int start = 0; start < n - L + 1; ++start) {
                if (haystack.substring(start, start + L).equals(needle)) {
                    return start;
                }
            }
            return -1;
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
            for (int i = 0; i < ans; ) {
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
                    length--;
                else
                    nums[j++] = nums[i + 1]; //若两个相邻元素不同，那么对另外一个元素进行存储

            }
            System.out.println(ArrayUtils.toString(nums));
            return length;
        }

        // 方式2 ： 双指针
        public static int removeDuplicates2(int[] nums) {
            if (nums == null || nums.length == 0)
                return 0;
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
     * [21]合并两个有序链表
     **/
    static class 合并两个有序链表 {
        public class ListNode {
            int val;
            ListNode next;

            ListNode(int x) {
                this.val = x;
            }
        }

        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            if (l1 == null)
                return l2;
            if (l2 == null)
                return l1;

            if (l1.val < l2.val) {
                l1.next = this.mergeTwoLists(l1.next, l2);
                return l1;
            } else {
                l2.next = this.mergeTwoLists(l1, l2.next);
                return l2;
            }
        }
    }

    /**
     * [20]有效的括号
     **/
    static class 有效的括号 {
        public static boolean isValid(String s) {
            if (s.length() % 2 != 0)
                return false;
            Stack<Character> stack = new Stack<>();
            for (char c : s.toCharArray()) {
                if (c == '(')
                    stack.push(')');
                else if (c == '[')
                    stack.push(']');
                else if (c == '{')
                    stack.push('}');
                else if (stack.isEmpty() || c != stack.pop()) {
                    return false;
                }
            }
            return stack.empty();
        }
    }

    /**
     * [14]最长公共前缀
     **/
    static class 最长公共前缀 {
        public String longestCommonPrefix(String[] strs) {
            if (strs.length == 0)
                return "";
            String ans = strs[0];
            for (int i = 1; i < strs.length; i++) {
                int j = 0;
                for (; j < ans.length() && j < strs[i].length(); j++) {
                    if (ans.charAt(j) != strs[i].charAt(j))
                        break;
                }
                ans = ans.substring(0, j);
                if (ans.equals(""))
                    return ans;
            }
            return ans;
        }
    }

    /**
     * [13]罗马数字转整数
     **/
    static class 罗马数字转整数 {
        public static int romanToInt(String s) {
            int sum = 0;
            int preNum = getValue(s.charAt(0));
            for (int i = 1; i < s.length(); i++) {
                int num = getValue(s.charAt(i));
                if (preNum < num) {
                    sum -= preNum;
                } else {
                    sum += preNum;
                }
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
     * [9]回文数
     **/
    static class 回文数 {
        public boolean isPalindrome(int x) {
            if (x < 0)
                return false;
            int cur = 0;
            int num = x;
            while (num != 0) {
                cur = cur * 10 + num % 10;
                num /= 10;
            }
            return cur == x;
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
                        return new int[]{i, j};
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
                    return new int[]{(int) map.get(target - data), i};
                }
            }
            throw new IllegalArgumentException("No two sum solution");
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
}
