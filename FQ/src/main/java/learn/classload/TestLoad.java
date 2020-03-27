package learn.classload;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TestLoad
 * @Description TODO
 * @Author fengqing
 * @Date 2020/3/27 15:48
 */
public class TestLoad {

    //    private static Integer item = LoadMap.map.get("A");
    private static Integer item = LoadMap.mapGet("A");

    {
        System.out.println("TestLoad 静态块");
        init();
    }

    public static void init() {
        System.out.println("TestLoad init " + item);
    }

    public void method() {
        System.out.println("TestLoad method");
        new LoadMap();
    }

    public static void main(String[] args) {
        System.out.println(LoadMap.mapGet("A"));
        new TestLoad().method();
        System.out.println(LoadMap.mapGet("A"));

//        LoadMap mapGet
//        LoadMap mapGet
//        null
//        TestLoad 静态块
//        TestLoad init null
//        TestLoad method
//        LoadMap 静态块
//        LoadMap mapGet
//        1
    }
}

class LoadMap {

    public static Map<String, Integer> map = new HashMap<>();

    {
        System.out.println("LoadMap 静态块");
        map.put("A", 1);
    }

    public static Integer mapGet(String key) {
        System.out.println("LoadMap mapGet");
        return map.get(key);
    }

}