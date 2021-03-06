package com.learnleetcode.字符串;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.learnleetcode.LeetCode;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/17 10:04
 */
public class 字符串 extends LeetCode {

    public static void main(String[] args) {

        最长公共前缀.run();
        将数组拆分成斐波那契序列.run();
        字母异位词分组.run();
    }

    /**
     * 49. 字母异位词分组
     * 
     * <pre>
     * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
     *
     * 示例:
     *
     * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出:
     * [
     *   ["ate","eat","tea"],
     *   ["nat","tan"],
     *   ["bat"]
     * ]
     * </pre>
     */
    final static class 字母异位词分组 extends 字符串 {

        static void run() {
            System.out.println(new 字母异位词分组().groupAnagrams(
                new String[] { "eat", "tea", "tan", "ate", "nat", "bat" }));
        }

        /**
         * 将字母排序，排序后的肯定是相等，将相等的加入到集合中
         */
        List<List<String>> groupAnagrams(String[] strs) {

            Map<String, List<String>> map = new HashMap<>();

            for (String str : strs) {

                char[] chars = str.toCharArray();

                Arrays.sort(chars);

                List<String> list = map.getOrDefault(Arrays.toString(chars),
                    new ArrayList<>());

                list.add(str);
                map.put(Arrays.toString(chars), list);

            }
            return new ArrayList<>(map.values());
        }
    }

    /**
     * 842. 将数组拆分成斐波那契序列
     * 
     * <pre>
     * 给定一个数字字符串 S，比如 S = "123456579"，我们可以将它分成斐波那契式的序列 [123, 456, 579]。
     * https://leetcode-cn.com/problems/split-array-into-fibonacci-sequence/
     * </pre>
     */
    final static class 将数组拆分成斐波那契序列 extends 字符串 {

        static void run() {
            System.out
                .println(new 将数组拆分成斐波那契序列().splitIntoFibonacci("123456579"));
        }

        int len;

        List<Integer> splitIntoFibonacci(String s) {
            len = s.length();
            List<Integer> res = new ArrayList<>();
            return dfs(0, s, res) ? res : new ArrayList<>();
        }

        private boolean dfs(int index, String s, List<Integer> res) {
            // 字符串长度
            int size = res.size();

            // 遍历到最后一位，判读结果集中是否大于2个，大于2个及  A+B=C 是斐波那契序列
            if (index == len) {
                if (size > 2) {

                    return true;
                } else
                    return false;
            }

            // System.out.println("#########结果值不相等重新开始计算");
            // 循环判断字符串中的结果值
            int num = 0;
            for (int i = index; i < len; i++) {

                // 计算:结果值
                num = 10 * num + s.charAt(i) - '0';

                //判断:结果值 是否溢出，2正数相加溢出 = 负数， 2负数溢出 = 正数
                if (num < 0) {
                    return false;
                }

                // System.out.println(size - 2 >= 0 ? res.get(size - 2) : "");
                // System.out.println(size - 1 >= 0 ? res.get(size - 1) : "");
                // System.out.println("num:" + num);
                // System.out.println("***********************");

                // 结果集中小于2个,直接加入
                // A+B = num 加入
                if (size < 2 || num == res.get(size - 1) + res.get(size - 2)) {
                    res.add(num);
                    // 递归 下一个字符, 如果相等返回
                    if (dfs(i + 1, s, res)) {
                        return true;
                    }
                    // 不相等 移除
                    res.remove(size);
                }
                //判断是否以0开头，阻止循环到下一位
                if (s.charAt(i) - '0' == 0 && i == index) {
                    return false;
                }
            }
            return false;
        }

    }

