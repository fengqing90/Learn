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
            // aClass.newInstance();
            System.out
                .println("#########-------------结束符------------##########");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try {
            Class.forName("learn.类加载.ClassForName");
            System.out
                .println("#########-------------结束符------------##########");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        try {
            this.getClass().getClassLoader()
                .loadClass("learn.类加载.ClassForName");
            System.out
                .println("#########-------------结束符------------##########");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
