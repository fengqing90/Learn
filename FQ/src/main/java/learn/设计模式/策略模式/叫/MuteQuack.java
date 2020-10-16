package learn.设计模式.策略模式.叫;

/**
 * 不会叫策略
 *
 * @author fengqing
 * @date 2020/10/15 20:01
 */
public class MuteQuack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("我不会叫");
    }
}
