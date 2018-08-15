package learn.桥接模式.例子2.实现类.图片;


import learn.桥接模式.例子2.Image;
import learn.桥接模式.例子2.Matrix;

// PNG格式图像：扩充抽象类
public class PNGImage extends Image {

    @Override
    public void parseFile(String fileName) {
        //模拟解析PNG文件并获得一个像素矩阵对象m;
        Matrix m = new Matrix();
        this.imp.doPaint(m);
        System.out.println(fileName + "，格式为PNG。");
    }
}