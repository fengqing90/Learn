package com.learnleetcode.链表;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.learnleetcode.LeetCode;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/13 15:10
 */
public class 链表 extends LeetCode {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return this.val + (this.next == null ? "" : "->" + this.next);
        }
    }

    public static void main(String[] args) {
        // Stream.iterate(0, i -> i + 1).limit(10).forEach(i -> {
        //     System.out.println(i + " = " + (i & 1));
        // });

        // System.out.println(奇偶链表.oddEvenList(奇偶链表.getListNode()));

        // System.out.println(
        //     两数相加.addTwoNumbers(两数相加.getListNode1(), 两数相加.getListNode2()));
        // System.out.println(
        //     两数相加.addTwoNumbers(两数相加.getListNode3(), 两数相加.getListNode4()));

        // System.out.println(
        //     删除链表的倒数第N个节点.removeNthFromEnd(删除链表的倒数第N个节点.getListNode(), 2));

        // System.out
        //     .println(删除排序链表中的重复元素.deleteDuplicates(删除排序链表中的重复元素.getListNode()));
        // System.out.println(
        //     删除排序链表中的重复元素.deleteDuplicates(删除排序链表中的重复元素.getListNode2()));

        // 环形链表.run();

        相交链表.run();
        合并两个有序链表.run();
        移除链表元素.run();

    }

    /**
     * 242. 有效的字母异位词
     * https://leetcode-cn.com/problems/valid-anagram/
     */
    final static class 有效的字母异位词 extends 链表 {

        public static void main(String[] args) {

            有效的字母异位词 有效的字母异位词 = new 有效的字母异位词();
            System.out.println(有效的字母异位词.isAnagram("anagram", "nagaram"));
        }

        public boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) {
                return false;
            }
            Map<Character, Integer> table = new HashMap<Character, Integer>();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                table.put(ch, table.getOrDefault(ch, 0) + 1);
            }
            for (int i = 0; i < t.length(); i++) {
                char ch = t.charAt(i);
                table.put(ch, table.getOrDefault(ch, 0) - 1);
                if (table.get(ch) < 0) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 237. 删除链表中的节点
     * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
     */
    final static class 删除链表中的节点 extends 链表 {

        public static void main(String[] args) {

            删除链表中的节点 删除链表中的节点 = new 删除链表中的节点();
            ListNode node = new ListNode(1, new ListNode(2,
                new ListNode(3, new ListNode(4, new ListNode(5)))));
            删除链表中的节点.deleteNode(node.next.next);
            System.out.println(node);
        }

        public void deleteNode(ListNode node) {
            node.val = node.next.val;
            node.next = node.next.next;
        }
    }

    /**
     * 206. 反转链表
     * https://leetcode-cn.com/problems/reverse-linked-list/
     **/
    final static class 反转链表 extends 链表 {

        public static void main(String[] args) {

            反转链表 反转链表 = new 反转链表();

            System.out.println(反转链表.reverseList(new ListNode(1, new ListNode(2,
                new ListNode(3, new ListNode(4, new ListNode(5)))))));
        }

        /** 将值调换位置 **/
        public ListNode reverseList(ListNode head) {
            ListNode ans = null;
            for (ListNode x = head; x != null; x = x.next) {
                ans = new ListNode(x.val, ans);
            }
            return ans;
        }
    }

    /**
     * 203. 移除链表元素
     */
    final static class 移除链表元素 extends 链表 {

        static void run() {

            移除链表元素 移除链表元素 = new 移除链表元素();

            ListNode node = new ListNode(1,
                new ListNode(2, new ListNode(6, new ListNode(3,
                    new ListNode(4, new ListNode(5, new ListNode(6)))))));

            System.out.println(移除链表元素.removeElements(node, 6));
        }

        public ListNode removeElements(ListNode head, int val) {

            ListNode tmp = new ListNode(-1);
            tmp.next = head;
            ListNode pre = tmp;

            // pre.next = 当期node
            while (pre.next != null) {
                if (pre.next.val == val) {
                    pre.next = pre.next.next;
                } else {
                    pre = pre.next;
                }
            }

            return tmp.next;
        }

        // 递归方式
        public ListNode removeElements2(ListNode head, int val) {

            if (head == null)
                return null;

            head.next = removeElements2(head.next, val);

            if (head.val == val)
                return head.next;
            else
                return head;

        }
    }

    /**
     * 160. 相交链表 </br>
     * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
     **/
    static class 相交链表 extends 链表 {

        static void run() {
            相交链表 相交链表 = new 相交链表();
            ListNode node8 = new ListNode(8, new ListNode(4, new ListNode(5)));

            // [4,1,8,4,5]
            ListNode listNodeA = new ListNode(4, new ListNode(1, node8));

            // [5,6,1,8,4,5]
            ListNode listNodeB = new ListNode(5,
                new ListNode(6, new ListNode(1, node8)));

            System.out.println(相交链表.getIntersectionNode(listNodeA, listNodeB));
        }

        /**
         * 方法三：双指针法
         * 
         * <pre>
         * 创建两个指针 pApA 和 pBpB，分别初始化为链表 A 和 B 的头结点。然后让它们向后逐结点遍历。 
         * 当 pApA 到达链表的尾部时，将它重定位到链表 B 的头结点 (你没看错，就是链表 B); 类似的，当 pBpB
         * 到达链表的尾部时，将它重定位到链表 A 的头结点。
         * 若在某一时刻 pApA 和 pBpB 相遇，则 pApA/pBpB 为相交结点。
         * 想弄清楚为什么这样可行, 可以考虑以下两个链表: A={1,3,5,7,9,11} 和 B={2,4,9,11}，相交于结点 9。 由于
         * B.length (=4) < A.length (=6)，pBpB 比 pApA 少经过 22 个结点，会先到达尾部。将 pBpB
         * 重定向到 A 的头结点，pApA 重定向到 B 的头结点后，pBpB 要比 pApA 多走 2 个结点。因此，它们会同时到达交点。
         * 如果两个链表存在相交，它们末尾的结点必然相同。因此当 pApA/pBpB 到达链表结尾时，记录下链表 A/B
         * 对应的元素。若最后元素不相同，则两个链表不相交。
         * 时间复杂度 : O(m+n)O(m+n)。
         * 空间复杂度 : O(1)O(1)。
         * 
         * A:[4,1,8,4,5][5,6,1,8,4,5]
         * B:[5,6,1,8,4,5][4,1,8,4,5]
         * </pre>
         */
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            ListNode a = headA, b = headB;
            while (a != b) {
                a = (a == null) ? headB : a.next;
                b = (b == null) ? headA : b.next;
            }
            return a;
        }
    }

    /**
     * 141. 环形链表 <br>
     * https://leetcode-cn.com/problems/linked-list-cycle/
     **/
    static class 环形链表 {

        static void run() {
            环形链表 环形链表 = new 环形链表();

            System.out.println(环形链表.hasCycle(环形链表.getListNode()));
            System.out.println(环形链表.hasCycle(环形链表.getListNode2()));
        }

        ListNode getListNode() {
            ListNode node_4 = new ListNode(-4);
            ListNode node_2 = new ListNode(2, new ListNode(0, node_4));
            node_4.next = node_2;
            return new ListNode(3, node_2);
        }

        ListNode getListNode2() {
            ListNode node_4 = new ListNode(4);
            ListNode node_0 = new ListNode(0,
                new ListNode(1, new ListNode(2, new ListNode(3, node_4))));
            node_4.next = node_0;
            return new ListNode(-3, new ListNode(-2, new ListNode(-1, node_0)));
        }

        boolean hasCycle(ListNode head) {

            if (head == null || head.next == null) {
                return false;
            }

            ListNode fast = head.next;
            ListNode slow = head;

            while (slow != null) {

                if (fast == null || fast.next == null) {
                    return false;
                }

                // System.out.println(slow.val);
                // System.out.println(fast.val);
                if (slow.equals(fast)) {
                    return true;
                }
                fast = fast.next.next;
                slow = slow.next;
            }

            return false;
        }

        public boolean hasCycle2(ListNode head) {
            Set<ListNode> seen = new HashSet<ListNode>();
            while (head != null) {
                if (!seen.add(head)) {
                    return true;
                }
                head = head.next;
            }
            return false;

        }
    }

    /**
     * 83. 删除排序链表中的重复元素
     *
     * <pre>
     * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
     *
     * 示例 1:
     * 输入: 1->1->2
     * 输出: 1->2
     *
     * 示例 2:
     * 输入: 1->1->2->3->3
     * 输出: 1->2->3
     * </pre>
     **/
    static class 删除排序链表中的重复元素 {

        public static ListNode getListNode2() {
            return new ListNode(1, new ListNode(1, new ListNode(1)));
        }

        public static ListNode getListNode() {
            return new ListNode(1, new ListNode(1,
                new ListNode(2, new ListNode(3, new ListNode(3)))));
        }

        public static ListNode deleteDuplicates(ListNode head) {

            // 操作node
            ListNode node = head;

            while (node != null && node.next != null) {
                // 如果重复直接指定下下个
                if (node.val == node.next.val) {
                    node.next = node.next.next;
                } else {
                    node = node.next;
                }
            }
            return head;
        }
    }

    /**
     * 19. 删除链表的倒数第N个节点
     *
     * <pre>
     * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
     *
     * 示例：
     * 给定一个链表: 1->2->3->4->5, 和 n = 2.
     * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
     * </pre>
     **/
    static class 删除链表的倒数第N个节点 {
        public static ListNode getListNode() {
            return new ListNode(1, new ListNode(2,
                new ListNode(3, new ListNode(4, new ListNode(5)))));
        }

        public static ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(0, head);
            ListNode first = head;
            ListNode second = dummy;

            // 直接找到需要删除的那一个
            for (int i = 0; i < n; ++i) {
                first = first.next;
            }
            // 遍历到最后一个
            while (first != null) {
                first = first.next;
                second = second.next;
            }
            // 删除first
            second.next = second.next.next;
            ListNode ans = dummy.next;
            return ans;
        }

    }

    /**
     * 2. 两数相加
     *
     * <pre>
     * 示例：
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     * </pre>
     **/
    static class 两数相加 {

        public static ListNode getListNode3() {
            return new ListNode(2, new ListNode(4, new ListNode(9)));
        }

        public static ListNode getListNode4() {
            return new ListNode(5,
                new ListNode(6, new ListNode(4, new ListNode(9))));
        }

        public static ListNode getListNode1() {
            return new ListNode(2, new ListNode(4, new ListNode(3)));
        }

        public static ListNode getListNode2() {
            return new ListNode(5, new ListNode(6, new ListNode(4)));
        }

        public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            // head记录，tail操作
            ListNode head = null, tail = null;
            int carry = 0;
            while (l1 != null || l2 != null) {
                int n1 = l1 != null ? l1.val : 0;
                int n2 = l2 != null ? l2.val : 0;
                // 每个数加和
                int sum = n1 + n2 + carry;
                if (head == null) {
                    // 初始化
                    head = tail = new ListNode(sum % 10);
                } else {
                    // 更新tail,和tail下标
                    tail.next = new ListNode(sum % 10);
                    tail = tail.next;
                }
                // 更新下标
                if (l1 != null) {
                    l1 = l1.next;
                }
                // 更新下标
                if (l2 != null) {
                    l2 = l2.next;
                }
                // 
                carry = sum / 10;
            }
            if (carry > 0) {
                tail.next = new ListNode(carry);
            }
            return head;
        }

    }

    /**
     * [21]合并两个有序链表
     **/
    final static class 合并两个有序链表 extends 链表 {

        ListNode getL1() {
            return new ListNode(1, new ListNode(2, new ListNode(4)));
        }

        ListNode getL2() {
            return new ListNode(1, new ListNode(3, new ListNode(4)));
        }

        static void run() {
            合并两个有序链表 合并两个有序链表 = new 合并两个有序链表();
            System.out.println(
                合并两个有序链表.mergeTwoLists(合并两个有序链表.getL1(), 合并两个有序链表.getL2()));
        }

        ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }

            if (l1.val < l2.val) {
                l1.next = this.mergeTwoLists(l1.next, l2);
                return l1;
            } else {
                l2.next = this.mergeTwoLists(l1, l2.next);
                return l2;
            }
        }
    }

    /**
     * 328. 奇偶链表
     * （这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性）
     *
     * <pre>
     * 示例 1:
     * 输入: 1->2->3->4->5->NULL
     * 输出: 1->3->5->2->4->NULL
     *
     * 示例 2:
     * 输入: 2->1->3->5->6->4->7->NULL
     * 输出: 2->3->6->7->1->5->4->NULL
     * </pre>
     **/
    static class 奇偶链表 {

        public static ListNode getListNode() {
            return new ListNode(1, new ListNode(2,
                new ListNode(3, new ListNode(4, new ListNode(5)))));
        }

        public static ListNode oddEvenList(ListNode head) {
            if (head == null) {
                return null;
            }

            // 偶数
            ListNode o = head.next;
            // 基数
            ListNode j = head;
            // 指针,从下一个开始,当前是偶数
            ListNode i = o;

            while (i != null && i.next != null) {
                // 基数的下一个 是 指针偶数的下一个
                j.next = i.next;
                // 更新基数坐标
                j = j.next;
                // 回正指针
                i.next = j.next;
                // 更新指针
                i = i.next;
            }
            // 基数 与 偶数 合并
            j.next = o;
            return head;
        }
    }
}
