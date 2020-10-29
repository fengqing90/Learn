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

            return this.val + " (" + (this.left == null ? "" : this.left)
                + " | " + (this.right == null ? "" : this.right) + ")";
            // return "TreeNode{" + "val=" + this.val + ", left=" + this.left
            //     + ", right=" + this.right + '}';
        }
    }

    public static void main(String[] args) {
        // System.out
        //     .println(二叉树的锯齿形层次遍历.zigzagLevelOrder(二叉树的锯齿形层次遍历.getTreeNode()));
        // System.out.println(二叉树的锯齿形层次遍历.zigzagLevelOrder(null));
        // System.out
        //     .println(二叉树的锯齿形层次遍历.zigzagLevelOrder(二叉树的锯齿形层次遍历.getTreeNode2()));

        // System.out.println(平衡二叉树.isBalanced(平衡二叉树.getTreeNode()));

        // System.out.println(二叉树的最小深度.minDepth(二叉树的最小深度.getTreeNode()));

        // System.out.println(路径总和.hasPathSum(路径总和.getTreeNode(), 22));
        // System.out.println(路径总和.hasPathSum2(路径总和.getTreeNode(), 26));

        // System.out.println(翻转二叉树.invertTree(翻转二叉树.getTreeNode()));

        // System.out.println(二叉树的最近公共祖先.lowestCommonAncestor(
        //     二叉树的最近公共祖先.getTreeNode(), new TreeNode(2), new TreeNode(8)));
        //
        // System.out.println(二叉树的最近公共祖先.lowestCommonAncestor(
        //     二叉树的最近公共祖先.getTreeNode(), new TreeNode(2), new TreeNode(4)));

        // System.out.println(二叉树的所有路径.binaryTreePaths(二叉树的所有路径.getTreeNode()));
        // System.out.println(二叉树的所有路径.binaryTreePaths2(二叉树的所有路径.getTreeNode()));

        // System.out.println(左叶子之和.sumOfLeftLeaves4BFS(左叶子之和.getTreeNode())); // 24
        // System.out.println(左叶子之和.sumOfLeftLeaves4BFS(左叶子之和.getTreeNode2()));// 0
        // System.out.println(左叶子之和.sumOfLeftLeaves(左叶子之和.getTreeNode()));
        // System.out.println(左叶子之和.sumOfLeftLeaves(左叶子之和.getTreeNode2()));
        // System.out.println(左叶子之和.sumOfLeftLeaves(左叶子之和.getTreeNode3()));

        System.out.println(
            二叉树中第二小的节点.findSecondMinimumValue(二叉树中第二小的节点.getTreeNode()));
    }

    /** 671. 二叉树中第二小的节点 **/
    static class 二叉树中第二小的节点 {
        public static TreeNode getTreeNode() {
            return new TreeNode(2, new TreeNode(2),
                new TreeNode(5, new TreeNode(5), new TreeNode(7)));
        }

        public static int findSecondMinimumValue(TreeNode root) {

            if (root == null) {
                return -1;
            }

            return help(root, root.val);
        }

        private static int help(TreeNode root, int min) {
            if (root == null) {
                return -1;
            }

            // 是否大于最小节点
            if (root.val > min) {
                return root.val;
            }

            // 左节点 第二小
            int left = help(root.left, min);
            // 右节点 第二小
            int right = help(root.right, min);

            if (left == -1) {
                return right;
            }
            if (right == -1) {
                return left;
            }
            return Math.min(left, right);
        }
    }

    /** 404. 左叶子之和 **/
    static class 左叶子之和 {

        public static TreeNode getTreeNode() {
            return new TreeNode(3, new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        }

        public static TreeNode getTreeNode2() {
            return new TreeNode(1);
        }

        public static TreeNode getTreeNode3() {
            return new TreeNode(1, new TreeNode(5), null);
        }

        /** DFS **/
        public static int sumOfLeftLeaves(TreeNode root) {

            if (root == null) {
                return 0;
            }

            int lsum = 0;
            int rsum = 0;
            if (root.left != null) {
                if (isLeafNode(root.left)) {
                    lsum = root.left.val;
                } else {
                    lsum = sumOfLeftLeaves(root.left);
                }
            }

            if (root.right != null && !isLeafNode(root.right)) {
                rsum = sumOfLeftLeaves(root.right);
            }

            return lsum + rsum;
        }

        /** BFS **/
        public static int sumOfLeftLeaves4BFS(TreeNode root) {

            if (root == null) {
                return 0;
            }

            Queue<TreeNode> nodeQ = new LinkedList<>();
            nodeQ.offer(root);

            int sum = 0;
            while (!nodeQ.isEmpty()) {

                TreeNode node = nodeQ.poll();

                // 左节点不为空
                if (node.left != null) {
                    // 左节点是否叶子节点,是加值,不是继续看左节点下面节点
                    if (isLeafNode(node.left)) {
                        sum += node.left.val;
                    } else {
                        nodeQ.offer(node.left);
                    }
                }

                // 右节点不为空, 右节点不是叶子节点继续看右节点下是否有左节点
                if (node.right != null && !isLeafNode(node.right)) {
                    nodeQ.offer(node.right);
                }

            }

            return sum;
        }

        public static boolean isLeafNode(TreeNode node) {
            return node.left == null && node.right == null;
        }

    }

    /** 257. 二叉树的所有路径 **/
    static class 二叉树的所有路径 {
        public static TreeNode getTreeNode() {
            return new TreeNode(1, new TreeNode(2, null, new TreeNode(5)),
                new TreeNode(3));
        }

        public static List<String> binaryTreePaths(TreeNode root) {
            List<String> paths = new ArrayList<String>();
            constructPaths(root, "", paths);
            return paths;
        }

        public static void constructPaths(TreeNode root, String path,
                List<String> paths) {
            if (root != null) {
                StringBuffer pathSB = new StringBuffer(path);
                pathSB.append(root.val);
                if (root.left == null && root.right == null) {  // 当前节点是叶子节点
                    paths.add(pathSB.toString());  // 把路径加入到答案中
                } else {
                    pathSB.append("->");  // 当前节点不是叶子节点，继续递归遍历
                    constructPaths(root.left, pathSB.toString(), paths);
                    constructPaths(root.right, pathSB.toString(), paths);
                }
            }
        }

        public static List<String> binaryTreePaths2(TreeNode root) {
            List<String> paths = new ArrayList<>();

            if (root == null) {
                return paths;
            }

            Queue<TreeNode> nodeQueue = new LinkedList<>();
            Queue<String> pathQueue = new LinkedList<>();

            nodeQueue.offer(root);
            pathQueue.offer(Integer.toString(root.val));

            while (!nodeQueue.isEmpty()) {
                TreeNode node = nodeQueue.poll();
                String path = pathQueue.poll();

                if (node.left == null && node.right == null) {
                    paths.add(0, path);  // 叶子节点,加入path
                } else {

                    if (node.left != null) {
                        nodeQueue.offer(node.left);
                        pathQueue.offer(path + "->" + node.left.val);
                    }

                    if (node.right != null) {
                        nodeQueue.offer(node.right);
                        pathQueue.offer(path + "->" + node.right.val);
                    }
                }
            }
            return paths;
        }

    }

    /** 236. 二叉树的最近公共祖先 **/
    static class 二叉树的最近公共祖先 {

        public static TreeNode getTreeNode() {
            return new TreeNode(6,
                new TreeNode(2, new TreeNode(0),
                    new TreeNode(4, new TreeNode(3), new TreeNode(5))),
                new TreeNode(8, new TreeNode(7), new TreeNode(9)));
        }

        public static TreeNode getTreeNode2() {
            return new TreeNode(3,
                new TreeNode(5, new TreeNode(6),
                    new TreeNode(2, new TreeNode(7), new TreeNode(4))),
                new TreeNode(1, new TreeNode(0), new TreeNode(8)));
        }

        public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode l,
                TreeNode r) {

            if (root == null) {
                return null;
            }
            if (root.val == l.val || root.val == r.val) {
                return root;
            }
            // 应为是二叉搜索树,左边的节点都比跟节点小,右边的节点都比根节点大

            // 当前节点 > 左节点 > 右节点  ： 当前节点往右偏移
            // 当前节点 < 左节点 < 右节点  ： 当前节点往左偏移
            // 当前节点 > 左节点 < 右节点  ： 即公共节点
            if (root.val < l.val && root.val < r.val) {
                return lowestCommonAncestor(root.right, l, r);
            } else if (root.val > l.val && root.val > r.val) {
                return lowestCommonAncestor(root.left, l, r);
            } else if (root.val > l.val && root.val < r.val) {
                return root;
            }

            return root;
        }
    }

    /** 226. 翻转二叉树 **/
    static class 翻转二叉树 {

        public static TreeNode getTreeNode() {
            return new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7, new TreeNode(6), new TreeNode(9)));
        }

        public static TreeNode invertTree(TreeNode root) {

            if (root == null) {
                return null;
            }

            TreeNode left = invertTree(root.left);
            TreeNode right = invertTree(root.right);

            root.left = right;
            root.right = left;
            return root;
        }
    }

    /** 112. 路径总和 **/
    static class 路径总和 {
        public static TreeNode getTreeNode() {
            return new TreeNode(5,
                new TreeNode(4,
                    new TreeNode(11, new TreeNode(7), new TreeNode(2)), null),
                new TreeNode(8, new TreeNode(13),
                    new TreeNode(4, null, new TreeNode(1))));
        }

        /** DFS 深度优先 **/
        public static boolean hasPathSum(TreeNode root, int sum) {
            if (root == null) {
                return false;
            }

            // 到达叶子节点时，递归终止，判断 减后的 sum 是否符合条件。
            if (root.left == null && root.right == null) {
                return root.val == sum;
            }

            // 有叶子节点 会将sum减去该节点
            // 递归地判断root节点的左孩子和右孩子。
            return hasPathSum(root.left, sum - root.val)
                || hasPathSum(root.right, sum - root.val);
        }

        /** BFS 广度优先 **/
        public static boolean hasPathSum2(TreeNode root, int sum) {
            if (root == null) {
                return false;
            }
            Queue<TreeNode> queNode = new LinkedList<>();
            Queue<Integer> queVal = new LinkedList<>();

            // 节点队列
            queNode.offer(root);

            // 值队列
            queVal.offer(root.val);

            while (!queNode.isEmpty()) {
                TreeNode now = queNode.poll();

                int temp = queVal.poll();

                // 如果 左右 没有子节点,直接比较值
                if (now.left == null && now.right == null) {
                    if (temp == sum) {
                        return true;
                    }
                    // 如果 不相等,跳出
                    continue;
                }

                if (now.left != null) {
                    queNode.offer(now.left);
                    queVal.offer(now.left.val + temp);
                }

                if (now.right != null) {
                    queNode.offer(now.right);
                    queVal.offer(now.right.val + temp);
                }
            }
            return false;
        }
    }

    /**
     * 111. 二叉树的最小深度
     */
    static class 二叉树的最小深度 {
        public static TreeNode getTreeNode() {
            return new TreeNode(3, new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        }

        /** 深度优先搜索 DFS **/
        public static int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            //这道题递归条件里分为三种情况
            //1.左孩子和有孩子都为空的情况，说明到达了叶子节点，直接返回1即可
            if (root.left == null && root.right == null) {
                return 1;
            }
            //2.如果左孩子和由孩子其中一个为空，那么需要返回比较大的那个孩子的深度        
            int m1 = minDepth(root.left);
            int m2 = minDepth(root.right);

            //这里其中一个节点为空，说明m1和m2有一个必然为0，所以可以返回m1 + m2 + 1;
            if (root.left == null || root.right == null) {
                return m1 + m2 + 1;
            }

            //3.最后一种情况，也就是左右孩子都不为空，返回最小深度+1即可
            return Math.min(m1, m2) + 1;
        }

        /** 广度优先搜索 BFS **/
        public static int minDepth2(TreeNode root) {
            return 0;
        }

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
