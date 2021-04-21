package learn.LRU;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author fengqing
 * @date 2020/10/28 20:13
 */
public class Lru4LinkedHashMap<K, V> {

    private static final float hashLoadFactory = 0.75f;
    private LinkedHashMap<K, V> map;
    private int cacheSize;

    public Lru4LinkedHashMap(int cacheSize) {
        this.cacheSize = cacheSize;
        int capacity = (int) Math.ceil(cacheSize / hashLoadFactory) + 1;
        map = new LinkedHashMap<K, V>(capacity, hashLoadFactory, true) {
            private static final long serialVersionUID = 1;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > Lru4LinkedHashMap.this.cacheSize;
            }
        };
    }

    public synchronized V get(K key) {
        return map.get(key);
    }

    public synchronized void put(K key, V value) {
        map.put(key, value);
    }

    public synchronized void clear() {
        map.clear();
    }

    public synchronized int usedSize() {
        return map.size();
    }

    public void print() {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.print(entry.getValue() + "--");
        }
        System.out.println();
    }
}