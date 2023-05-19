package example.test;

import java.lang.ref.PhantomReference;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PhantomTest<K, V>{
    private final Map<K, PhantomReference<V>> cache;
    private final Function<K, V> producer = (K key) -> (V) key.toString();

    public PhantomReference<V> get(K key) {
        return cache.get(producer.apply(key));
    }

    public void put(K key) {
        cache.put(key, new PhantomReference<>(producer.apply(key) , null));
    }

    public PhantomTest() {
        this.cache = new HashMap<>();
    }
}

