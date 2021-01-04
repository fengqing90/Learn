package learn.设计模式.装饰器模式;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/1/4 16:54
 */
public class ConcreteComponent implements Component {
    @Override
    public void operation() {
        System.out.println("ConcreteComponent");
    }
}
