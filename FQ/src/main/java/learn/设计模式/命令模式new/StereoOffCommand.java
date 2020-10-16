package learn.设计模式.命令模式new;

public class StereoOffCommand implements Command {
    private Stereo stereo;

    public void setStereo(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.off();
    }

    @Override
    public void undo() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume();
    }
}