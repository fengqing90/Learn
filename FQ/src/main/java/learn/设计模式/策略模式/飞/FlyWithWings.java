package learn.设计模式.策略模式.飞;

/**
 * 会飞的策略
 *
 * @author fengqing
 * @date 2020/10/15 20:00
 */
public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("我会飞行，一冲云霄");
    }
}