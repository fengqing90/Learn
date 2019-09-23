package learn.设计模式.桥接模式.例子1;

public class MyBridge extends Bridge {
    @Override
    public void method() {
        this.getSource().method();
    }
}
