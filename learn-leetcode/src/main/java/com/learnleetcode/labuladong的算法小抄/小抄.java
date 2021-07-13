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

    /**
     * 322. 零钱兑换
     * https://leetcode-cn.com/problems/coin-change/
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
