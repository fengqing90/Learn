package learn.设计模式.桥接模式.例子2;

import learn.设计模式.桥接模式.例子2.实现类.图片.JPGImage;
import learn.设计模式.桥接模式.例子2.实现类.图片.PNGImage;
import learn.设计模式.桥接模式.例子2.实现类.操作系统.LinuxImp;
import learn.设计模式.桥接模式.例子2.实现类.操作系统.WindowsImp;

class Client {

    public static void main(String args[]) {

        Image image = new JPGImage();
        ImageImp imp = new WindowsImp();

        image.setOsImp(imp);
        image.parseFile("小龙女");

        /////////////

        image = new PNGImage();
        imp = new LinuxImp();
        image.setOsImp(imp);
        image.parseFile("小龙女");
    }
}