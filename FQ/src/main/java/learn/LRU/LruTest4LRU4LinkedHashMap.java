package learn.LRU;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/28 20:14
 */
public class LruTest4LRU4LinkedHashMap {
    public static void main(String[] args) {

        LRU4LinkedHashMap lru4LinkedHashMap = new LRU4LinkedHashMap(10);

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
