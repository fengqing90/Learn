package learn.设计模式.命令模式new.命令;

import learn.设计模式.命令模式new.实物.Stereo;

public class StereoOffCommand implements Command {
    private Stereo stereo;

    public void setStereo(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        this.stereo.off();
    }

    @Override
    public void undo() {
        this.stereo.on();
        this.stereo.setCD();
        this.stereo.setVolume();
    }
}