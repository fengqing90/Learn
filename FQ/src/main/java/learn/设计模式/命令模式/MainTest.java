package learn.设计模式.命令模式;

import learn.设计模式.命令模式.命令.CommandQueue;
import learn.设计模式.命令模式.命令.CreateCommand;
import learn.设计模式.命令模式.命令.EditCommand;
import learn.设计模式.命令模式.命令.OpenCommand;

/**
 * @ClassName: MainTest
 * @Description:
 * @author: FengQing
 * @date: 2018年3月26日 下午3:57:28
 */
public class MainTest {

    public static void main(String[] args) {
        // 非队列
//        MainTest.command();

        // 队列
        MainTest.command4Queue();
    }

    protected static void command4Queue() {

        // 接收者 : 显示面板
        BoardScreen screen = new BoardScreen("显示面板");
        // 队列
        CommandQueue queue = new CommandQueue();

        // 菜单
        Menu page = new Menu("首页");
        Menu afterPage = new MenuItem("售后");
        Menu salesPage = new MenuItem("销售页面");
        page.addMenuItem(afterPage);
        page.addMenuItem(salesPage);

        page.setQueue(queue);
        afterPage.setQueue(queue);
        salesPage.setQueue(queue);

        // 显示菜单项
        System.out.println("显示导航:");
        page.display();
        MainTest.print("------------------------------");

        MainTest.print("【操作】：" + page.getMenuName());
        page.call4Queue(new OpenCommand(screen));     // 命令
        page.call4Queue(new CreateCommand(screen));   // 命令
        page.call4Queue(new EditCommand(screen));     // 命令
        MainTest.print("------------------------------");

        MainTest.print("【操作】：" + afterPage.getMenuName());
        afterPage.call4Queue(new OpenCommand(screen));// 命令
        MainTest.print("------------------------------");

        MainTest.print("【操作】：" + salesPage.getMenuName());
        salesPage.call4Queue(new OpenCommand(screen));// 命令
        salesPage.call4Queue(new EditCommand(screen));// 命令
        MainTest.print("------------------------------" + "\n");

//        MainTest.print("【单项恢复】");
//        page.recover();
//        afterPage.recover();
//        salesPage.recover();

        MainTest.print("【执行】");
        queue.execute();
        MainTest.print("------------------------------" + "\n");
        System.out.println("【全部恢复】");
        page.recover4Queue();

    }

    protected static void command() {
        // 接收者 : 显示面板
        BoardScreen screen = new BoardScreen("显示面板");

        // 菜单
        Menu page = new Menu("首页");
        Menu afterPage = new MenuItem("售后");
        Menu salesPage = new MenuItem("销售页面");
        page.addMenuItem(afterPage);
        page.addMenuItem(salesPage);

        // 显示菜单项
        System.out.println("显示导航:");
        page.display();
        MainTest.print("------------------------------");

        MainTest.print("【操作】：" + page.getMenuName());
        page.call(new OpenCommand(screen));     // 命令
        page.call(new CreateCommand(screen));   // 命令
        page.call(new EditCommand(screen));     // 命令
        MainTest.print("------------------------------");

        MainTest.print("【操作】：" + afterPage.getMenuName());
        afterPage.call(new OpenCommand(screen));// 命令
        MainTest.print("------------------------------");

        MainTest.print("【操作】：" + salesPage.getMenuName());
        salesPage.call(new OpenCommand(screen));// 命令
        salesPage.call(new EditCommand(screen));// 命令
        MainTest.print("------------------------------" + "\n");

        MainTest.print("【恢复】---" + page.getMenuName());
        page.recover();
        MainTest.print();

        MainTest.print("【恢复】---" + afterPage.getMenuName());
        afterPage.recover();
        MainTest.print();

        MainTest.print("【恢复】---" + salesPage.getMenuName());
        salesPage.recover();

    }

    static void print(String str) {
        if (str == null) {
            System.out.println();
        } else {
            System.out.println(str);
        }
    }

    static void print() {
        MainTest.print(null);
    }
}
