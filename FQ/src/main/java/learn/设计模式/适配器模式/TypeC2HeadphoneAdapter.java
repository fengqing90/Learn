package learn.设计模式.适配器模式;

public class TypeC2HeadphoneAdapter implements XiaoMi6Interface {
    private CommonPhoneInterface commonPhoneInterface;

    public TypeC2HeadphoneAdapter(CommonPhoneInterface commonPhoneInterface) {
        this.commonPhoneInterface = commonPhoneInterface;
    }

    @Override
    public void listenMusic() {
        System.out.println("通过 type c 转 3.5mm适配器将目标 type c 接口适配上3.5mm接口。");
        commonPhoneInterface.listenMusic();
    }
}