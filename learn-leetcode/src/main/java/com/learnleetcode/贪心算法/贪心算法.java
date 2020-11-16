package com.learnleetcode.贪心算法;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * <pre>
 * 贪心算法（又称贪婪算法）是指，在对问题求解时，总是做出在当前看来是最好的选择。
 * 也就是说，不从整体最优上加以考虑，他所做出的是在某种意义上的局部最优解。
 *
 * 贪心算法不是对所有问题都能得到整体最优解，关键是贪心策略的选择，选择的贪心策略必须具备无后效性，即某个状态以前的过程不会影响以后的状态，只与当前状态有关。
 * </pre>
 *
 * @author fengqing
 * @date 2020/11/16 17:40
 */
public class 贪心算法 {
    public static void main(String[] args) {
        System.out
            .println(Arrays.deepToString(根据身高重建队列.reconstructQueue(new int[][] {
                { 7, 0 }, { 4, 4 }, { 7, 1 }, { 5, 0 }, { 6, 1 }, { 5, 2 } })));
        System.out.println("******************************************");
    }

    /**
     * 406. 根据身高重建队列
     * 
     * <pre>
     * 假设有打乱顺序的一群人站成一个队列。 每个人由一个整数对(h, k)表示，其中h是这个人的身高，k是排在这个人前面且身高大于或等于h的人数。编写一个算法来重建这个队列。
     *
     * 注意：
     * 总人数少于1100人。
     *
     * 示例
     * 输入:
     * [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
     * 输出:
     * [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
     * </pre>
     **/
    static class 根据身高重建队列 {
        static int[][] reconstructQueue(int[][] people) {
            Arrays.sort(people,
                (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0]);
            System.out.println(Arrays.deepToString(people));

            LinkedList<int[]> list = new LinkedList<>();
            for (int[] i : people) {
                list.add(i[1], i);
            }
            return list.toArray(new int[list.size()][2]);
        }
    }
}
