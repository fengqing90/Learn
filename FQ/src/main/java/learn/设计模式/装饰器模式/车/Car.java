package learn.设计模式.装饰器模式.车;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/1/4 17:06
 */
public interface Car {
    void run();

    static void main(String[] args) {
        Car benzCar = new BenzCar();
        Car bmwCar = new BmwCar();
        Car teslaCar = new TeslaCar();
        //创建自动驾驶的奔驰汽车
        CarDecorator autoBenzCar = new AutoCarDecorator(benzCar);
        //创建飞行的、自动驾驶的宝马汽车
        CarDecorator flyAutoBmwCar = new FlyCarDecorator(
            new AutoCarDecorator(bmwCar));

        benzCar.run();
        bmwCar.run();
        teslaCar.run();
        autoBenzCar.run();
        flyAutoBmwCar.run();
    }

    class BenzCar implements Car {
        @Override
        public void run() {
            System.out.println("奔驰开车了！");
        }
    }

    class BmwCar implements Car {
        @Override
        public void run() {
            System.out.println("宝马开车了！");
        }
    }

    class TeslaCar implements Car {
        @Override
        public void run() {
            System.out.println("特斯拉开车了！");
        }
    }

    abstract class CarDecorator implements Car {

        protected Car decoratedCar;

        public CarDecorator(Car decoratedCar) {
            this.decoratedCar = decoratedCar;
        }

        public void run() {
            decoratedCar.run();
        }
    }

    class AutoCarDecorator extends CarDecorator {

        public AutoCarDecorator(Car decoratedCar) {
            super(decoratedCar);
        }

        @Override
        public void run() {
            decoratedCar.run();
            autoRun();
        }

        private void autoRun() {
            System.out.println("开启自动驾驶");
        }
    }

    class FlyCarDecorator extends CarDecorator {

        public FlyCarDecorator(Car decoratedCar) {
            super(decoratedCar);
        }

        @Override
        public void run() {
            decoratedCar.run();
            fly();
        }

        private void fly() {
            System.out.println("开启飞行汽车模式");
        }

    }
}
