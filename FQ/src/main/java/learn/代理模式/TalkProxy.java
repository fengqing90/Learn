package learn.代理模式;

public class TalkProxy {
    Italk talker;

    public TalkProxy(Italk talker) {
        //super();
        this.talker = talker;
    }

    public void talk(String msg, String singname) {
        this.talker.talk(msg);
        this.sing(singname);
    }

    private void sing(String singname) {
        System.out.println("唱歌：" + singname);
    }
}
