package com.learnleetcode.哈希表;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/16 18:45
 */
public class 哈希表 {

    public static void main(String[] args) {
        System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("bbbbb"));
        System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("pwwkew"));
        System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("abba"));
    }

    /**
     * 3. 无重复字符的最长子串
     *
     * <pre>
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     *
     * 示例 2:
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     *
     * 示例 3:
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     * </pre>
     **/
    static class 无重复字符的最长子串 {
        public static int lengthOfLongestSubstring(String s) {

            if (s == null || s.length() == 0) {
                return 0;
            }

            Map<Character, Integer> map = new HashMap<>();

            int max = 0, left = 0;

            for (int i = 0; i < s.length(); i++) {

                // 是否出现过
                if (map.containsKey(s.charAt(i))) {
                    // 出现过 覆盖 i 字符的位置， 以后出现的下标计算
                    left = Math.max(left, map.get(s.charAt(i)) + 1);
                }

                // 放入 第 i 出现位置
                map.put(s.charAt(i), i);

                // 最大值 = 当前位置- i出现的位置+1
                max = Math.max(max, i - left + 1);

            }

            return max;
        }
    }
}
