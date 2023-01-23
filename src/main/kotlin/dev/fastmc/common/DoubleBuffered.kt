package dev.fastmc.common

import java.util.function.Consumer

class DoubleBuffered<T>(provider: () -> T, private val initAction: Consumer<in T>) {
    @Suppress("UNCHECKED_CAST")
    constructor(provider: () -> T) : this(provider, DEFAULT_INIT_ACTION as Consumer<T>)

    @Volatile
    var front = provider(); private set

    @Volatile
    var back = provider(); private set

    fun swap() {
        val temp = front
        front = back
        back = temp
    }

    fun initFront() {
        initAction.accept(front)
    }

    fun initBack() {
        initAction.accept(back)
    }

    companion object {
        val DEFAULT_INIT_ACTION = Consumer<Any?> {

        }

        val CLEAR_INIT_ACTION = Consumer<MutableCollection<*>> {
            it.clear()
        }
    }
}