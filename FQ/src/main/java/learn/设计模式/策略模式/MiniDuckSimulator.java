package learn.设计模式.策略模式;

import learn.设计模式.策略模式.叫.MuteQuack;
import learn.设计模式.策略模式.叫.Quack;
import learn.设计模式.策略模式.叫.QuackBehavior;
import learn.设计模式.策略模式.飞.FlyBehavior;
import learn.设计模式.策略模式.飞.FlyNoWay;
import learn.设计模式.策略模式.飞.FlyWithWings;
import learn.设计模式.策略模式.鸭.Duck;
import learn.设计模式.策略模式.鸭.MallardDuck;
import learn.设计模式.策略模式.鸭.ModelDuck;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/15 20:02
 */
public class MiniDuckSimulator {
    // 我是一只模型鸭子
    // 老司机带带我，我不会飞
    // 我不会叫
    //     -------------------
    // 我是一只野鸭子
    // 我会飞行，
    // 一冲云霄 呱呱叫

    public static void main(String[] args) {

        //定义不会叫不会飞的鸭子
        FlyBehavior flyBehavior = new FlyNoWay();
        QuackBehavior quackBehavior = new MuteQuack();
        Duck modelDuck = new ModelDuck(flyBehavior, quackBehavior);
        //这里我们可以设置不同的行为实现类就会执行不同的策略
        // modelDuck.setFlyBehavior(flyBehavior);
        // modelDuck.setQuackBehavior(quackBehavior);

        modelDuck.display();
        // modelDuck.performFly();
        // modelDuck.performQuack();
        System.out.println("-------------------");

        // 定义会叫会飞的鸭子
        FlyBehavior flyWithWings = new FlyWithWings();
        QuackBehavior quack = new Quack();
        Duck mallardDuck = new MallardDuck(flyWithWings, quack);

        // mallardDuck.setFlyBehavior(flyWithWings);
        // mallardDuck.setQuackBehavior(quack);

        mallardDuck.display();
        // mallardDuck.performFly();
        // mallardDuck.performQuack();

    }
}
