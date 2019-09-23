package learn.设计模式.命令模式.命令;

import learn.设计模式.命令模式.BoardScreen;

/**
 * @Description:新建命令
 * @author: FengQing
 * @date: 2018年3月23日 下午3:09:17
 */
public class CreateCommand extends Command {

    public CreateCommand(BoardScreen screen) {
        super("Create", screen);
    }

    @Override
    public void execute() {
        this.screen.create(this.args);
    }
}
