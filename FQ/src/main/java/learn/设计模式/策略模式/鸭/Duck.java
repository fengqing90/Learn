package learn.设计模式.策略模式.鸭;

import learn.设计模式.策略模式.叫.QuackBehavior;
import learn.设计模式.策略模式.飞.FlyBehavior;
import lombok.Setter;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/15 19:57
 */
@Setter
public abstract class Duck {

    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    Duck(FlyBehavior flyBehavior, QuackBehavior quackBehavior) {
        this.flyBehavior = flyBehavior;
        this.quackBehavior = quackBehavior;
    }

    public void performFly() {
        flyBehavior.fly();
    }

    public void performQuack() {
        quackBehavior.quack();
    }

    /**
     * 展示鸭子
     */
    public void display() {
        this.performFly();
        this.performQuack();
    }
}
