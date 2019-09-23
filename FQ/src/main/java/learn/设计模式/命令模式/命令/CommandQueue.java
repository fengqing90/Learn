package learn.设计模式.命令模式.命令;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: CommandQueue
 * @Description:命令队列
 * @author: FengQing
 * @date: 2018年3月26日 下午5:38:41
 */
public class CommandQueue {

    private List<Command> commands = new LinkedList<>();

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public void removeCommand(Command command) {
        this.commands.remove(command);
    }

    public void execute() {
        this.commands.forEach(Command::execute);
    }

    public void recover() {

        synchronized (this) {

            Collections.reverse(this.commands);

            for (Iterator<Command> iterator = this.commands.iterator(); iterator
                .hasNext();) {
                iterator.next().execute();
                iterator.remove();
            }
        }
    }
}
