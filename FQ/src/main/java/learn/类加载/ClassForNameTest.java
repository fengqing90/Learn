package learn.类加载;

import org.junit.Test;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/9 19:39
 */
public class ClassForNameTest {

    @Test
    public void test1() {
        try {
            Class<?> aClass = ClassLoader.getSystemClassLoader()
                .loadClass("learn.类加载.ClassForName");

            Class<?> bClass = Class.forName("learn.类加载.ClassForName");

            System.out.println(aClass == bClass);

            System.out.println("#########-------------结束符------------##########"
                + aClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try {
            Class<?> aClass = Class.forName("learn.类加载.ClassForName");
            System.out.println("#########-------------结束符------------##########"
                + aClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        try {
            Class<?> aClass = this.getClass().getClassLoader()
                .loadClass("learn.类加载.ClassForName");
            System.out.println("#########-------------结束符------------##########"
                + aClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
