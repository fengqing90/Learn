package learn.设计模式.装饰器模式;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/1/4 16:55
 */
public abstract class Decorator implements Component {

    public Decorator(Component component) {
        this.component = component;
    }

    private Component component;

    @Override
    public void operation() {
        this.component.operation();
    }
}
