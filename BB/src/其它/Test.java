package 其它;

import org.apache.commons.lang3.time.StopWatch;

class T {

    public static void main(String[] args) {

        System.out.println("T main");

    }

}

public class Test {

    // 程序入口函数

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Thread.sleep(2000);
        stopWatch.stop();
        System.out.println(stopWatch);

        stopWatch.reset();
        stopWatch.start();
        Thread.sleep(3000);
        stopWatch.stop();
        System.out.println(stopWatch);

        System.out.println("Test main");

    }

}