package learn.设计模式.命令模式;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import learn.设计模式.命令模式.命令.Command;
import learn.设计模式.命令模式.命令.CommandQueue;

/**
 * @ClassName: Menu
 * @Description:菜单
 * @author: FengQing
 * @date: 2018年3月26日 下午3:36:34
 */
class Menu {

    List<Menu> items = new LinkedList<>();
    List<Command> commands = new LinkedList<>();

    CommandQueue queue;

    private String menuName;

    void addMenuItem(Menu i) {
        this.items.add(i);
    }

    /**
     * 调用
     *
     * @param command
     */
    public void call(Command command) {
        command.setArgs(this.menuName);
        command.execute();
        this.commands.add(command);
    }

    /**
     * 调用4队列
     *
     * @param command
     */
    public void call4Queue(Command command) {
        System.out.println("call4Queue:" + command.getName());
        command.setArgs(this.menuName);
        this.queue.addCommand(command);
    }

    public Menu(String menuName) {
        super();
        this.menuName = menuName;
    }

    /**
     * 显示导航
     */
    public void display() {
        System.out.println("显示：" + this.menuName);
        this.items.forEach(Menu::display);
    }

    /**
     * 恢复4队列
     */
    public void recover4Queue() {
        this.queue.recover();
    }

    /**
     * 恢复
     */
    public void recover() {

//        this.items.forEach(Menu::recover);

        Collections.reverse(this.commands);
        this.commands.forEach(Command::execute);

    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setQueue(CommandQueue queue) {
        this.queue = queue;
    }

}
