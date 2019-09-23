package learn.设计模式.命令模式.命令;

import learn.设计模式.命令模式.BoardScreen;

/**
 * @Description:编辑命令
 * @author: FengQing
 * @date: 2018年3月23日 下午3:09:48
 */
public class EditCommand extends Command {

    public EditCommand(BoardScreen screen) {
        super("Edit", screen);
    }

    @Override
    public void execute() {
        this.screen.edit(this.args);
    }
}
