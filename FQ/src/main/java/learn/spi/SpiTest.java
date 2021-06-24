package learn.spi;

import java.util.ServiceLoader;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/6 15:08
 */
public class SpiTest {
    public static void main(String[] args) {
        // javaSPI();
        dubboSPI();

    }

    //  Dubbo SPI
    private static void dubboSPI() {
        ExtensionLoader<Car> extensionLoader = ExtensionLoader
            .getExtensionLoader(Car.class);
        Car car = extensionLoader.getExtension("honda");
        car.run();
    }

    // Java SPI  learn.spi.Car
    private static void javaSPI() {
        ServiceLoader<Car> serviceLoader = ServiceLoader.load(Car.class);
        serviceLoader.forEach(Car::run);
    }
}
