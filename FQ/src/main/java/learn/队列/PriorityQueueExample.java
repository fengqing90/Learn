package learn.队列;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/7/26 17:02
 */
public class PriorityQueueExample {
    public static void main(String[] args) {
        System.out.println(65 >> 1);
        //优先队列自然排序示例

        Queue<Integer> integerPriorityQueue = new PriorityQueue<>(7);

        Random rand = new Random();

        for (int i = 0; i < 7; i++) {
            integerPriorityQueue.add(rand.nextInt(100));
        }

        for (int i = 0; i < 7; i++) {

            Integer in = integerPriorityQueue.poll();

            System.out.println("Processing Integer:" + in);

        }

        //优先队列使用示例

        Queue<Customer> customerPriorityQueue = new PriorityQueue<>(7,
            idComparator);

        addDataToQueue(customerPriorityQueue);

        pollDataFromQueue(customerPriorityQueue);

    }

    //匿名Comparator实现
    public static Comparator<Customer> idComparator = (c1,
            c2) -> (int) (c1.getId() - c2.getId());

    //用于往队列增加数据的通用方法
    private static void addDataToQueue(Queue<Customer> customerPriorityQueue) {

        Random rand = new Random();

        for (int i = 0; i < 7; i++) {

            int id = rand.nextInt(100);

            customerPriorityQueue.add(new Customer(id, "Pankaj " + id));

        }

    }

    //用于从队列取数据的通用方法

    private static void pollDataFromQueue(
            Queue<Customer> customerPriorityQueue) {

        while (true) {

            Customer cust = customerPriorityQueue.poll();

            if (cust == null)
                break;

            System.out.println("Processing Customer with ID=" + cust.getId());

        }

    }

}

class Customer {

    private int id;

    private String name;

    public Customer(int i, String n) {

        this.id = i;

        this.name = n;

    }

    public int getId() {

        return id;

    }

    public String getName() {

        return name;
    }

}