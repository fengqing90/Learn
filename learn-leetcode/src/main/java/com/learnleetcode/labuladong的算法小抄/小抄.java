package com.learnleetcode.labuladong的算法小抄;

import com.learnleetcode.LeetCode;

/**
 * https://labuladong.gitee.io/algo/
 *
 * @author fengqing
 * @date 2021/7/12 14:25
 */
public class 小抄 extends LeetCode {
    public static class TreeNode {
        int val;
        小抄.TreeNode left;
        小抄.TreeNode right;

        TreeNode(int x) {
            this.val = x;
        }

        TreeNode(int val, 小抄.TreeNode left, 小抄.TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            if (this.left == null && this.right == null)
                return this.val + "";

            return this.val + "( " + (this.left == null ? "" : this.left)
                + " | " + (this.right == null ? "" : this.right) + " )";

            // return "TreeNode{" + "val=" + this.val + ", left=" + this.left
            //     + ", right=" + this.right + '}';
        }
    }

    public static void main(String[] args) {
        System.out.println(Math.sqrt(6));
        System.out.println((int) Math.sqrt(6));
        System.out.println((int) Math.sqrt(7));
        System.out.println((int) Math.sqrt(8));
        System.out.println((int) Math.sqrt(14));
    }

    /**
     * 877. 石子游戏
     * https://leetcode-cn.com/problems/stone-game/
     * 如果我们把这四堆石头按索引的奇偶分为两组，即第 1、3 堆和第 2、4
     * 堆，那么这两组石头的数量一定不同，也就是说一堆多一堆少。因为石头的总数是奇数，不能被平分。
     * 而作为第一个拿石头的人，你可以控制自己拿到所有偶数堆，或者所有的奇数堆。
     * 你最开始可以选择第 1 堆或第 4 堆。如果你想要偶数堆，你就拿第 4 堆，这样留给对手的选择只有第 1、3 堆，他不管怎么拿，第 2
     * 堆又会暴露出来，你就可以拿。同理，如果你想拿奇数堆，你就拿第 1 堆，留给对手的只有第 2、4 堆，他不管怎么拿，第 3 堆又给你暴露出来了。
     * 也就是说，你可以在第一步就观察好，奇数堆的石头总数多，还是偶数堆的石头总数多，然后步步为营，就一切尽在掌控之中了。
     */
    final static class 石子游戏 extends 小抄 {

        public static void main(String[] args) {

            石子游戏 石子游戏 = new 石子游戏();

            System.out.println(石子游戏.stoneGame(null));
        }

        public boolean stoneGame(int[] piles) {
            return true;
        }
    }

    /**
     * 292. Nim 游戏
     * https://leetcode-cn.com/problems/nim-game/
     */
    final static class Nim游戏 extends 小抄 {

        public static void main(String[] args) {

            Nim游戏 Nim游戏 = new Nim游戏();

            System.out.println(Nim游戏.canWinNim(5));
            System.out.println(Nim游戏.canWinNim2(5));

            System.out.println(Nim游戏.canWinNim(17));
            System.out.println(Nim游戏.canWinNim2(17));

            System.out.println(Nim游戏.canWinNim(1348820612));
            System.out.println(Nim游戏.canWinNim2(1348820612));
        }

        public boolean canWinNim(int n) {
            return n % 4 != 0;
        }

        public boolean canWinNim2(int n) {
            if (n < 4) {
                return true;
            }
            if (n == 4) {
                return false;
            }
            boolean[] dp = new boolean[n];
            dp[0] = true;
            dp[1] = true;
            dp[2] = true;
            for (int i = 3; i < n; i++) {
                dp[i] = !(dp[i - 1] && dp[i - 2] && dp[i - 3]);
            }

            return dp[n - 1];
        }
    }

    /**
     * 322. 零钱兑换
     * https://leetcode-cn.com/problems/coin-change/
     * 题解:https://leetcode-cn.com/problems/coin-change/solution/javadi-gui-ji-yi-hua-sou-suo-dong-tai-gui-hua-by-s/
     */
    final static class 零钱兑换 extends 小抄 {

        public static void main(String[] args) {
            零钱兑换 零钱兑换 = new 零钱兑换();

            System.out.println(零钱兑换.coinChange3(new int[] { 2 }, 3));
            System.out.println(
                零钱兑换.coinChange3(new int[] { 186, 419, 83, 408 }, 6249));
            System.out.println(零钱兑换.coinChange3(new int[] { 1 }, 11));
            System.out.println(零钱兑换.coinChange3(new int[] { 1, 2, 5 }, 100));
            System.out.println(零钱兑换.coinChange(new int[] { 1, 2, 5 }, 11));
            //****************************************************************
            // System.out.println(
            //     零钱兑换.coinChange2(new int[] { 186, 419, 83, 408 }, 6249));
            // System.out.println(零钱兑换.coinChange2(new int[] { 2 }, 3));
            // System.out.println(零钱兑换.coinChange2(new int[] { 1 }, 11));
            // System.out.println(零钱兑换.coinChange2(new int[] { 1, 2, 5 }, 100));
            // //****************************************************************
            // System.out.println(零钱兑换.coinChange(new int[] { 2 }, 3));
            // System.out.println(零钱兑换.coinChange(new int[] { 1 }, 11));
            // System.out.println(零钱兑换.coinChange(new int[] { 1, 2, 5 }, 11));
        }

