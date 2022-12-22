package dev.fastmc.common

import java.nio.*
import kotlin.math.max

@Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
class CachedBuffer(
    initialCapacity: Int,
    val defaultSizingStrategy: SizingStrategy,
    val defaultShrinkStrategy: ShrinkStrategy
) {
    constructor(initialCapacity: Int) : this(initialCapacity, SizingStrategy.AtLeastOneHalf, ShrinkStrategy.Never)

    @Volatile
    private var byteBuffer = allocateByte(initialCapacity)

    @Volatile
    private var charBuffer: CharBuffer? = null

    @Volatile
    private var shortBuffer: ShortBuffer? = null

    @Volatile
    private var intBuffer: IntBuffer? = null

    @Volatile
    private var floatBuffer: FloatBuffer? = null

    private fun allocate(capacity: Int) {
        byteBuffer = allocateByte(capacity)
        shortBuffer = null
        floatBuffer = null
        intBuffer = null
    }

    private fun reallocate(capacity: Int) {
        byteBuffer.flip()
        val newBuffer = allocateByte(capacity)
        newBuffer.put(byteBuffer)

        byteBuffer = newBuffer
        shortBuffer = null
        floatBuffer = null
        intBuffer = null
    }

    fun getByte(): ByteBuffer {
        return byteBuffer
    }

    fun getEnsureCapacityByte(require: Int): ByteBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            require
        )
        if (newCapacity != byteBuffer.capacity()) {
            allocate(newCapacity)
        }
        return getByte()
    }

    fun ensureCapacityByte(require: Int): ByteBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            require
        )
        if (newCapacity != byteBuffer.capacity()) {
           reallocate(newCapacity)
        }
        return getByte()
    }

    fun ensureRemainingByte(require: Int): ByteBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            byteBuffer.position() + require
        )
        if (newCapacity != byteBuffer.capacity()) {
           reallocate(newCapacity)
        }
        return getByte()
    }

    @Deprecated("Use getEnsureCapacityByte instead")
    fun getWithCapacityByte(minCapacity: Int, newCapacity: Int): ByteBuffer {
        if (byteBuffer.capacity() < minCapacity) {
            allocate(newCapacity)
        }
        byteBuffer.clear()
        return getByte()
    }

    @Deprecated("Use getEnsureCapacityByte instead")
    fun getWithCapacityByte(minCapacity: Int, newCapacity: Int, maxCapacity: Int): ByteBuffer {
        if (byteBuffer.capacity() < minCapacity || byteBuffer.capacity() > maxCapacity) {
            allocate(newCapacity)
        }
        byteBuffer.clear()
        return getByte()
    }

    @Deprecated("Use ensureCapacityByte instead")
    fun ensureCapacityByte(minCapacity: Int, newCapacity: Int): ByteBuffer {
        if (byteBuffer.capacity() < minCapacity) {
            reallocate(newCapacity)
        }
        return getByte()
    }

    @Deprecated("Use ensureCapacityByte instead")
    fun ensureCapacityByte(minCapacity: Int, newCapacity: Int, maxCapacity: Int): ByteBuffer {
        if (byteBuffer.capacity() !in minCapacity..maxCapacity) {
            reallocate(newCapacity)
        }
        return getByte()
    }


    fun getChar(): CharBuffer {
        var buffer = charBuffer
        if (buffer == null) {
            buffer = this.byteBuffer.asCharBuffer()!!
            charBuffer = buffer
        }
        return buffer
    }

    fun getEnsureCapacityChar(require: Int): CharBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            require * 2
        )
        if (newCapacity != byteBuffer.capacity()) {
            allocate(newCapacity)
        }
        return getChar()
    }

    fun ensureCapacityChar(require: Int): CharBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            require * 2
        )
        if (newCapacity != byteBuffer.capacity()) {
            reallocate(newCapacity)
        }
        return getChar()
    }

    fun ensureRemainingChar(require: Int): CharBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            byteBuffer.position() + require * 2
        )
        if (newCapacity != byteBuffer.capacity()) {
            reallocate(newCapacity)
        }
        return getChar()
    }

    @Deprecated("Use getEnsureCapacityChar instead")
    fun getWithCapacityChar(minCapacity: Int, newCapacity: Int): CharBuffer {
        if (byteBuffer.capacity() < minCapacity * 2) {
            allocate(newCapacity * 2)
        }
        byteBuffer.clear()
        return getChar()
    }

    @Deprecated("Use getEnsureCapacityChar instead")
    fun getWithCapacityChar(minCapacity: Int, newCapacity: Int, maxCapacity: Int): CharBuffer {
        if (byteBuffer.capacity() < minCapacity * 2 || byteBuffer.capacity() > maxCapacity * 2) {
            allocate(newCapacity * 2)
        }
        byteBuffer.clear()
        return getChar()
    }

    @Deprecated("Use ensureCapacityChar instead")
    fun ensureCapacityChar(minCapacity: Int, newCapacity: Int): CharBuffer {
        if (byteBuffer.capacity() < minCapacity * 2) {
            reallocate(newCapacity * 2)
        }
        return getChar()
    }

    @Deprecated("Use ensureCapacityChar instead")
    fun ensureCapacityChar(minCapacity: Int, newCapacity: Int, maxCapacity: Int): CharBuffer {
        if (byteBuffer.capacity() < minCapacity * 2 || byteBuffer.capacity() > maxCapacity * 2) {
            reallocate(newCapacity * 2)
        }
        return getChar()
    }


    fun getShort(): ShortBuffer {
        var buffer = shortBuffer
        if (buffer == null) {
            buffer = this.byteBuffer.asShortBuffer()!!
            shortBuffer = buffer
        }
        return buffer
    }

    fun getEnsureCapacityShort(require: Int): ShortBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            require * 2
        )
        if (newCapacity != byteBuffer.capacity()) {
            allocate(newCapacity)
        }
        return getShort()
    }

    fun ensureCapacityShort(require: Int): ShortBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            require * 2
        )
        if (newCapacity != byteBuffer.capacity()) {
            reallocate(newCapacity)
        }
        return getShort()
    }

    fun ensureRemainingShort(require: Int): ShortBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            byteBuffer.position() + require * 2
        )
        if (newCapacity != byteBuffer.capacity()) {
            reallocate(newCapacity)
        }
        return getShort()
    }

    @Deprecated("Use getEnsureCapacityShort instead")
    fun getWithCapacityShort(minCapacity: Int, newCapacity: Int): ShortBuffer {
        if (byteBuffer.capacity() < minCapacity * 2) {
            allocate(newCapacity * 2)
        }
        byteBuffer.clear()
        return getShort()
    }

    @Deprecated("Use getEnsureCapacityShort instead")
    fun getWithCapacityShort(minCapacity: Int, newCapacity: Int, maxCapacity: Int): ShortBuffer {
        if (byteBuffer.capacity() < minCapacity * 2 || byteBuffer.capacity() > maxCapacity * 2) {
            allocate(newCapacity * 2)
        }
        byteBuffer.clear()
        return getShort()
    }

    @Deprecated("Use ensureCapacityShort instead")
    fun ensureCapacityShort(minCapacity: Int, newCapacity: Int): ShortBuffer {
        if (byteBuffer.capacity() < minCapacity * 2) {
            reallocate(newCapacity * 2)
        }
        return getShort()
    }

    @Deprecated("Use ensureCapacityShort instead")
    fun ensureCapacityShort(minCapacity: Int, newCapacity: Int, maxCapacity: Int): ShortBuffer {
        if (byteBuffer.capacity() < minCapacity * 2 || byteBuffer.capacity() > maxCapacity * 2) {
            reallocate(newCapacity * 2)
        }
        return getShort()
    }


    fun getInt(): IntBuffer {
        var buffer = intBuffer
        if (buffer == null) {
            buffer = this.byteBuffer.asIntBuffer()!!
            intBuffer = buffer
        }
        return buffer
    }

    fun getEnsureCapacityInt(require: Int): IntBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            require * 4
        )
        if (newCapacity != byteBuffer.capacity()) {
            allocate(newCapacity)
        }
        return getInt()
    }

    fun ensureCapacityInt(require: Int): IntBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            require * 4
        )
        if (newCapacity != byteBuffer.capacity()) {
            reallocate(newCapacity)
        }
        return getInt()
    }

    fun ensureRemainingInt(require: Int): IntBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            byteBuffer.position() + require * 4
        )
        if (newCapacity != byteBuffer.capacity()) {
            reallocate(newCapacity)
        }
        return getInt()
    }

    @Deprecated("Use getEnsureCapacityInt instead")
    fun getWithCapacityInt(minCapacity: Int, newCapacity: Int): IntBuffer {
        if (byteBuffer.capacity() < minCapacity * 4) {
            allocate(newCapacity * 4)
        }
        byteBuffer.clear()
        return getInt()
    }

    @Deprecated("Use getEnsureCapacityInt instead")
    fun getWithCapacityInt(minCapacity: Int, newCapacity: Int, maxCapacity: Int): IntBuffer {
        if (byteBuffer.capacity() < minCapacity * 4 || byteBuffer.capacity() > maxCapacity * 4) {
            allocate(newCapacity * 4)
        }
        byteBuffer.clear()
        return getInt()
    }

    @Deprecated("Use ensureCapacityInt instead")
    fun ensureCapacityInt(minCapacity: Int, newCapacity: Int): IntBuffer {
        if (byteBuffer.capacity() < minCapacity * 4) {
            reallocate(newCapacity * 4)
        }
        return getInt()
    }

    @Deprecated("Use ensureCapacityInt instead")
    fun ensureCapacityInt(minCapacity: Int, newCapacity: Int, maxCapacity: Int): IntBuffer {
        if (byteBuffer.capacity() < minCapacity * 4 || byteBuffer.capacity() > maxCapacity * 4) {
            reallocate(newCapacity * 4)
        }
        return getInt()
    }


    fun getFloat(): FloatBuffer {
        var buffer = floatBuffer
        if (buffer == null) {
            buffer = this.byteBuffer.asFloatBuffer()!!
            floatBuffer = buffer
        }
        return buffer
    }

    fun getEnsureCapacityFloat(require: Int): FloatBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            require * 4
        )
        if (newCapacity != byteBuffer.capacity()) {
            allocate(newCapacity)
        }
        return getFloat()
    }

    fun ensureCapacityFloat(require: Int): FloatBuffer {
        val newCapacity = computeNewCapacity(
            defaultSizingStrategy,
            defaultShrinkStrategy,
            require * 4
        )
        if (newCapacity != byteBuffer.capacity()) {
            reallocate(newCapacity)
        }
        return getFloat()
    }

    @Deprecated("Use getEnsureCapacityFloat instead")
    fun getWithCapacityFloat(minCapacity: Int, newCapacity: Int): FloatBuffer {
        if (byteBuffer.capacity() < minCapacity * 4) {
            allocate(newCapacity * 4)
        }
        byteBuffer.clear()
        return getFloat()
    }

    @Deprecated("Use getEnsureCapacityFloat instead")
    fun getWithCapacityFloat(minCapacity: Int, newCapacity: Int, maxCapacity: Int): FloatBuffer {
        if (byteBuffer.capacity() < minCapacity * 4 || byteBuffer.capacity() > maxCapacity * 4) {
            allocate(newCapacity * 4)
        }
        byteBuffer.clear()
        return getFloat()
    }

    @Deprecated("Use ensureCapacityFloat instead")
    fun ensureCapacityFloat(minCapacity: Int, newCapacity: Int): FloatBuffer {
        if (byteBuffer.capacity() < minCapacity * 4) {
            reallocate(newCapacity * 4)
        }
        return getFloat()
    }

    @Deprecated("Use ensureCapacityFloat instead")
    fun ensureCapacityFloat(minCapacity: Int, newCapacity: Int, maxCapacity: Int): FloatBuffer {
        if (byteBuffer.capacity() < minCapacity * 4 || byteBuffer.capacity() > maxCapacity * 4) {
            reallocate(newCapacity * 4)
        }
        return getFloat()
    }

    fun free() {
        byteBuffer.free()
    }

    private fun computeNewCapacity(
        sizingStrategy: SizingStrategy,
        shrinkStrategy: ShrinkStrategy,
        targetBytes: Int
    ): Int {
        val current = byteBuffer.capacity()
        return if (targetBytes > current || shrinkStrategy.shouldShrink(current, targetBytes)) {
            sizingStrategy.newCapacity(current, targetBytes)
        } else {
            current
        }
    }

    sealed interface SizingStrategy {
        fun newCapacity(current: Int, target: Int): Int

        object AtLeastOneHalf : SizingStrategy {
            override fun newCapacity(current: Int, target: Int): Int {
                return max(current + (current shr 1), target)
            }
        }

        object AtLeastDouble : SizingStrategy {
            override fun newCapacity(current: Int, target: Int): Int {
                return max(current * 2, target)
            }
        }

        class Constant(private val value: Int) : SizingStrategy {
            override fun newCapacity(current: Int, target: Int): Int {
                return max(current + value, target)
            }
        }

        object PowerOfTwo : SizingStrategy {
            override fun newCapacity(current: Int, target: Int): Int {
                return MathUtils.ceilToPOT(target)
            }
        }
    }

    interface ShrinkStrategy {
        fun shouldShrink(current: Int, target: Int): Boolean

        object Never : ShrinkStrategy {
            override fun shouldShrink(current: Int, target: Int): Boolean {
                return false
            }
        }

        object HalfEmpty : ShrinkStrategy {
            override fun shouldShrink(current: Int, target: Int): Boolean {
                return current - target > current / 2
            }
        }

        object QuarterEmpty : ShrinkStrategy {
            override fun shouldShrink(current: Int, target: Int): Boolean {
                return current - target > current / 4
            }
        }
    }
}