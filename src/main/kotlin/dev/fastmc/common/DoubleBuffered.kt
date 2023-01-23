package dev.fastmc.common

import java.util.function.Consumer

class DoubleBuffered<T>(provider: () -> T, private val initAction: Consumer<in T>) {
    @Suppress("UNCHECKED_CAST")
    constructor(provider: () -> T) : this(provider, DEFAULT_INIT_ACTION as Consumer<T>)

    @Volatile
    var front = provider(); private set

    @Volatile
    var back = provider(); private set

    fun swap(): DoubleBuffered<T> {
        val temp = front
        front = back
        back = temp
        return this
    }

    fun initFrontBack(): DoubleBuffered<T> {
        initAction.accept(front)
        initAction.accept(back)
        return this
    }

    fun initFront(): DoubleBuffered<T> {
        initAction.accept(front)
        return this
    }

    fun initBack(): DoubleBuffered<T> {
        initAction.accept(back)
        return this
    }

    companion object {
        @JvmField
        val DEFAULT_INIT_ACTION = Consumer<Any?> {

        }

        @JvmField
        val CLEAR_INIT_ACTION = Consumer<MutableCollection<*>> {
            it.clear()
        }
    }
}