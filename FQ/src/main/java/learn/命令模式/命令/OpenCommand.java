package learn.命令模式.命令;

import learn.命令模式.BoardScreen;

/**
 * @Description:打开命令
 * @author: FengQing
 * @date: 2018年3月23日 下午3:08:24
 */
public class OpenCommand extends Command {

    public OpenCommand(BoardScreen screen) {
        super("Open", screen);
    }

    @Override
    public void execute() {
        this.screen.open(this.args);
    }
}
