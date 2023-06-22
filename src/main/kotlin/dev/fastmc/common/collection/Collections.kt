package dev.fastmc.common.collection

import it.unimi.dsi.fastutil.ints.Int2ByteMap
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ByteMap

inline fun <T, reified R> Array<T>.mapArray(transform: (T) -> R): Array<R> {
    return Array(this.size) {
        transform.invoke(this[it])
    }
}

operator fun Int2ByteMap.set(key: Int, value: Byte) {
    this.put(key, value)
}

operator fun <T> Int2ObjectMap<T>.set(key: Int, value: T) {
    this.put(key, value)
}

operator fun Long2ByteMap.set(key: Long, value: Byte) {
    this.put(key, value)
}