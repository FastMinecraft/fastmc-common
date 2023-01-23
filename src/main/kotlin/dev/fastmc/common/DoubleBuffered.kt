package dev.fastmc.common

import java.util.function.Consumer

class DoubleBuffered<T>(front: T, back: T, private val initAction: Consumer<T>) {
    @Suppress("UNCHECKED_CAST")
    constructor(front: T, swap: T) : this(front, swap, DEFAULT_INIT_ACTION as Consumer<T>)

    @Volatile
    var front = front; private set

    @Volatile
    var back = back; private set

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

    private companion object {
        val DEFAULT_INIT_ACTION = Consumer<Any?> {

        }

        val CLEAR_INIT_ACTION = Consumer<MutableCollection<*>> {
            it.clear()
        }
    }
}