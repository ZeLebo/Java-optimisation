package example.wrapper

import example.entity.Cache
import java.util.function.Function

/*
 * Wrapper class for JavaCache that counts the lifetime and times to access the cache
 * */
class CacheWrapper<K, V>(producer: Function<K, V>?) : Cache<K, V>(producer) {
    private val cacheTimer = Timer<K>()
    private val putCounter = Counter<K>()
    private val getCounter = Counter<K>()

    // get the probability of the key being accessed
    fun getProbability(key: K): Double {
        val total = getCounter[key].toDouble()
        // how many get to how many was put in cache
        return if (total == 0.0) 0.0 else 1 - (putCounter[key] / total)
    }

    // get the lifetime of the key in the cache (in nanoseconds)
    fun getLifetime(key: K): Long {
        return cacheTimer.getTime(key) / if (putCounter[key] != 0) putCounter[key] else 1
    }

    public override fun remove(key: K) {
        cacheTimer.stop(key)
        super.remove(key)
    }

    // put only when object in memory is null
    public override fun put(key: K): V {
        putCounter.inc(key)
        cacheTimer.start(key)
        return super.put(key)
    }

    public override fun get(key: K): V {
        getCounter.inc(key)
        return super.get(key)
    }
}