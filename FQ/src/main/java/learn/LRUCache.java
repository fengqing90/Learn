package learn;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class uses the LinkedHashMap to build LRU cache. User can define the
 * cache size.
 */
public class LRUCache {
    int cacheSize = 1;
    float loadFactor = 0.75f; // default
    LinkedHashMap<Object, Object> map;

    public LRUCache(int cacheSize) {
        this.cacheSize = cacheSize;
        this.map = new LinkedHashMap<Object, Object>(cacheSize, this.loadFactor,
            true) {
            /**
             *
             */
            private static final long serialVersionUID = 919558900405159883L;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return this.size() > LRUCache.this.cacheSize;
                //				 return false;
            }
        };
    }

    public synchronized void clear() {
        this.map.clear();
    }

    public synchronized Object get(Object key) {
        return this.map.get(key);
    }

    public synchronized void put(Object key, Object value) {
        this.map.put(key, value);
    }

    public synchronized Object remove(Object key) {
        return this.map.remove(key);
    }

    public synchronized int size() {
        return this.map.size();
    }

    public synchronized Collection<Object> values() {
        return this.map.values();
    }

    public static void main(String[] args) {
        // testing
        int size = 6;
        LRUCache cache = new LRUCache(size);
        cache.put(new Integer("1"), "1");
        cache.put(new Integer("2"), "2");
        cache.put(new Integer("3"), "3");
        cache.put(new Integer("4"), "4");
        cache.put(new Integer("5"), "5");
        cache.put(new Integer("6"), "6");

        //		Object[] values = cache.values().toArray();
        //		for (int i = 0; i < values.length; i++) {
        //			System.out.println((String) values[i]);
        //		}

        System.out.println("..............");
        String value = (String) cache.get(new Integer(1));
        String value2 = (String) cache.get(new Integer(2));
        System.out.println(value);
        System.out.println(value2);
        System.out.println("Testing ...");

        Object[] values = cache.values().toArray();
        for (Object value3 : values) {
            System.out.println((String) value3);
        }
    }
}
