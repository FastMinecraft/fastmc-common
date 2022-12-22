@file:Suppress("NOTHING_TO_INLINE")
@file:JvmName("BufferUtils")

package dev.fastmc.common

import sun.misc.Unsafe
import java.nio.*
import java.util.function.Consumer

inline fun allocateByte(capacity: Int): ByteBuffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder())

inline fun allocateShort(capacity: Int): ShortBuffer = allocateByte(capacity * 4).asShortBuffer()

inline fun allocateInt(capacity: Int): IntBuffer = allocateByte(capacity * 4).asIntBuffer()

inline fun allocateFloat(capacity: Int): FloatBuffer = allocateByte(capacity * 4).asFloatBuffer()

inline fun <T : Buffer> T.skip(count: Int): Buffer {
    this.position(position() + count)
    return this
}

val UNSAFE = run {
    val field = Unsafe::class.java.getDeclaredField("theUnsafe")
    field.isAccessible = true
    field.get(null) as Unsafe
}

private val ADDRESS_OFFSET = UNSAFE.objectFieldOffset(Buffer::class.java.getDeclaredField("address"))
private val POSITION_OFFSET = UNSAFE.objectFieldOffset(Buffer::class.java.getDeclaredField("position"))
private val MARK_OFFSET = UNSAFE.objectFieldOffset(Buffer::class.java.getDeclaredField("mark"))
private val LIMIT_OFFSET = UNSAFE.objectFieldOffset(Buffer::class.java.getDeclaredField("limit"))
private val CAPACITY_OFFSET = UNSAFE.objectFieldOffset(Buffer::class.java.getDeclaredField("capacity"))

var ByteBuffer.address
    get() = UNSAFE.getLong(this, ADDRESS_OFFSET)
    set(value) {
        UNSAFE.putLong(this, ADDRESS_OFFSET, value)
    }

var ByteBuffer.position
    get() = position()
    set(value) {
        UNSAFE.putInt(this, POSITION_OFFSET, value)
    }

var ByteBuffer.mark
    get() = UNSAFE.getInt(this, MARK_OFFSET)
    set(value) {
        UNSAFE.putInt(this, MARK_OFFSET, value)
    }

var ByteBuffer.limit
    get() = limit()
    set(value) {
        UNSAFE.putInt(this, LIMIT_OFFSET, value)
    }

var ByteBuffer.capacity
    get() = capacity()
    set(value) {
        UNSAFE.putInt(this, CAPACITY_OFFSET, value)
    }

val DIRECT_BYTE_BUFFER_CLASS = allocateByte(0).javaClass

fun wrapDirectByteBuffer(address: Long, capacity: Int): ByteBuffer {
    val buffer = UNSAFE.allocateInstance(DIRECT_BYTE_BUFFER_CLASS) as ByteBuffer

    buffer.address = address
    buffer.mark = -1
    buffer.limit = capacity
    buffer.capacity = capacity

    return buffer
}

private val FREE_FUNC = run {
    try {
        val invokeCleanerMethod = UNSAFE.javaClass.getDeclaredMethod("invokeCleaner", ByteBuffer::class.java)
        Consumer<ByteBuffer> {
            invokeCleanerMethod.invoke(UNSAFE, it)
        }
    } catch (e: NoSuchMethodException) {
        val cleanerField = DIRECT_BYTE_BUFFER_CLASS.getDeclaredField("cleaner")
        val cleanerOffset = UNSAFE.objectFieldOffset(cleanerField)
        val cleanMethod = cleanerField.type.getDeclaredMethod("clean")
        cleanMethod.isAccessible = true
        Consumer<ByteBuffer> { buffer ->
            UNSAFE.getObject(buffer, cleanerOffset)?.let {
                cleanMethod.invoke(it)
            }
        }
    }
}

fun ByteBuffer.free() {
    FREE_FUNC.accept(this)
}

