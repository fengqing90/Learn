package learn.设计模式.适配器模式;

/** * TODO * * @author fengqing * @date 2020/10/16 10:23 */
public class Client {
    public static void main(String[] args) {
        //被适配者 3.5毫米耳机           
        CommonPhoneInterface headPhone = new CommonPhoneImpl();
        //适配器           
        TypeC2HeadphoneAdapter typeC2HeadphoneAdapter = new TypeC2HeadphoneAdapter(
            headPhone);
        //通过适配器实现了听歌          
        typeC2HeadphoneAdapter.listenMusic();
    }
}