package com.learnleetcode.树;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 树
 *
 * @author fengqing
 * @date 2020/10/16 11:02
 */
public class 树 {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            this.val = x;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" + "val=" + this.val + ", left=" + this.left
                + ", right=" + this.right + '}';
        }
    }

    public static void main(String[] args) {
        // System.out
        //     .println(二叉树的锯齿形层次遍历.zigzagLevelOrder(二叉树的锯齿形层次遍历.getTreeNode()));
        // System.out.println(二叉树的锯齿形层次遍历.zigzagLevelOrder(null));
        // System.out
        //     .println(二叉树的锯齿形层次遍历.zigzagLevelOrder(二叉树的锯齿形层次遍历.getTreeNode2()));

        System.out.println(平衡二叉树.isBalanced(平衡二叉树.getTreeNode()));
    }

    /**
     * 110. 平衡二叉树
     */
    static class 平衡二叉树 {

        public static TreeNode getTreeNode() {
            return new TreeNode(3, new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
            // return new TreeNode(3, new TreeNode(9), new TreeNode(20,
            //     new TreeNode(15), new TreeNode(7, new TreeNode(3), null)));
        }

        public static boolean isBalanced(TreeNode root) {
            return balanced(root) != -1;
        }

        private static int balanced(TreeNode node) {
            if (node == null) {
                return 0;
            }
            int leftHeight, rightHeight;
            // 左边高度
            // 右边高度
            // 左右高度差是否大于1
            if ((leftHeight = balanced(node.left)) == -1
                || (rightHeight = balanced(node.right)) == -1
                || Math.abs(leftHeight - rightHeight) > 1) {
                return -1;
            }
            // 高度
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    /**
     * 108. 将有序数组转换为二叉搜索树
     */
    static class 将有序数组转换为二叉搜索树 {
        public TreeNode sortedArrayToBST(int[] nums) {
            return this.dfs(nums, 0, nums.length - 1);
        }

        private TreeNode dfs(int[] nums, int left, int right) {
            if (left > right) {
                return null;
            }
            // 以升序数组的中间元素作为根节点 root。
            int mid = left + (right - left) / 2;
            // 创建tree
            TreeNode root = new TreeNode(nums[mid]);

            // 递归的构建 root 的左子树与右子树。
            root.left = this.dfs(nums, left, mid - 1);
            root.right = this.dfs(nums, mid + 1, right);
            return root;
        }

    }

    /***
     * 对称二叉树
     */
    static class 对称二叉树 {

        public boolean isSymmetric(TreeNode root) {
            return this.check(root, root);
        }

        public boolean check(TreeNode p, TreeNode q) {
            if (p == null && q == null) {
                return true;
            } else if (p == null || q == null) {
                return false;
            } else {
                // 左和右值必须相等，递归
                return (p.val == q.val) && this.check(p.left, q.right)
                    && this.check(p.right, q.left);
            }
        }

    }

    /**
     * 相同的树
     */
    static class 相同的树 {

        public boolean isSameTree(TreeNode p, TreeNode q) {
            if (p == null && q == null) {
                return true;
            } else if (p == null || q == null) {
                return false;
            } else if (q.val == p.val) {
                return true;
            } else {
                // 两个数的左节点是否相等
                // 两个数的右节点是否相等
                return this.isSameTree(p.left, q.left)
                    && this.isSameTree(p.right, q.right);
            }
        }

    }

    /**
     * 107. 二叉树的层次遍历 II
     */
    static class 二叉树的层次遍历II {

        private static TreeNode getTreeNode() {
            TreeNode root = new TreeNode(3);
            TreeNode l2 = new TreeNode(9);
            TreeNode r2 = new TreeNode(20);
            TreeNode l3 = new TreeNode(15);
            TreeNode r3 = new TreeNode(7);
            r2.left = l3;
            r2.right = r3;
            root.left = l2;
            root.right = r2;
            return root;
        }

        public static List<List<Integer>> levelOrderBottom(TreeNode root) {
            List<List<Integer>> levelOrder = new LinkedList<>();
            if (root == null) {
                return levelOrder;
            }
            // 转成queue,把每层数据都加入到queue中,利用queue处理
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                List<Integer> level = new ArrayList<>();

                // 当前层大小
                int size = queue.size();

                // 遍历 左右
                for (int i = 0; i < size; i++) {
                    // 获取第一个& 删除
                    TreeNode node = queue.poll();
                    level.add(node.val);

                    TreeNode left = node.left, right = node.right;
                    // 左右不等于null 加入队列中
                    if (left != null) {
                        queue.offer(left);
                    }
                    if (right != null) {
                        queue.offer(right);
                    }
                }
                // 倒序,最高层先入
                levelOrder.add(0, level);
            }
            return levelOrder;
        }
    }

    /**
     * 104 二叉树的最大深度
     */
    static class 二叉树的最大深度 {

        // max(l,r)+1
        public int maxDepth(TreeNode root) {
            // 等于null 说明到底
            if (root == null) {
                return 0;
            }

            // 获取 左 深度
            int lc = this.maxDepth(root.left);
            // 获取 右 深度
            int rc = this.maxDepth(root.right);

            // 深度 = max(左,右)+1
            return Math.max(lc, rc) + 1;
        }

        public class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;

            TreeNode(int x) {
                this.val = x;
            }
        }
    }

    /**
     * 103. 二叉树的锯齿形层次遍历（蛇形）
     */
    static class 二叉树的锯齿形层次遍历 {

        private static TreeNode getTreeNode2() {
            TreeNode root = new TreeNode(1);
            TreeNode l = new TreeNode(2);
            TreeNode r = new TreeNode(3);
            TreeNode ll = new TreeNode(4);
            TreeNode rr = new TreeNode(5);
            l.left = ll;
            r.right = rr;
            root.left = l;
            root.right = r;
            System.out.println("********");
            System.out.println(root.val);
            System.out.println(root.left.val + " | " + root.right.val);
            System.out
                .println(root.left.left.val + " | " + root.right.right.val);
            System.out.println("********");
            return root;
        }

        private static TreeNode getTreeNode() {
            TreeNode root = new TreeNode(3);
            TreeNode l2 = new TreeNode(9);
            TreeNode r2 = new TreeNode(20);
            TreeNode l3 = new TreeNode(15);
            TreeNode r3 = new TreeNode(7);
            r2.left = l3;
            r2.right = r3;
            root.left = l2;
            root.right = r2;
            return root;
        }

        public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {

            List<List<Integer>> result = new LinkedList<>();

            LinkedList<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            int h = 0;
            while (!queue.isEmpty()) {

                LinkedList<Integer> list = new LinkedList<>();

                int size = queue.size();

                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();

                    if (node != null) {
                        if ((h & 1) == 0) {
                            list.add(node.val);
                        } else {
                            list.push(node.val);
                        }

                        if (node.left != null) {
                            queue.offer(node.left);
                        }
                        if (node.right != null) {
                            queue.offer(node.right);
                        }
                    }
                }
                h++;
                if (list.size() > 0) {
                    result.add(list);
                }
            }
            return result;
        }
    }
}
