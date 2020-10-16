package learn.设计模式.策略模式.飞;

/**
 * 不会飞的策略
 *
 * @author fengqing
 * @date 2020/10/15 20:00
 */
public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("老司机带带我，我不会飞");
    }
}