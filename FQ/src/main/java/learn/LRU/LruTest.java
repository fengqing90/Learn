package learn.LRU;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/28 20:14
 */
public class LruTest {
    public static void main(String[] args) {

        Lru4自定义<Integer, Integer> 自定义 = new Lru4自定义(10);

        自定义.put(1, 1);
        自定义.print();
        自定义.put(2, 2);
        自定义.print();
        自定义.put(3, 3);
        自定义.print();

        // LruTest4LRU4LinkedHashMap();
    }

    private static void LruTest4LRU4LinkedHashMap() {
        Lru4LinkedHashMap lru4LinkedHashMap = new Lru4LinkedHashMap(10);

        int key = 0;
        for (int i = 0; i < 10; i++) {
            lru4LinkedHashMap.put(key + "", key);
            key++;
        }
        lru4LinkedHashMap.print();

        for (int i = 0; i < 1; i++) {
            lru4LinkedHashMap.put(key + "", key);
            key++;
        }
        lru4LinkedHashMap.print();

        lru4LinkedHashMap.get("5");
        lru4LinkedHashMap.print();

        lru4LinkedHashMap.get("8");
        lru4LinkedHashMap.print();
    }
}
