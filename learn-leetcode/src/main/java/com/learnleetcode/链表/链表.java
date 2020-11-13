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
        System.out.println(奇偶链表.oddEvenList(奇偶链表.getListNode()));
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
