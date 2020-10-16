package learn.设计模式.命令模式new.实物;

/** * TODO * * @author fengqing * @date 2020/10/15 20:51 */
public class Stereo {
    public void on() {
        System.out.println("打开音响");
    }

    public void off() {
        System.out.println("关闭音响");
    }

    public void setCD() {
        System.out.println("放入CD");
    }

    public void setVolume() {
        System.out.println("音响音量设置为20");
    }
}