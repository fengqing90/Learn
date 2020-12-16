package com.learnleetcode.哈希表;

import java.util.HashMap;
import java.util.Map;

import com.learnleetcode.LeetCode;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/16 18:45
 */
public class 哈希表 extends LeetCode {

    public static void main(String[] args) {
        // System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("abcabcbb"));
        // System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("bbbbb"));
        // System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("pwwkew"));
        // System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("abba"));

        // System.out.println(只出现一次的数字.singleNumber(new int[] { 2, 2, 1 }));
        // 只出现一次的数字.run();
        四数相加II.run();
        单词规律.run();
    }

    /**
     * 290. 单词规律
     * 
     * <pre>
     *  给定一种规律 pattern 和一个字符串 str ，判断 str 是否遵循相同的规律。
     *
     * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之间存在着双向连接的对应规律。
     *
     * 示例1:
     *
     * 输入: pattern = "abba", str = "dog cat cat dog"
     * 输出: true
     * 示例 2:
     *
     * 输入:pattern = "abba", str = "dog cat cat fish"
     * 输出: false
     * 示例 3:
     *
     * 输入: pattern = "aaaa", str = "dog cat cat dog"
     * 输出: false
     * 示例 4:
     *
     * 输入: pattern = "abba", str = "dog dog dog dog"
     * 输出: false
     * 说明:
     * 你可以假设 pattern 只包含小写字母， str 包含了由单个空格分隔的小写字母。    
     * </pre>
     */
    final static class 单词规律 extends 哈希表 {

        static void run() {

            单词规律 单词规律 = new 单词规律();

            System.out.println("abba = dog cat cat dog : "
                + 单词规律.wordPattern("abba", "dog cat cat dog"));

            System.out.println("abba = dog cat cat fish : "
                + 单词规律.wordPattern("abba", "dog cat cat fish"));

            System.out.println("aaaa = dog cat cat dog : "
                + 单词规律.wordPattern("aaaa", "dog cat cat dog"));

            System.out.println("aaaa = dog dog dog dog : "
                + 单词规律.wordPattern("aaaa", "dog dog dog dog"));

            System.out.println("abba = dog dog dog dog : "
                + 单词规律.wordPattern("abba", "dog dog dog dog"));
        }

        public boolean wordPattern(String pattern, String s) {

            if (pattern == null || s == null) {
                return false;
            }

            // 长度不相等，不是
            String[] strings = s.split(" ");
            if (strings.length != pattern.length()) {
                return false;
            }

            Map<Character, String> p_map = new HashMap<>();
            Map<String, Character> s_map = new HashMap<>();

            for (int i = 0; i < pattern.length(); i++) {

                // s_map中，如果p存在肯定相等
                String s_v = p_map.get(pattern.charAt(i));
                if (s_v != null && !s_v.equals(strings[i])) {
                    return false;
                }
                if (s_v == null) {
                    p_map.put(pattern.charAt(i), strings[i]);
                }

                // p_map中，如果s存在肯定相等
                Character p_v = s_map.get(strings[i]);
                if (p_v != null && !p_v.equals(pattern.charAt(i))) {
                    return false;
                }
                if (p_v == null) {
                    s_map.put(strings[i], pattern.charAt(i));
                }

                // 长度肯定相等
                if (p_map.size() != s_map.size()) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * 454. 四数相加 II
     * 
     * <pre>
     * 给定四个包含整数的数组列表 A , B , C , D ,计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。
     * 为了使问题简单化，所有的 A, B, C, D 具有相同的长度 N，且 0 ≤ N ≤ 500 。所有整数的范围在 -228 到 228 - 1 之间，最终结果不会超过 231 - 1 。
     *
     * 例如:
     * 输入:
     * A = [ 1, 2]
     * B = [-2,-1]
     * C = [-1, 2]
     * D = [ 0, 2]
     *
     * 输出:
     * 2
     *
     * 解释:
     * 两个元组如下:
     * 1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
     * 2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0
     * </pre>
     */
    static class 四数相加II extends 哈希表 {

        static void run() {
            System.out.println(fourSumCount(new int[] { 1, 2 },
                new int[] { -2, -1 }, new int[] { -1, 2 }, new int[] { 0, 2 }));
        }

        /**
         * <pre>
         * 1段：(A+B) + 2段：(B+C) = 0
         * 1段结果存到map中。
         * 2段结果必然是1段的相反数，判断2段结果是否在1段中。
         * </pre>
         */
        public static int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
            Map<Integer, Integer> map = new HashMap<>();

            int res = 0;
            for (int element : A) {
                for (int value : B) {
                    int sumAB = element + value;
                    if (map.containsKey(sumAB)) {
                        map.put(sumAB, map.get(sumAB) + 1);
                    } else
                        map.put(sumAB, 1);
                }
            }

            for (int item : C) {
                for (int value : D) {
                    int sumCD = -(item + value);
                    if (map.containsKey(sumCD)) {
                        res += map.get(sumCD);
                    }
                }
            }
            return res;
        }
    }

    /**
     * 136. 只出现一次的数字
     * 
     * <pre>
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     *
     * 说明：你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     *
     * 示例 1:
     * 输入: [2,2,1]
     * 输出: 1
     * 
     * 示例 2:
     * 输入: [4,1,2,1,2]
     * 输出: 
     * 
     * ******************************
     * 异或运算有以下三个性质。
     * 1、任何数和 00 做异或运算，结果仍然是原来的数，即 a ⊕ 0=aa⊕0=a。
     * 2、任何数和其自身做异或运算，结果是 00，即 a ⊕ a=0a⊕a=0。
     * 3、异或运算满足交换律和结合律，即 a ⊕ b ⊕ a=b ⊕ a ⊕ a=b ⊕ (a ⊕ a)=b ⊕0=ba⊕b⊕a=b⊕a⊕a=b⊕(a⊕a)=b⊕0=b。
     * </pre>
     **/
    static class 只出现一次的数字 {
        static void run() {
            System.out.println(singleNumber(new int[] { 4, 1, 2, 1, 2 }));
        }

        public static int singleNumber(int[] nums) {
            int single = 0;
            for (int num : nums) {
                // System.out.println(Integer.toBinaryString(num));
                // System.out.println(Integer.toBinaryString(single));
                single ^= num;
                // System.out.println("***************");
                // System.out.println(Integer.toBinaryString(single));
                // System.out.println("***************");
            }
            return single;
        }

        public static int singleNumber2(int[] nums) {

            for (int i : nums) {
                int t = 0;
                for (int j : nums) {
                    if (i == j) {
                        t++;
                    }
                }
                if (t == 1) {
                    return i;
                }
            }

            return -1;
        }
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