    /**
     * 1370. 上升下降字符串
     * 
     * <pre>
     * 桶排序 
     * https://leetcode-cn.com/problems/increasing-decreasing-string/solution/javadai-ma-2msji-bai-liao-100de-yong-hu-by-sdwwld/
     * </pre>
     **/
    final static class 上升下降字符串 extends 字符串 {
        public String sortString(String s) {
            //相当于26个桶
            int[] bucket = new int[26];
            char[] charArr = s.toCharArray();
            //把s中的字符分别放到对应的桶里
            for (char c : charArr) {
                bucket[c - 'a']++;
            }
            //存储计算的结果
            char[] res = new char[s.length()];
            int index = 0;
            while (index < s.length()) {
                //先从左往右找，遍历26个桶,如果当前桶不为空，
                //就从当前桶里拿出一个元素出来
                for (int i = 0; i < 26; i++) {
                    if (bucket[i] != 0) {
                        res[index++] = (char) (i + 'a');
                        bucket[i]--;//拿出之后桶中元素的个数要减1
                    }
                }
                //从右往左拿，同上
                for (int i = 25; i >= 0; i--) {
                    if (bucket[i] != 0) {
                        res[index++] = (char) (i + 'a');
                        bucket[i]--;
                    }
                }
            }
            //把结果转化为字符串
            return new String(res);
        }
    }

    /**
     * [14]最长公共前缀
     **/
    final static class 最长公共前缀 extends 字符串 {
        static void run() {
            System.out.println(new 最长公共前缀().longestCommonPrefix(
                new String[] { "flower", "flow", "flight" }));
        }

        String longestCommonPrefix(String[] strs) {
            if (strs.length == 0) {
                return "";
            }
            // 默认第1个数组
            String ans = strs[0];
            // 从第2个数组开始比较
            for (int i = 1; i < strs.length; i++) {
                int j = 0;

                // 挨个跟存储的字符 比较是否相等
                for (; j < ans.length() && j < strs[i].length(); j++) {
                    // 是否和存储的字符相同
                    if (ans.charAt(j) != strs[i].charAt(j)) {
                        break;
                    }
                }
                // 相等 保存 相同字符串
                ans = ans.substring(0, j);
                if (ans.equals("")) {
                    return ans;
                }
            }
            return ans;
        }
    }

    /**
     * [20]有效的括号
     **/
    static class 有效的括号 {
        public static boolean isValid(String s) {
            if (s.length() % 2 != 0) {
                return false;
            }
            // 利用栈的特性,后进先出
            Stack<Character> stack = new Stack<>();
            for (char c : s.toCharArray()) {
                if (c == '(') {
                    stack.push(')');
                } else if (c == '[') {
                    stack.push(']');
                }
                // 出栈
                else if (c == '{') {
                    stack.push('}');
                }
                // 出栈不相等 就不是有效括号
                else if (stack.isEmpty() || c != stack.pop()) {
                    return false;
                }
            }
            return stack.empty();
        }
    }

    /**
     * [58]最后一个单词的长度
     **/
    static class 最后一个单词的长度 {
        static int lengthOfLastWord(String s) {
            int len = s.length(), lastWordLen = 0;
            if (len == 0) {
                return 0;
            }

            // 倒着数
            for (int i = len - 1; i >= 0; i--) {
                // 没空格说明还是个单词
                if (s.charAt(i) != ' ') {
                    lastWordLen++;
                }
                // 有空格 & 长度大于0  ,说明到了最后个单词
                if (s.charAt(i) == ' ' && lastWordLen > 0) {
                    break;
                }
            }
            return lastWordLen;
        }
    }

    /**
     * [28]实现 strStr()
     * 方法一：子串逐一比较 - 线性时间复杂度
     **/
    static class 实现strStr extends 字符串 {
        public int strStr(String haystack, String needle) {
            int L = needle.length(), n = haystack.length();

            // 5
            // 5-2+1 = 3
            // 挨个比较
            for (int start = 0; start < n - L + 1; ++start) {
                if (haystack.substring(start, start + L).equals(needle)) {
                    return start;
                }
            }
            return -1;
        }
    }

    /***
     * 5. 最长回文子串
     * 
     * <pre>
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     *
     * 示例 1：
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     * 
     * 示例 2：
     * 输入: "cbbd"
     * 输出: "bb"
     * </pre>
     */
    static class 最长回文子串 {
        public static String longestPalindrome(String s) {

            return null;
        }
    }
}
