package dev.fastmc.common

import it.unimi.dsi.fastutil.bytes.ByteArrays
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet
import it.unimi.dsi.fastutil.ints.IntArrays
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import java.util.*
import kotlin.math.max
import kotlin.math.min

object ByteArrayUtil {
    fun closeSorted(n: Int): ByteArray {
        val random = Random()
        val data = ByteArray(n) {
            (it + random.nextGaussian() * 10).toInt().toByte()
        }
        return data
    }

    fun ascending(n: Int): ByteArray {
        return ByteArray(n) { it.toByte() }
    }

    fun descending(n: Int): ByteArray {
        return ByteArray(n) { (n - it).toByte() }
    }

    fun randomAscending(n: Int): ByteArray {
        val random = Random()
        val data = ByteArray(n) { it.toByte() }
        ByteArrays.shuffle(data, random)
        return data
    }

    fun random(n: Int): ByteArray {
        val random = Random()
        return ByteArray(n) { random.nextInt().toByte() }
    }

    fun duplicated(n: Int): ByteArray {
        val random = Random()
        val repeatSize = min(max(n / 1_000, 100), 256)
        val tempSet = ByteOpenHashSet(repeatSize)
        while (tempSet.size < repeatSize) {
            tempSet.add(random.nextInt().toByte())
        }
        val tempArray = tempSet.toByteArray()
        val data = ByteArray(n) { tempArray[random.nextInt(repeatSize)] }
        ByteArrays.shuffle(data, random)
        return data
    }
}