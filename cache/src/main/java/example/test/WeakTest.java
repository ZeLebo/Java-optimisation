package example.test;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class WeakTest<K, V> {
    private Map<K, WeakReference<V>> cache;
    // we need this function to avoid strongReference
    private Function<K, V> producer = (K key) -> (V) key.toString();

    public WeakReference<V> get(K key) {
        return cache.get(producer.apply(key));
    }

    public void put(K key) {
        cache.put(key, new WeakReference<>(producer.apply(key)));
    }

    public WeakTest() {
        this.cache = new HashMap<>();
    }
}
