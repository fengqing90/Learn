package learn.设计模式.适配器模式;

/** * 小米六 type c 接口默认实现功能 * * @date 2019/6/24 20:02 */
public class XiaoMi6InterfaceImpl implements XiaoMi6Interface {
    @Override
    public void listenMusic() {
        System.out.println("通过 type c 接口的耳机听歌");
    }
}