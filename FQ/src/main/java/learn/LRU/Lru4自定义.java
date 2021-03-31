package learn.LRU;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/2/23 17:24
 */
public class Lru4自定义<k, v> {
    //容量
    private int capacity;
    //当前有多少节点的统计
    private int count;
    //缓存节点
    private Map<k, Node> nodeMap;
    private Node head;
    private Node tail;

    public void print() {
        // for (Map.Entry<k, Node> entry : nodeMap.entrySet()) {
        //     System.out.print("(" + entry.getValue().key + ":"
        //         + entry.getValue().value + ")--");
        // }
        System.out.println("*************");
        Node temp = head;
        while (temp != null) {
            System.out.println("(" + temp.key + ":" + temp.value + ")--");
            temp = temp.next;
        }
    }

    public Lru4自定义(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException(String.valueOf(capacity));
        }
        this.capacity = capacity;
        this.nodeMap = new HashMap<>();
        //初始化头节点和尾节点，利用哨兵模式减少判断头结点和尾节点为空的代码
        Node headNode = new Node(null, null);
        Node tailNode = new Node(null, null);
        headNode.next = tailNode;
        tailNode.pre = headNode;
        this.head = headNode;
        this.tail = tailNode;
    }

    public void put(k key, v value) {
        Node node = this.nodeMap.get(key);
        if (node == null) {
            if (this.count >= this.capacity) {
                //先移除一个节点
                this.removeNode();
            }
            node = new Node<>(key, value);
            //添加节点
            this.addNode(node);
        } else {
            //移动节点到头节点
            this.moveNodeToHead(node);
        }
    }

    public Node get(k key) {
        Node node = this.nodeMap.get(key);
        if (node != null) {
            this.moveNodeToHead(node);
        }
        return node;
    }

    private void removeNode() {
        Node node = this.tail.pre;
        //从链表里面移除
        this.removeFromList(node);
        this.nodeMap.remove(node.key);
        this.count--;
    }

    private void removeFromList(Node node) {
        Node pre = node.pre;
        Node next = node.next;

        pre.next = next;
        next.pre = pre;

        node.next = null;
        node.pre = null;
    }

    private void addNode(Node node) {
        //添加节点到头部
        this.addToHead(node);
        this.nodeMap.put((k) node.key, node);
        this.count++;
    }

    private void addToHead(Node node) {
        Node next = this.head.next;
        next.pre = node;
        node.next = next;
        node.pre = this.head;
        this.head.next = node;
    }

    public void moveNodeToHead(Node node) {
        //从链表里面移除
        this.removeFromList(node);
        //添加节点到头部
        this.addToHead(node);
    }

    class Node<k, v> {
        k key;
        v value;
        Node pre;
        Node next;

        public Node(k key, v value) {
            this.key = key;
            this.value = value;
        }
    }
}