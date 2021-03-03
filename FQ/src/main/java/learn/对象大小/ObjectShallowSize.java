package learn.对象大小;

import org.openjdk.jol.info.ClassLayout;

/**
 * @ClassName ObjectShallowSize
 * @Description TODO
 * @Author fengqing
 * @Date 2020/3/30 16:11
 */
public class ObjectShallowSize {

    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
