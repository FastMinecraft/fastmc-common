package dev.fastmc.common

import it.unimi.dsi.fastutil.ints.IntArrays
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import java.util.*
import kotlin.math.max

object IntArrayUtil {
    fun closeSorted(n: Int): IntArray {
        val random = Random()
        val data = IntArray(n) {
            (it + random.nextGaussian() * 100).toInt()
        }
        return data
    }

    fun ascending(n: Int): IntArray {
        return IntArray(n) { it }
    }

    fun descending(n: Int): IntArray {
        return IntArray(n) { n - it }
    }

    fun randomAscending(n: Int): IntArray {
        val random = Random()
        val data = IntArray(n) { it }
        IntArrays.shuffle(data, random)
        return data
    }

    fun random(n: Int): IntArray {
        val random = Random()
        return IntArray(n) { random.nextInt() }
    }

    fun duplicated(n: Int): IntArray {
        val random = Random()
        val repeatSize = max(n / 1_000, 100)
        val tempSet = IntOpenHashSet(repeatSize)
        while (tempSet.size < repeatSize) {
            tempSet.add(random.nextInt())
        }
        val tempArray = tempSet.toIntArray()
        val data = IntArray(n) { tempArray[random.nextInt(repeatSize)] }
        IntArrays.shuffle(data, random)
        return data
    }
}