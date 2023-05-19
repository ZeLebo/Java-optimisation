package example.wrapper

internal class Counter<T> {
    private var invokes: MutableMap<T, Int> = HashMap()

    fun inc(key: T) {
        invokes.merge(key, 1) { a: Int?, b: Int? ->
            Integer.sum(
                a!!, b!!
            )
        }
    }

    operator fun get(key: T): Int {
        // return key or 0 by default
        return invokes.getOrDefault(key, 0)
    }
}