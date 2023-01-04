package dev.fastmc.common

import it.unimi.dsi.fastutil.floats.FloatArrays
import it.unimi.dsi.fastutil.floats.FloatOpenHashSet
import java.util.*
import kotlin.math.max

object FloatArrayUtil {
    fun closeSorted(n: Int): FloatArray {
        val random = Random()
        val data = FloatArray(n) {
            (it + random.nextGaussian() * 100).toFloat()
        }
        return data
    }

    fun ascending(n: Int): FloatArray {
        return FloatArray(n) { it.toFloat() }
    }

    fun descending(n: Int): FloatArray {
        return FloatArray(n) { (n - it).toFloat() }
    }

    fun randomAscending(n: Int): FloatArray {
        val data = ascending(n)
        FloatArrays.shuffle(data, Random())
        return data
    }

    fun random(n: Int): FloatArray {
        val random = Random()
        return FloatArray(n) { random.nextFloat() }
    }

    fun duplicated(n: Int): FloatArray {
        val random = Random()
        val repeatSize = max(n / 1_000, 100)
        val tempSet = FloatOpenHashSet(repeatSize)
        while (tempSet.size < repeatSize) {
            tempSet.add(random.nextFloat())
        }
        val tempArray = tempSet.toFloatArray()
        val data = FloatArray(n) { tempArray[random.nextInt(repeatSize)] }
        FloatArrays.shuffle(data, random)
        return data
    }
}