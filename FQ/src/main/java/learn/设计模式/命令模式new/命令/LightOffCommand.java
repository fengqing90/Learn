package learn.设计模式.命令模式new.命令;

import learn.设计模式.命令模式new.实物.Light;

public class LightOffCommand implements Command {
    /** * 持有接受者实例，以便当命令execute执行的时候由接受者执行 */
    private Light light;

    @Override
    public void execute() {
        this.light.off();
    }

    @Override
    public void undo() {
        this.light.on();
    }

    public void setLight(Light light) {
        this.light = light;
    }
}