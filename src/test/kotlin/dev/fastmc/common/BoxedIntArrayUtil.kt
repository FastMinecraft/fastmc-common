package dev.fastmc.common

import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import it.unimi.dsi.fastutil.objects.ObjectArrays
import java.util.*
import kotlin.math.max

object BoxedIntArrayUtil {
    fun closeSorted(n: Int): Array<Int> {
        val random = Random()
        val data = Array(n) {
            (it + random.nextGaussian() * 100).toInt()
        }
        return data
    }

    fun ascending(n: Int): Array<Int> {
        return Array(n) { it }
    }

    fun descending(n: Int): Array<Int> {
        return Array(n) { n - it }
    }

    fun randomAscending(n: Int): Array<Int> {
        val random = Random()
        val data = Array(n) { it }
        ObjectArrays.shuffle(data, random)
        return data
    }

    fun random(n: Int): Array<Int> {
        val random = Random()
        return Array(n) { random.nextInt() }
    }

    fun duplicated(n: Int): Array<Int> {
        val random = Random()
        val repeatSize = max(n / 1_000, 100)
        val tempSet = IntOpenHashSet(repeatSize)
        while (tempSet.size < repeatSize) {
            tempSet.add(random.nextInt())
        }
        val tempArray = tempSet.toIntArray()
        val data = Array(n) { tempArray[random.nextInt(repeatSize)] }
        ObjectArrays.shuffle(data, random)
        return data
    }
}