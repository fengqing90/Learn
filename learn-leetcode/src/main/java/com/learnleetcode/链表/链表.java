package com.learnleetcode.链表;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/13 15:10
 */
public class 链表 {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return this.val + "->" + this.next;
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
        System.out.println(
            删除排序链表中的重复元素.deleteDuplicates(删除排序链表中的重复元素.getListNode2()));
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
    static class 合并两个有序链表 {

        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
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
