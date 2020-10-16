package learn.设计模式.适配器模式;

public class CommonPhoneImpl implements CommonPhoneInterface {
    @Override
    public void listenMusic() {
        System.out.println("标准手机 3.5 mm 耳机接口听歌");
    }
}