        /**
         * 动态规划
         * 
         * @param coins
         * @param amount
         * @return
         */
        public int coinChange3(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            for (int i = 1; i < dp.length; i++) {
                int min = Integer.MAX_VALUE;
                for (int j = 0; j < coins.length; j++) {

                    // 无法换，跳过
                    if (i - coins[j] < 0)
                        continue;

                    // 找到计算过的次数,如果小于min +1
                    if (dp[i - coins[j]] < min)
                        min = dp[i - coins[j]] + 1;
                }
                // 记录换的次数
                dp[i] = min;
            }
            return (dp[amount] == Integer.MAX_VALUE) ? -1 : dp[amount];
        }

        /**
         * 递归,dfs,备忘录解法
         * 
         * @param coins
         * @param amount
         * @return
         */
        public int coinChange2(int[] coins, int amount) {
            return change2(coins, amount, new int[amount]);
        }

        private int change2(int[] coins, int amount, int[] dp) {
            if (amount < 0)
                return -1;
            if (amount == 0) {
                return 0;
            }
            // db是否已经计算过
            if (dp[amount - 1] != 0) {
                return dp[amount - 1];
            }

            int min = Integer.MAX_VALUE;

            for (int c : coins) {
                int sub = change2(coins, amount - c, dp);

                if (sub == -1)
                    continue;

                min = Math.min(sub + 1, min);

                // if (sub >= 0 && sub < min)
                //     min = sub + 1;
            }

            dp[amount - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
            return dp[amount - 1];
        }

        /***
         * 暴力解法
         * 
         * @param coins
         * @param amount
         *        状态
         */
        public int coinChange(int[] coins, int amount) {
            if (amount == 0)
                return 0;
            if (amount < 0)
                return -1;

            int min = Integer.MAX_VALUE;

            for (int c : coins) {

                int sub = coinChange(coins, amount - c);

                if (sub == -1)
                    continue;

                min = Math.min(min, sub + 1);
            }

            return min == Integer.MAX_VALUE ? -1 : min;
        }
    }

    /**
     * 124. 二叉树中的最大路径和
     * https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/
     * https://labuladong.gitee.io/algo/1/2/
     */
    final static class 二叉树中的最大路径和 extends 小抄 {

        public static void main(String[] args) {

            二叉树中的最大路径和 二叉树中的最大路径和 = new 二叉树中的最大路径和();

            System.out.println(二叉树中的最大路径和.maxPathSum(
                new TreeNode(2, new TreeNode(-1), new TreeNode(-2))));
            二叉树中的最大路径和.maxSum = Integer.MIN_VALUE;

            System.out.println(
                二叉树中的最大路径和.maxPathSum(new TreeNode(2, new TreeNode(-1), null)));
            二叉树中的最大路径和.maxSum = Integer.MIN_VALUE;

            System.out.println(二叉树中的最大路径和
                .maxPathSum(new TreeNode(1, new TreeNode(2), new TreeNode(3))));
            二叉树中的最大路径和.maxSum = Integer.MIN_VALUE;

            System.out.println(
                二叉树中的最大路径和.maxPathSum(new TreeNode(-10, new TreeNode(9),
                    new TreeNode(20, new TreeNode(15), new TreeNode(7)))));
            二叉树中的最大路径和.maxSum = Integer.MIN_VALUE;

            System.out.println(二叉树中的最大路径和.maxPathSum(new TreeNode(-3)));
            // System.out.println(二叉树中的最大路径和.maxPathSum(new TreeNode(-3)));
        }

        int maxSum = Integer.MIN_VALUE;

        public int maxPathSum2(TreeNode root) {
            if (root == null)
                return 0;

            // 左右最大值，小于0 = 无用路径，直接等于0
            int left = Math.max(maxPathSum2(root.left), 0);
            int right = Math.max(maxPathSum2(root.right), 0);

            // 左中右 路径最大值比较
            int tmp = left + right + root.val;
            maxSum = Math.max(maxSum, tmp);

            //返回最大值与当前行程路径
            return root.val + Math.max(left, right);
        }

        public int maxPathSum(TreeNode root) {
            // maxGain(root);
            maxPathSum2(root);
            return maxSum;
        }

        public int maxGain(TreeNode node) {
            if (node == null) {
                return 0;
            }

            // 递归计算左右子节点的最大贡献值
            // 只有在最大贡献值大于 0 时，才会选取对应子节点
            int leftGain = Math.max(maxGain(node.left), 0);
            int rightGain = Math.max(maxGain(node.right), 0);

            // 节点的最大路径和取决于该节点的值与该节点的左右子节点的最大贡献值
            int priceNewpath = node.val + leftGain + rightGain;

            // 更新答案
            maxSum = Math.max(maxSum, priceNewpath);

            // 返回节点的最大贡献值
            return node.val + Math.max(leftGain, rightGain);
        }

    }
}
