package learn.设计模式.装饰器模式;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/1/4 16:59
 */
public class ConcreteDecoratorB extends Decorator {
    public ConcreteDecoratorB(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        super.operation();
        this.addB();
    }

    private void addB() {
        System.out.println("addB");
    }
}
