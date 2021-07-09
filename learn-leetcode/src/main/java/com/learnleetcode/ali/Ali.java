package com.learnleetcode.ali;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.learnleetcode.LeetCode;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/10 15:07
 */

public class Ali extends LeetCode {

    /**
     * 121. 买卖股票的最佳时机
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
     */
    final static class 买卖股票的最佳时机 extends Ali {
        public static void main(String[] args) {

            买卖股票的最佳时机 买卖股票的最佳时机 = new 买卖股票的最佳时机();

            System.out
                .println(买卖股票的最佳时机.maxProfit(new int[] { 7, 1, 5, 3, 6, 4 }));
        }

        int maxProfit(int[] prices) {

            return 0;
        }

    }

    /**
     * 91. 解码方法
     * https://leetcode-cn.com/problems/decode-ways/
     */
    final static class 解码方法 extends Ali {

        public static void main(String[] args) {

            解码方法 解码方法 = new 解码方法();

            System.out.println(解码方法.numDecodings("2299"));
            System.out.println(解码方法.numDecodings2("2299"));
        }

        // 直接使用动态规划
        // 如果 最后两个数小于27 ，可以 拆分为两个
        public int numDecodings2(String s) {
            int len = s.length();
            if (len == 0 || s.charAt(0) == '0')
                return 0;
            // 转int
            int[] nums = new int[s.length()];
            for (int i = 0; i < s.length(); i++) {
                nums[i] = s.charAt(i) - '0';
            }
            System.out.println("nums:" + Arrays.toString(nums));
            int[] dp = new int[len + 1];
            dp[0] = 1;
            dp[1] = 1;
            for (int i = 2; i <= len; i++) {
                int x = nums[i - 2] * 10 + nums[i - 1];
                if (x < 27 && x > 9) {
                    dp[i] += dp[i - 2];
                }
                if (nums[i - 1] != 0) {
                    dp[i] += dp[i - 1];
                }
            }
            return dp[len];
        }

        public int numDecodings(String s) {
            int n = s.length();
            s = " " + s;
            char[] cs = s.toCharArray();
            int[] f = new int[n + 1];
            f[0] = 1;
            for (int i = 1; i <= n; i++) {
                // a : 代表「当前位置」单独形成 item
                // b : 代表「当前位置」与「前一位置」共同形成 item
                int a = cs[i] - '0', b = (cs[i - 1] - '0') * 10 + (cs[i] - '0');
                // 如果 a 属于有效值，那么 f[i] 可以由 f[i - 1] 转移过来
                if (1 <= a && a <= 9)
                    f[i] = f[i - 1];
                // 如果 b 属于有效值，那么 f[i] 可以由 f[i - 2] 或者 f[i - 1] & f[i - 2] 转移过来
                if (10 <= b && b <= 26)
                    f[i] += f[i - 2];
            }

            System.out.println("f:" + Arrays.toString(f));
            return f[n];
        }

    }

    /**
     * 【ali】55. 跳跃游戏
     * https://leetcode-cn.com/problems/jump-game/
     */
    final static class 跳跃游戏 extends Ali {

        public static void main(String[] args) {

            跳跃游戏 跳跃游戏 = new 跳跃游戏();

            System.out.println(跳跃游戏.canJump(new int[] { 3, 2, 1, 0, 4 }));
        }

        public boolean canJump(int[] nums) {
            int maxPosition = 0;
            int max = 0;
            int cur = 0;
            while (cur <= maxPosition && cur < nums.length) {
                max = Math.max(nums[cur] + cur, max);
                if (cur == maxPosition) {
                    maxPosition = max;
                    max = 0;
                }
                cur++;
            }
            return cur == nums.length;
        }
    }

    /**
     * 【ali】 45. 跳跃游戏 II
     * https://leetcode-cn.com/problems/jump-game-ii/
     */
    final static class 跳跃游戏II extends Ali {

        public static void main(String[] args) {

            跳跃游戏II 跳跃游戏II = new 跳跃游戏II();

            System.out.println(跳跃游戏II.jump(new int[] { 2, 3, 1, 1, 4 }));
            System.out.println("****************");
            System.out.println(跳跃游戏II.jump2(new int[] { 2, 3, 1, 1, 4 }));
        }

        // 思路是统计当前范围的最大值，基数
        public int jump(int[] nums) {
            if (nums == null || nums.length < 2) {
                return 0;
            }
            int max = 0;
            int maxPosition = 0;
            int step = 0;
            for (int i = 0; i < nums.length; i++) {
                max = Math.max(max, nums[i] + i);
                if (i == nums.length - 1) {
                    break;
                }
                if (i == maxPosition) {
                    maxPosition = max;
                    step++;
                    max = 0;
                }
            }
            return step;
        }

        public int jump2(int[] nums) {
            int position = nums.length - 1;
            int steps = 0;
            while (position > 0) {
                for (int i = 0; i < position; i++) {
                    // 找到第一个大于数组长度的位置，即最后一步跳到数组最后一步的位置
                    if (i + nums[i] >= position) {
                        // 替换最后跳到的位置,
                        position = i;
                        // 步数+1
                        steps++;
                        // 替换完后,跳出继续找能到到此位置的数
                        break;
                    }
                }
            }
            return steps;
        }

