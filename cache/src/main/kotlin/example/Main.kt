package example

import example.graph.GraphBuilder
import example.wrapper.CacheWrapper
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

const val medium = 1000
const val cacheSize = medium * 2
const val shift = 300
const val requests = 100000000
val random = Random()


fun getRandomKey(): Int {
    val randomKey = (random.nextGaussian() * shift + medium).toInt()
    if (randomKey < 0 || randomKey >= cacheSize) {
        return getRandomKey()
    }
    return randomKey
}

const val type: String = "PhantomReference"
val memory = Runtime.getRuntime().maxMemory() / (1024 * 1024)

val chartsPath: Path = Paths.get("charts/${type}")
val producer = { key: Int -> key.toString() }

fun main() {
    val cacheLifetime = CacheWrapper(producer)
    for (i in 0 until requests) {
        cacheLifetime[getRandomKey()]
    }

    val chart = GraphBuilder("$type $memory lifetime", "Values", "Lifetime")
    for (value in 0 until cacheSize) {
        chart.addData(value.toDouble(), cacheLifetime.getLifetime(value).toDouble())
    }
    chart.saveChart(Paths.get(chartsPath.toString(), "${type}_${memory}_lifetime.jpg"))

    val chartProbability = GraphBuilder("$type $memory probability", "Values", "Probability")
    for (value in 0 until cacheSize) {
        chartProbability.addData(value.toDouble(), cacheLifetime.getProbability(value))
    }
    chartProbability.saveChart(Paths.get(chartsPath.toString(), "${type}_${memory}_probability.jpg"))
}