package learn.设计模式.命令模式new.命令;

import learn.设计模式.命令模式new.实物.Stereo;

/** * 音响开指令 */
public class StereoOnCommand implements Command {
    private Stereo stereo;

    @Override
    public void execute() {
        this.stereo.on();
        this.stereo.setCD();
        this.stereo.setVolume();
    }

    @Override
    public void undo() {
        this.stereo.off();
    }

    public void setStereo(Stereo stereo) {
        this.stereo = stereo;
    }
}