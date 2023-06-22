package dev.fastmc.common

import kotlinx.coroutines.CancellationException
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ArrayPriorityObjectPool<K : Cancellable, E>(
    capacity: Int,
    comparator: Comparator<K>? = null,
) : AbstractPriorityObjectPool<K, E>(comparator) {
    constructor(capacity: Int, comparator: Comparator<K>? = null, init: (ArrayPriorityObjectPool<K, E>) -> E) : this(
        capacity,
        comparator
    ) {
        repeat(capacity) {
            put(init.invoke(this))
        }
    }

    private val queue = ConcurrentLinkedQueue<QueueEntry>()
    private val array = arrayOfNulls<Any>(capacity)

    @Volatile
    private var index = 0

    override suspend fun get(key: K): E {
        var result: E? = null

        if (index != 0) {
            synchronized(array) {
                if (index != 0) {
                    @Suppress("UNCHECKED_CAST")
                    result = array[--index] as E
                }
            }
        }

        if (result == null) {
            result = suspendCoroutine<E> {
                val queueEntry = QueueEntry(key, it)
                queue.add(queueEntry)
            }
        }

        return result!!
    }

    override fun put(element: E) {
        if (queue.isNotEmpty()) {
            synchronized(queue) {
                if (queue.isNotEmpty()) {
                    var queueEntry: QueueEntry? = null
                    queue.removeIf {
                        if (it.value.isCancelled) {
                            it.continuation.resumeWithException(CancellationException())
                            true
                        } else {
                            if (queueEntry == null || compare(it.value, queueEntry!!.value) > 0) {
                                queueEntry = it
                            }
                            false
                        }
                    }

                    if (queueEntry != null) {
                        queue.remove(queueEntry)
                        queueEntry!!.continuation.resume(element)
                        return
                    }
                }
            }
        }

        synchronized(array) {
            array[index++] = element
        }
    }

    private inner class QueueEntry(val value: K, val continuation: Continuation<E>)
}

abstract class AbstractPriorityObjectPool<K : Cancellable, E>(private val comparator: Comparator<K>?) {
    abstract suspend fun get(key: K): E

    abstract fun put(element: E)

    @Suppress("UNCHECKED_CAST")
    protected fun compare(o1: K, o2: K): Int {
        return comparator?.compare(o1, o2) ?: (o1 as Comparable<K>).compareTo(o2)
    }
}

interface SuspendObjectPool<T> {
    suspend fun get(): T
    fun tryGet(): T?
    fun put(element: T)
}