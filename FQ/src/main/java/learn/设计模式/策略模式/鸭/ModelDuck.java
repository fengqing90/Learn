package learn.设计模式.策略模式.鸭;

import learn.设计模式.策略模式.叫.QuackBehavior;
import learn.设计模式.策略模式.飞.FlyBehavior;

/**
 * 模型鸭子
 *
 * @author fengqing
 * @date 2020/10/15 19:58
 */
public class ModelDuck extends Duck {

    public ModelDuck(FlyBehavior flyBehavior, QuackBehavior quackBehavior) {
        super(flyBehavior, quackBehavior);
    }

    @Override
    public void display() {
        super.display();

        System.out.println("我是一只模型鸭子");
    }
}