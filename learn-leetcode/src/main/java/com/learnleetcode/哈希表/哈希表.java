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
        // System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("abcabcbb"));
        // System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("bbbbb"));
        // System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("pwwkew"));
        // System.out.println(无重复字符的最长子串.lengthOfLongestSubstring("abba"));

        // System.out.println(只出现一次的数字.singleNumber(new int[] { 2, 2, 1 }));
        // 只出现一次的数字.run();
        四数相加II.run();
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
    static class 四数相加II {

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
