package learn.java_NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/7/7 17:26
 */
public class NIO快速入门 {
    static class 分散读取和聚合写入 {
        /*
         * 1.txt ：abcdefghijklmnopqrstuvwxyz//26个字母
         * 打印结果：
         * posstion=5,limit=5
         * posstion=5,limit=5
         * posstion=5,limit=5
         * posstion=5,limit=5
         * posstion=5,limit=5
         * posstion=1,limit=5
         * 总长度:26
         */
        public static void main(String[] args) throws Exception {
            //获取文件输入流
            File file = new File("1.txt");
            FileInputStream inputStream = new FileInputStream(file);
            //从文件输入流获取通道
            FileChannel inputStreamChannel = inputStream.getChannel();
            //获取文件输出流
            FileOutputStream outputStream = new FileOutputStream(
                new File("2.txt"));
            //从文件输出流获取通道
            FileChannel outputStreamChannel = outputStream.getChannel();
            //创建三个缓冲区，分别都是5
            ByteBuffer byteBuffer1 = ByteBuffer.allocate(5);
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(5);
            ByteBuffer byteBuffer3 = ByteBuffer.allocate(5);
            //创建一个缓冲区数组
            ByteBuffer[] buffers = new ByteBuffer[] { byteBuffer1, byteBuffer2,
                byteBuffer3 };
            //循环写入到buffers缓冲区数组中，分散读取
            long read;
            long sumLength = 0;
            while ((read = inputStreamChannel.read(buffers)) != -1) {
                sumLength += read;
                Arrays
                    .stream(buffers).map(buffer -> "posstion="
                        + buffer.position() + ",limit=" + buffer.limit())
                    .forEach(System.out::println);
                //切换模式
                Arrays.stream(buffers).forEach(Buffer::flip);
                //聚合写入到文件输出通道
                outputStreamChannel.write(buffers);
                //清空缓冲区
                Arrays.stream(buffers).forEach(Buffer::clear);
            }
            System.out.println("总长度:" + sumLength);
            //关闭通道
            outputStream.close();
            inputStream.close();
            outputStreamChannel.close();
            inputStreamChannel.close();
        }
    }

    /*
     * transferTo()： 把源通道的数据传输到目的通道中。
     * transferFrom()：把来自源通道的数据传输到目的通道。
     */
    static class 通道间的数据传输 {

        public static void main(String[] args) throws Exception {
            //获取文件输入流
            File file = new File("1.txt");
            FileInputStream inputStream = new FileInputStream(file);
            //从文件输入流获取通道
            FileChannel inputStreamChannel = inputStream.getChannel();
            //获取文件输出流
            FileOutputStream outputStream = new FileOutputStream(
                new File("2.txt"));
            //从文件输出流获取通道
            FileChannel outputStreamChannel = outputStream.getChannel();
            //创建一个byteBuffer，小文件所以就直接一次读取，不分多次循环了
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

            //**********************
            //把输入流通道的数据读取到输出流的通道
            inputStreamChannel.transferTo(0, byteBuffer.limit(),
                outputStreamChannel);
            //**********************
            //**********************
            // // 把输入流通道的数据读取到输出流的通道
            // outputStreamChannel.transferFrom(inputStreamChannel,0,byteBuffer.limit());
            //**********************

            //关闭通道
            outputStream.close();
            inputStream.close();
            outputStreamChannel.close();
            inputStreamChannel.close();
        }
    }

    static class 非直接_直接缓冲区 {
        /***
         * 非直接缓冲区的创建方式： static ByteBuffer allocate(int capacity) jvm空间和物理空间拷贝
         * 直接缓冲区的创建方式： static ByteBuffer allocateDirect(int capacity) 物理文件映射
         */
        public static void main(String[] args) throws Exception {
            long starTime = System.currentTimeMillis();
            //获取文件输入流
            File file = new File("D:\\小电影.mp4");//文件大小136 MB
            FileInputStream inputStream = new FileInputStream(file);
            //从文件输入流获取通道
            FileChannel inputStreamChannel = inputStream.getChannel();
            //获取文件输出流
            FileOutputStream outputStream = new FileOutputStream(
                new File("D:\\test.mp4"));
            //从文件输出流获取通道
            FileChannel outputStreamChannel = outputStream.getChannel();
            //创建一个直接缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(5 * 1024 * 1024);
            //创建一个非直接缓冲区
            //ByteBuffer byteBuffer = ByteBuffer.allocate(5 * 1024 * 1024);
            //写入到缓冲区
            while (inputStreamChannel.read(byteBuffer) != -1) {
                //切换读模式
                byteBuffer.flip();
                outputStreamChannel.write(byteBuffer);
                byteBuffer.clear();
            }
            //关闭通道
            outputStream.close();
            inputStream.close();
            outputStreamChannel.close();
            inputStreamChannel.close();
            long endTime = System.currentTimeMillis();
            System.out.println("消耗时间：" + (endTime - starTime) + "毫秒");

            // 结果：
            // 直接缓冲区的消耗时间：283毫秒
            // 非直接缓冲区的消耗时间：487毫秒
        }
    }
}
