package learn.设计模式.命令模式;

/**
 * @Description:公告板系统界面类
 * @author: FengQing
 * @date: 2018年3月23日 下午3:11:08
 */
public class BoardScreen {
    private String name;

    public void open(String arg) {
        System.out.println(this.name + ":" + arg + ":open");
    }

    public void create(String arg) {
        System.out.println(this.name + ":" + arg + ":create");
    }

    public void edit(String arg) {
        System.out.println(this.name + ":" + arg + ":edit");
    }

    public BoardScreen(String name) {
        super();
        this.name = name;
    }
}
