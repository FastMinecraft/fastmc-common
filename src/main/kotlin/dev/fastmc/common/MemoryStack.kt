package dev.fastmc.common

import dev.fastmc.common.collection.FastObjectArrayList
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class MemoryStack private constructor(initCapacity: Int) {
    private var rawBuffer = allocateByte(initCapacity)
    private val objectPool = ObjectPool {
        (UNSAFE.allocateInstance(DIRECT_BYTE_BUFFER_CLASS) as ByteBuffer).order(ByteOrder.nativeOrder())
    }

    private var frameIndex = 0
    private var frameSize = 8
    private var frameBufferReference = arrayOfNulls<ByteBuffer>(frameSize)
    private var framePointers = IntArray(frameSize)
    private var frameBufferObjects = Array<FastObjectArrayList<ByteBuffer>>(frameSize) { FastObjectArrayList() }
    private var pointer = 0

    fun push(): MemoryStack {
        if (frameIndex == frameSize) {
            frameSize *= 2
            frameBufferReference = frameBufferReference.copyOf(frameSize)
            framePointers = framePointers.copyOf(frameSize)
            @Suppress("UNCHECKED_CAST")

            frameBufferObjects = frameBufferObjects.copyOf(frameSize).apply {
                for (i in frameBufferObjects.size until frameSize) {
                    this[i] = FastObjectArrayList()
                }
            } as Array<FastObjectArrayList<ByteBuffer>>
        }

        frameBufferReference[frameIndex] = rawBuffer
        framePointers[frameIndex] = pointer
        frameIndex++
        return this
    }

    fun pop(): MemoryStack {
        --frameIndex
        frameBufferReference[frameIndex] = null
        pointer = framePointers[frameIndex]

        val objects = frameBufferObjects[frameIndex]
        for (i in objects.indices) {
            objectPool.put(objects[i])
        }
        objects.clear()

        return this
    }

    fun freeBuffer0(buffer: ByteBuffer) {
        objectPool.put(buffer)
    }

    fun malloc0(size: Int): ByteBuffer {
        val newPointer = pointer + size
        if (newPointer > rawBuffer.capacity()) {
            rawBuffer = allocateByte(MathUtils.ceilToPOT(newPointer))
        }

        return objectPool.get().apply {
            address = rawBuffer.address + pointer
            pointer = newPointer

            position = 0
            mark = -1
            limit = size
            capacity = size
        }
    }

    fun calloc0(size: Int): ByteBuffer {
        return malloc0(size).apply {
            UNSAFE.setMemory(address, size.toLong(), 0)
        }
    }

    fun malloc(size: Int): ByteBuffer {
        return malloc0(size).apply {
            frameBufferObjects[frameIndex - 1].add(this)
        }
    }

    fun calloc(size: Int): ByteBuffer {
        return calloc0(size).apply {
            frameBufferObjects[frameIndex - 1].add(this)
        }
    }

    inline fun <T> withMalloc(size: Int, crossinline block: (ByteBuffer) -> T): T {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }

        val buffer = malloc0(size)
        try {
            return block(buffer)
        } finally {
            freeBuffer0(buffer)
        }
    }

    inline fun <T> withCalloc(size: Int, crossinline block: (ByteBuffer) -> T): T {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }

        val buffer = calloc0(size)

        try {
            return block(buffer)
        } finally {
            freeBuffer0(buffer)
        }
    }

    inline fun <T> use(crossinline block: MemoryStack.() -> T): T {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }

        push()
        try {
            return block.invoke(this)
        } finally {
            pop()
        }
    }

    companion object {
        private val threadLocal = ThreadLocal.withInitial { MemoryStack(1024 * 1024) }

        fun get(): MemoryStack {
            return threadLocal.get()
        }

        inline fun <T> use(crossinline block: MemoryStack.() -> T): T {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }
            return get().use(block)
        }
    }
}