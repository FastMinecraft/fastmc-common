package dev.fastmc.common

import dev.fastmc.common.collection.mapArray
import dev.fastmc.common.sort.*
import it.unimi.dsi.fastutil.bytes.Byte2IntMap
import it.unimi.dsi.fastutil.bytes.Byte2IntOpenHashMap
import it.unimi.dsi.fastutil.floats.Float2IntMap
import it.unimi.dsi.fastutil.floats.Float2IntOpenHashMap
import it.unimi.dsi.fastutil.ints.Int2IntMap
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
import org.junit.jupiter.api.Test

class FloatSortTest : SortTest() {
    @Test
    fun heapsort() {
        testSort(FloatHeapsort.INSTANCE)
    }

    @Test
    fun insertionSort() {
        testSort(FloatInsertionSort.INSTANCE, 10_000)
    }

    @Test
    fun introsort() {
        testSort(FloatIntrosort.INSTANCE)
    }

    private fun testSort(sort: FloatSort, maxSize: Int = Int.MAX_VALUE) {
        testSort(
            maxSize,
            *naturalOrders(sort),
            *reverseOrders(sort)
        )
    }

    private fun reverseOrders(sort: FloatSort): Array<(Int) -> FloatDataEntry> {
        return naturalOrders(sort).mapArray {
            { n ->
                ComparatorFloatDataEntry(sort, it(n).data, FloatComparator.REVERSE_ORDER)
            }
        }
    }

    private fun naturalOrders(sort: FloatSort): Array<(Int) -> FloatDataEntry> {
        return arrayOf(
            { n ->
                NaturalOrderFloatDataEntry(sort, FloatArrayUtil.ascending(n))
            },
            { n ->
                NaturalOrderFloatDataEntry(sort, FloatArrayUtil.descending(n))
            },
            { n ->
                NaturalOrderFloatDataEntry(sort, FloatArrayUtil.randomAscending(n))
            },
            { n ->
                NaturalOrderFloatDataEntry(sort, FloatArrayUtil.random(n))
            },
            { n ->
                NaturalOrderFloatDataEntry(sort, FloatArrayUtil.duplicated(n))
            },
            { n ->
                NaturalOrderFloatDataEntry(sort, FloatArrayUtil.closeSorted(n))
            }
        )
    }

    private class ComparatorFloatDataEntry(private val sort: FloatSort, data0: FloatArray, private val comp: FloatComparator) :
        FloatDataEntry(data0) {
        override fun fullSort(array: FloatArray) {
            sort.iSort(array, comp)
        }

        override fun partialSort(array: FloatArray, from: Int, to: Int) {
            sort.iSort(array, from, to, comp)
        }

        override fun isSorted(array: FloatArray, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (comp.compare(array[i], array[i + 1]) > 0) return false
            }
            return true
        }
    }

    private class NaturalOrderFloatDataEntry(private val sort: FloatSort, data0: FloatArray) : FloatDataEntry(data0) {
        override fun fullSort(array: FloatArray) {
            sort.iSort(array)
        }

        override fun partialSort(array: FloatArray, from: Int, to: Int) {
            sort.iSort(array, from, to)
        }

        override fun isSorted(array: FloatArray, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (array[i] > array[i + 1]) return false
            }
            return true
        }
    }

    private sealed class FloatDataEntry(private val data0: FloatArray) : DataEntry<FloatArray> {
        private val count = count(data0)
        override val data: FloatArray
            get() = data0.copyOf()

        private fun count(array: FloatArray): Float2IntMap {
            val result = Float2IntOpenHashMap()
            for (i in array.indices) {
                result.put(array[i], result.getOrDefault(array[i], 0) + 1)
            }
            return result
        }

        override fun isContentEqual(array: FloatArray): Boolean {
            val other = count(array)
            if (count.size != other.size) {
                return false
            }
            if (!count.keys.containsAll(other.keys)) {
                return false
            }
            if (!other.keys.containsAll(count.keys)) {
                return false
            }
            for (key in count.keys) {
                if (count[key] != other[key]) {
                    return false
                }
            }
            return true
        }

        override fun isEqual(array: FloatArray, from: Int, to: Int): Boolean {
            for (i in from until to) {
                if (data0[i] != array[i]) return false
            }
            return true
        }
    }
}