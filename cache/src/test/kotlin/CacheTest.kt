import example.graph.GraphBuilder
import example.wrapper.CacheWrapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

const val MEAN_VALUE = 1000
const val STD_VALUE = 300
const val CACHE_SIZE = 2 * MEAN_VALUE
const val REQUESTS_NUMBER = 100000000

val chartsPath: Path = Paths.get("charts")

val random = Random()
val producer = { key: Int -> key.toString() }

internal class CacheTest {
    private var cache: CacheWrapper<Int, String> = CacheWrapper(producer)
    private val randomKey: Int
        get() {
            val randomKey = (random.nextGaussian() * STD_VALUE + MEAN_VALUE).toInt()
            return if (randomKey < 0 || randomKey >= CACHE_SIZE) this.randomKey else randomKey
        }

    @BeforeEach
    fun init() {
        cache = CacheWrapper(producer)
    }

    @Test
    fun get_ReturnsValuesFromCache_ForRandomKeys() {
        for (i in 0 until REQUESTS_NUMBER) {
            cache[randomKey]
        }
        val chart = GraphBuilder("WeakReferenceCache hits distribution", "Values", "Returns from cache")
        for (value in 0 until CACHE_SIZE) {
            chart.addData(value.toDouble(), cache.getProbability(value))
        }
        chart.saveChart(Paths.get(chartsPath.toString(), "WeakReferenceCache.jpg"))
    }

    @Test
    fun get_ClearsCache_ForRandomKeys() {
        for (i in 0 until REQUESTS_NUMBER) {
            cache[randomKey]
        }
        val chart = GraphBuilder("WeakReferenceCache cache lifetime", "Values", "Lifetime")
        for (value in 0 until CACHE_SIZE) {
            chart.addData(value.toDouble(), cache.getLifetime(value).toDouble())
        }
        chart.saveChart(Paths.get(chartsPath.toString(), "WeakReferenceCache_CacheLifetime.jpg"))
    }
}