package learn.设计模式.装饰器模式;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/1/4 16:55
 */
public class ConcreteDecoratorA extends Decorator {

    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        super.operation();
        this.addA();
    }

    private void addA() {
        System.out.println("addA");
    }
}
