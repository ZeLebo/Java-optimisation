package example.entity;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Cache<K, V> {
    private final Function<K, V> producer;

    public Cache(Function<K, V> producer) {
        this.producer = producer;
        Thread cleaner = new ReferenceCleaner();
        cleaner.start();
    }

    // what for?
    private final ReferenceQueue<V> re = new ReferenceQueue<>();
    private final Map<K, CacheReference> cache = new ConcurrentHashMap<>(new HashMap<K, CacheReference>());

    private class CacheReference extends PhantomReference<V> {
        // to get the object back from the reference
        private final K key;

        public CacheReference(K key, V value) {
            super(value, Cache.this.re);
            this.key = key;
        }
    }

    // clean the thread
    private class ReferenceCleaner extends Thread {
        public ReferenceCleaner() {
            super("ReferenceCleaner");
            setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    CacheReference ref = (CacheReference) re.remove();
                    Cache.this.cache.remove(ref.key);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    protected void remove(K key) {
        this.cache.remove(key);
    }

    // make new object in memory with some reference
    protected V put(K key) {
        V value = this.producer.apply(key);
        cache.put(key, new CacheReference(key, value));
        return value;
    }

    // get the object from cache
    protected V get(K key) {
        CacheReference ref = cache.get(key);
        if (ref != null) {
            V value = ref.get();
            if (value != null) {
                return value;
            }
        }
        // if not found, let's add it
        return put(key);
    }
}
