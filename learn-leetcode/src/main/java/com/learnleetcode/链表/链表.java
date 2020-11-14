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

        System.out.println(
            两数相加.addTwoNumbers(两数相加.getListNode1(), 两数相加.getListNode2()));
        System.out.println(
            两数相加.addTwoNumbers(两数相加.getListNode3(), 两数相加.getListNode4()));
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
