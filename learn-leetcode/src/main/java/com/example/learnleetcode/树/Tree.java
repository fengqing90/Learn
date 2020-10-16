package com.example.learnleetcode.树;

import java.util.LinkedList;
import java.util.List;

/**
 * 树
 *
 * @author fengqing
 * @date 2020/10/16 11:02
 */
public class Tree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            this.val = x;
        }

        @Override
        public String toString() {
            return "TreeNode{" + "val=" + val + ", left=" + left + ", right="
                + right + '}';
        }
    }

    public static void main(String[] args) {
        System.out
            .println(二叉树的锯齿形层次遍历.zigzagLevelOrder(二叉树的锯齿形层次遍历.getTreeNode()));
        System.out.println(二叉树的锯齿形层次遍历.zigzagLevelOrder(null));
        System.out
            .println(二叉树的锯齿形层次遍历.zigzagLevelOrder(二叉树的锯齿形层次遍历.getTreeNode2()));
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
