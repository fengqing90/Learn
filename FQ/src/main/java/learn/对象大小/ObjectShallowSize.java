package learn.对象大小;

import java.lang.instrument.Instrumentation;

/**
 * @ClassName ObjectShallowSize
 * @Description TODO
 * @Author fengqing
 * @Date 2020/3/30 16:11
 */
public class ObjectShallowSize {

    private static Instrumentation inst;

    public static void premain(String agentArgs, Instrumentation instP) {
        inst = instP;
    }

    public static long sizeOf(Object obj) {
        return inst.getObjectSize(obj);
    }

}