        public int jump3(int[] nums) {
            int maxPosition = 0;
            int end = 0;
            int steps = 0;
            for (int i = 0; i < nums.length - 1; i++) {
                // 找最大可跳到的位置
                maxPosition = Math.max(maxPosition, i + nums[i]);

                // 判读是否已经跳到此位置,如果是跳到此位置,更新下次最大可跳到位置.第一次直接进入
                if (i == end) {
                    end = maxPosition;
                    steps++;
                }
            }
            return steps;
        }
    }

    /**
     * 【ali】134.加油站
     * https://leetcode-cn.com/problems/gas-station/
     * 题解：https://leetcode-cn.com/problems/gas-station/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by--30/
     */
    final static class 加油站 extends Ali {

        public static void main(String[] args) {

            加油站 加油站 = new 加油站();

            System.out.println(加油站.canCompleteCircuit(
                new int[] { 1, 2, 3, 4, 5 }, new int[] { 3, 4, 5, 1, 2 }));
            System.out.println(加油站.canCompleteCircuit1(
                new int[] { 1, 2, 3, 4, 5 }, new int[] { 3, 4, 5, 1, 2 }));
        }

        // 思路是从正数出发，然后进行模拟
        public int canCompleteCircuit(int[] gas, int[] cost) {
            int[] diff = new int[gas.length];
            for (int i = 0; i < gas.length; i++) {
                diff[i] = gas[i] - cost[i];
            }
            // diff  [-2, -2, -2, 3, 3]
            for (int i = 0; i < gas.length; i++) {
                if (diff[i] >= 0) {
                    int total = diff[i];
                    boolean flag = false;
                    for (int j = 1; j < gas.length; j++) {
                        total += diff[(j + i) % gas.length];
                        if (total < 0) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        return i;
                    }
                }
            }
            return -1;
        }

        public int canCompleteCircuit1(int[] gas, int[] cost) {
            int n = gas.length;
            //考虑从每一个点出发
            for (int i = 0; i < n; i++) {
                int j = i;
                int remain = gas[i];
                //当前剩余的油能否到达下一个点
                while (remain - cost[j] >= 0) {
                    //减去花费的加上新的点的补给
                    remain = remain - cost[j] + gas[(j + 1) % n];
                    j = (j + 1) % n;
                    //j 回到了 i
                    if (j == i) {
                        return i;
                    }
                }
            }
            //任何点都不可以
            return -1;
        }

        public int canCompleteCircuitBy(int[] gas, int[] cost) {
            int len = gas.length;
            int spare = 0;
            int minSpare = Integer.MAX_VALUE;
            int minIndex = 0;

            for (int i = 0; i < len; i++) {
                spare += gas[i] - cost[i];
                if (spare < minSpare) {
                    minSpare = spare;
                    minIndex = i;
                }
            }
            // spare 剩余汽油大于0，即最开始
            return spare < 0 ? -1 : (minIndex + 1) % len;

        }
    }

    /**
     * 【ali】135. 分发糖果
     * https://leetcode-cn.com/problems/candy/
     */
    final static class 分发糖果 extends Ali {

        public static void main(String[] args) {

            分发糖果 分发糖果 = new 分发糖果();

            System.out.println(分发糖果.candy(new int[] { 1, 0, 2 }));
        }

        /**
         * 规则定义： 设学生 A 和学生 B 左右相邻，A 在 B 左边；
         * 左规则： 当 ratings_B>ratings_A 时，BB 的糖比 AA 的糖数量多。
         * 右规则： 当 ratings_A>ratings_B 时，AA 的糖比 BB 的糖数量多。
         * 最终，取以上 2 轮遍历 left 和 right 对应学生糖果数的 最大值 ，这样则 同时满足左规则和右规则
         * ，即得到每个同学的最少糖果数量。
         */
        public int candy(int[] ratings) {
            int[] left = new int[ratings.length];
            int[] right = new int[ratings.length];
            Arrays.fill(left, 1);
            Arrays.fill(right, 1);

            for (int i = 1; i < ratings.length; i++)
                if (ratings[i] > ratings[i - 1])
                    left[i] = left[i - 1] + 1;

            int count = left[ratings.length - 1];

            for (int i = ratings.length - 2; i >= 0; i--) {
                if (ratings[i] > ratings[i + 1])
                    right[i] = right[i + 1] + 1;

                count += Math.max(left[i], right[i]);
            }
            return count;
        }

    }

    /**
     * 【ali】45. 把数组排成最小的数
     * 
     * <pre>
     * 输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     *
     * 示例 1:
     *
     * 输入: [10,2]
     * 输出: "102"
     * 示例 2:
     *
     * 输入: [3,30,34,5,9]
     * 输出: "3033459"
     * </pre>
     */
    final static class 把数组排成最小的数 extends Ali {

