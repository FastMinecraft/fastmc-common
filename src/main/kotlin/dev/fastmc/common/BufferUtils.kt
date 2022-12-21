@file:Suppress("NOTHING_TO_INLINE")
@file:JvmName("BufferUtils")

package dev.fastmc.common

import dev.fastmc.common.collection.FastObjectArrayList
import sun.misc.Unsafe
import java.nio.*
import java.util.function.Consumer
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.math.max

inline fun allocateByte(capacity: Int): ByteBuffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder())

inline fun allocateShort(capacity: Int): ShortBuffer = allocateByte(capacity * 4).asShortBuffer()

inline fun allocateInt(capacity: Int): IntBuffer = allocateByte(capacity * 4).asIntBuffer()

inline fun allocateFloat(capacity: Int): FloatBuffer = allocateByte(capacity * 4).asFloatBuffer()

inline fun <T : Buffer> T.skip(count: Int): Buffer {
    this.position(position() + count)
    return this
}

@Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
class CachedBuffer @JvmOverloads constructor(
    initialCapacity: Int,
    val defaultSizingStrategy: SizingStrategy = SizingStrategy.AtLeastOneHalf,
    val defaultShrinkStrategy: ShrinkStrategy = ShrinkStrategy.Never
) {
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

private val DIRECT_BYTE_BUFFER_CLASS = allocateByte(0).javaClass

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