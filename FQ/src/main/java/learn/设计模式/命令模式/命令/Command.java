package learn.设计模式.命令模式.命令;

import learn.设计模式.命令模式.BoardScreen;

/**
 * @ClassName: Command
 * @Description:命令
 * @author: FengQing
 * @date: 2018年3月26日 下午2:38:07
 */
public abstract class Command {

    protected String name; //命令名称
    protected String args; //命令参数

    protected BoardScreen screen;

    public abstract void execute();

    public void setScreen(BoardScreen screen) {
        this.screen = screen;
    }

    public Command(BoardScreen screen) {
        super();
        this.screen = screen;
    }

    public Command(String name, BoardScreen screen) {
        super();
        this.name = name;
        this.screen = screen;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArgs() {
        return this.args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

}
