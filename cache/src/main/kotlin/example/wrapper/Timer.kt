package example.wrapper

// count the living time of the object
class Timer<T> {
    private val startTime: MutableMap<T, Long?> = HashMap()
    private val totalTime: MutableMap<T, Long?> = HashMap()
    fun start(key: T) {
        startTime[key] = System.nanoTime()
    }

    fun stop(key: T) {
        val start = if (startTime[key] != null) startTime[key]!! else 0
        val end = System.nanoTime()
        val time = end - start
        val oldValue = totalTime[key] ?: 0
        totalTime[key] = time + oldValue
    }

    fun getTime(key: T): Long {
        stop(key)
        return if (totalTime[key] != null) totalTime[key]!! else 0
    }
}

