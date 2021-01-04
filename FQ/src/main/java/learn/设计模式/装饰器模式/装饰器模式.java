package learn.设计模式.装饰器模式;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/1/4 17:03
 */
public class 装饰器模式 {
    public static void main(String[] args) {

        // ConcreteComponent c = new ConcreteComponent();
        //
        // Decorator a = new ConcreteDecoratorA(c);
        //
        // Decorator b = new ConcreteDecoratorB(a);
        //
        // b.operation();

        new ConcreteDecoratorB(new ConcreteDecoratorB(new ConcreteDecoratorB(
            new ConcreteDecoratorA(new ConcreteComponent())))).operation();

    }
}
