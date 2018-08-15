package learn.桥接模式.例子1;

public abstract class Bridge {

    private Sourceable source;

    public void method() {
        this.source.method();
    }

    public Sourceable getSource() {
        return this.source;
    }

    public void setSource(Sourceable source) {
        this.source = source;
    }
}
