package learn.bankQueue;

import java.util.Random;

import learn.bankQueue.WindowService.CustomerType;

import org.junit.Test;

public class BankTest {

    @Test
    public void test_BankQueue() {

        // 创建用户
        for (int j = 1; j <= 200; j++) {
            int a = Math.abs(new Random().nextInt(3));
            switch (a) {
                case 0:
                    NumberManager.addCommonQueue(j);
                    break;
                case 1:
                    NumberManager.addExpressQueue(j);
                    break;
                case 2:
                    NumberManager.addVipQueue(j);
                    break;
            }
        }
        System.out.println("普通：");
        for (Object o : NumberManager.commonQueue) {
            System.out.print(o + ",");
        }
        System.out.println();
        System.out.println("快速：");
        for (Object o : NumberManager.expressQueue) {
            System.out.print(o + ",");
        }
        System.out.println();
        System.out.println("VIP：");
        for (Object o : NumberManager.vipQueue) {
            System.out.print(o + ",");
        }
        System.out.println();

        WindowService windowService = null;
        for (int i = 1; i < 6; i++) {
            windowService = new WindowService();
            windowService.setWindowNumber(i);
            windowService.setType(CustomerType.COMMON);
            windowService.commonWindService();
        }

        for (int i = 6; i < 9; i++) {
            windowService = new WindowService();
            windowService.setWindowNumber(i);
            windowService.setType(CustomerType.EXPRESS);
            windowService.commonWindService();
        }

        for (int i = 9; i < 11; i++) {
            windowService = new WindowService();
            windowService.setWindowNumber(i);
            windowService.setType(CustomerType.VIP);
            windowService.commonWindService();
        }
    }

    public static void main(String[] args) {
        new BankTest().test_BankQueue();
    }
}
