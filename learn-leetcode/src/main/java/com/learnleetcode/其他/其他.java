package com.learnleetcode.其他;

import com.learnleetcode.LeetCode;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/30 18:48
 */
public class 其他 extends LeetCode {
    public static void main(String[] args) {
        喝饮料.run();
        生兔子.run();
    }

    /**
     * 古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
     */
    static class 生兔子 extends 其他 {
        static void run() {
            new 生兔子().生();
        }

        void 生() {
            System.out.println("第1个月的兔子对数:    1");
            System.out.println("第2个月的兔子对数:    1");
            int f1 = 1, f2 = 1, f, M = 24;
            for (int i = 3; i <= M; i++) {
                f = f2;
                f2 = f1 + f2;
                f1 = f;
                System.out.println("第" + i + "个月的兔子对数: " + f2);
            }
        }
    }

    /**
     * 喝3瓶换1瓶，n瓶可以喝多少瓶
     */
    static class 喝饮料 extends 其他 {

        static void run() {
            new 喝饮料().drink();
        }

        void drink() {
            //初始饮料总数
            int n = 20;
            //兑换次数
            int i = 0;
            while (true) {
                //喝3瓶
                n -= 3;
                //兑换1瓶
                n++;
                //兑换次数+1
                i++;
                if (n < 3) {
                    System.out.println("共喝了" + (12 + i) + "瓶");
                    break;
                }
            }
        }
    }

}
