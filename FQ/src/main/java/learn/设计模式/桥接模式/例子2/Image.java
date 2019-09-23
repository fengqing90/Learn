package learn.设计模式.桥接模式.例子2;

// 抽象图像类：抽象类
public abstract class Image {

    protected ImageImp imp;

    public void setOsImp(ImageImp imp) {
        this.imp = imp;
    }


    public abstract void parseFile(String fileName);
}