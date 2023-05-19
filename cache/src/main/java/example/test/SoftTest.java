package example.test;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SoftTest<K, V>{
    private Map<K, SoftReference<V>> cache;
    private Function<K, V> producer = (K key) -> (V) key.toString();

    public SoftReference<V> get(K key) {
        return cache.get(producer.apply(key));
    }

    public void put(K key) {
        cache.put(key, new SoftReference<>(producer.apply(key)));
    }

    public SoftTest() {
        this.cache = new HashMap<>();
    }

}

