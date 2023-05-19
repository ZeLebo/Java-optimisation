package example

import example.graph.GraphBuilder
import example.wrapper.CacheWrapper
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Random

const val medium = 1000
const val cacheSize = medium * 2
const val shift = 300
const val requests = 100000000
val random = Random()

val chartsPath: Path = Paths.get("charts")
val producer = { key: Int -> key.toString() }

fun getRandomKey(): Int {
    val randomKey = (random.nextGaussian() * shift + medium).toInt()
    if (randomKey < 0 || randomKey >= cacheSize) {
        return getRandomKey()
    }
    return randomKey
}

fun main() {
    val cache = CacheWrapper(producer)
    for (i in 0 until requests) {
        cache[getRandomKey()]
    }
    val chart = GraphBuilder("WeakReferenceCache cache lifetime", "Values", "Lifetime")
    for (value in 0 until cacheSize) {
        chart.addData(value.toDouble(), cache.getLifetime(value).toDouble())
    }
    chart.saveChart(Paths.get(chartsPath.toString(), "WeakReferenceCache_CacheLifetime_MAIN_Graph.jpg"))

    val cache2 = CacheWrapper(producer)
    for (i in 0 until requests) {
        cache[getRandomKey()]
        cache.get(getRandomKey())
    }
}