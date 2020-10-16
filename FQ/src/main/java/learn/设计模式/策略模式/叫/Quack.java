package learn.设计模式.策略模式.叫;

/**
 * 呱呱叫
 *
 * @author fengqing
 * @date 2020/10/15 20:01
 */
public class Quack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("呱呱叫");
    }
}