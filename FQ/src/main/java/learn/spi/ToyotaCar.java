package learn.spi;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/6 14:21
 */
public class ToyotaCar implements Car {
    @Override
    public void run() {
        System.out.println("Toyota");
    }
}