        public static void main(String[] args) {

            把数组排成最小的数 把数组排成最小的数 = new 把数组排成最小的数();

            System.out
                .println(把数组排成最小的数.minNumber(new int[] { 3, 30, 34, 5, 9 }));
        }

        public String minNumber(int[] nums) {
            if (nums.length == 1) {
                return String.valueOf(nums[0]);
            }

            // 把数组转成字符
            String[] numstr = new String[nums.length];
            for (int i = 0; i < nums.length; i++) {
                numstr[i] = String.valueOf(nums[i]);
            }

            // 比较2个字符组合,取小
            Arrays.sort(numstr, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String a = o1 + o2;
                    String b = o2 + o1;
                    return a.compareTo(b);
                }
            });

            // 拼接字符,及最小组合数字
            StringBuilder sb = new StringBuilder();
            for (String s : numstr) {
                sb.append(s);
            }
            return sb.toString();
        }
    }

    /**
     * 【ali】44. 数字序列中某一位的数字
     * 
     * <pre>
     * 数字以0123456789101112131415…的格式序列化到一个字符序列中。在这个序列中，第5位（从下标0开始计数）是5，第13位是1，第19位是4，等等。
     *
     * 请写一个函数，求任意第n位对应的数字。
     *
     * 示例 1：
     *
     * 输入：n = 3
     * 输出：3
     * 示例 2：
     *
     * 输入：n = 11
     * 输出：0
     * </pre>
     */
    final static class 数字序列中某一位的数字 extends Ali {

        public static void main(String[] args) {

            数字序列中某一位的数字 数字序列中某一位的数字 = new 数字序列中某一位的数字();

            System.out.println(数字序列中某一位的数字.findNthDigit(33));
        }

        // 思路是：0 ~ 9  9
        //      10 ~ 99 90
        //     100  999 900
        // 先找到那个数，然后转str,通过余数取到对应位
        public int findNthDigit(int n) {
            // 1 10 100 基数
            int start = 1;
            // 位数
            int digit = 1;
            // 当前的数
            int count = 9;
            // 开始从10 不会出现问题

            while (n > count) {
                n -= count;
                start *= 10;
                digit += 1;
                count = 9 * start * digit;
            }
            // 确定是哪一个数
            long num = start + (n - 1) / digit;
            return Long.toString(num).charAt((n - 1) % digit) - '0';
        }
    }

    /**
     * 【ali】62. 圆圈中最后剩下的数字
     * 
     * <pre>
     * 0,1,,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字。求出这个圆圈里剩下的最后一个数字。
     *
     * 例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。
     *
     * 示例 1：
     *
     * 输入: n = 5, m = 3
     * 输出: 3
     * 示例 2：
     *
     * 输入: n = 10, m = 17
     * 输出: 2
     * </pre>
     */
    final static class 圆圈中最后剩下的数字 extends Ali {

        public static void main(String[] args) {

            圆圈中最后剩下的数字 圆圈中最后剩下的数字 = new 圆圈中最后剩下的数字();

            System.out.println(圆圈中最后剩下的数字.lastRemaining(5, 3));

        }

        public int lastRemaining(int n, int m) {
            if (n <= 1) {
                return 0;
            }
            // 填充
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                list.add(i);
            }

            // 记录index
            int index = 0;
            // list里面大于1说明还没移除到最后一位
            while (list.size() > 1) {

                // 每次移除后都是从上次移除的下标开始,所以要加上上次记录下标
                index = (index + (m - 1)) % list.size();

                System.out.println(list);
                list.remove(index);
            }
            return list.get(0);
        }

    }

    /**
     * 【ali】279. 完全平方数
     * https://leetcode-cn.com/problems/perfect-squares/
     */
    final static class 完全平方数 extends Ali {

        public static void main(String[] args) {

            完全平方数 完全平方数 = new 完全平方数();

            System.out.println(完全平方数.numSquares(5));

        }

        public int numSquares(int n) {
            int dp[] = new int[n + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);
            // bottom case
            dp[0] = 0;

            // pre-calculate the square numbers.
            int max_square_index = (int) Math.sqrt(n) + 1;
            int square_nums[] = new int[max_square_index];
            for (int i = 1; i < max_square_index; ++i) {
                square_nums[i] = i * i;
            }

            for (int i = 1; i <= n; ++i) {
                for (int s = 1; s < max_square_index; ++s) {
                    if (i < square_nums[s])
                        break;
                    dp[i] = Math.min(dp[i], dp[i - square_nums[s]] + 1);
                }
            }
            return dp[n];
        }

        public int numSquares2(int n) {
            int[] dp = new int[n + 1]; // 默认初始化值都为0
            for (int i = 1; i <= n; i++) {
                dp[i] = i; // 最坏的情况就是每次+1
                for (int j = 1; i - j * j >= 0; j++) {
                    dp[i] = Math.min(dp[i], dp[i - j * j] + 1); // 动态转移方程
                }
            }
            return dp[n];
        }
    }
}
