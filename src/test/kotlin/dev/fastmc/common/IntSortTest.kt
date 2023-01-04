package dev.fastmc.common

import dev.fastmc.common.collection.mapArray
import dev.fastmc.common.sort.*
import it.unimi.dsi.fastutil.ints.Int2IntMap
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
import org.junit.jupiter.api.Test

class IntSortTest : SortTest() {
    @Test
    fun heapsort() {
        testSort(IntHeapsort.INSTANCE)
    }

    @Test
    fun insertionSort() {
        testSort(IntInsertionSort.INSTANCE, 10_000)
    }

    @Test
    fun introsort() {
        testSort(IntIntrosort.INSTANCE)
    }

    private fun testSort(sort: IntSort, maxSize: Int = Int.MAX_VALUE) {
        testSort(
            maxSize,
            *naturalOrders(sort),
            *reverseOrders(sort),
            *intKeys(sort),
            *floatKeys(sort)
        )
    }

    private fun floatKeys(sort: IntSort): Array<(Int) -> IntDataEntry> {
        return arrayOf(
            { n ->
                val keys = floatArrayOf(0.0f)
                val array = IntArray(n)
                FloatKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArrayUtil.ascending(n)
                val array = IntArrayUtil.ascending(n)
                FloatKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArrayUtil.randomAscending(n)
                val array = IntArrayUtil.randomAscending(n)
                FloatKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArray(n)
                val array = IntArrayUtil.randomAscending(n)
                FloatKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArrayUtil.random(n)
                val array = IntArrayUtil.randomAscending(n)
                FloatKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArrayUtil.closeSorted(n)
                val array = IntArrayUtil.randomAscending(n)
                FloatKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArrayUtil.duplicated(n)
                val array = IntArrayUtil.randomAscending(n)
                FloatKeyIntDataEntry(sort, keys, array)
            },
        )
    }

    private fun intKeys(sort: IntSort): Array<(Int) -> IntDataEntry> {
        return arrayOf(
            { n ->
                val keys = intArrayOf(0)
                val array = IntArray(n)
                IntKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArrayUtil.ascending(n)
                val array = IntArrayUtil.ascending(n)
                IntKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArrayUtil.randomAscending(n)
                val array = IntArrayUtil.randomAscending(n)
                IntKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArray(n)
                val array = IntArrayUtil.randomAscending(n)
                IntKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArrayUtil.random(n)
                val array = IntArrayUtil.randomAscending(n)
                IntKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArrayUtil.closeSorted(n)
                val array = IntArrayUtil.randomAscending(n)
                IntKeyIntDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArrayUtil.duplicated(n)
                val array = IntArrayUtil.randomAscending(n)
                IntKeyIntDataEntry(sort, keys, array)
            },
        )
    }

    private fun reverseOrders(sort: IntSort): Array<(Int) -> IntDataEntry> {
        return naturalOrders(sort).mapArray {
            { n ->
                ComparatorIntDataEntry(sort, it(n).data, IntComparator.REVERSE_ORDER)
            }
        }
    }

    private fun naturalOrders(sort: IntSort): Array<(Int) -> IntDataEntry> {
        return arrayOf(
            { n ->
                NaturalOrderIntDataEntry(sort, IntArrayUtil.ascending(n))
            },
            { n ->
                NaturalOrderIntDataEntry(sort, IntArrayUtil.descending(n))
            },
            { n ->
                NaturalOrderIntDataEntry(sort, IntArrayUtil.randomAscending(n))
            },
            { n ->
                NaturalOrderIntDataEntry(sort, IntArrayUtil.random(n))
            },
            { n ->
                NaturalOrderIntDataEntry(sort, IntArrayUtil.duplicated(n))
            },
            { n ->
                NaturalOrderIntDataEntry(sort, IntArrayUtil.closeSorted(n))
            }
        )
    }

    private class ComparatorIntDataEntry(private val sort: IntSort, data0: IntArray, private val comp: IntComparator) :
        IntDataEntry(data0) {
        override fun fullSort(array: IntArray) {
            sort.iSort(array, comp)
        }

        override fun partialSort(array: IntArray, from: Int, to: Int) {
            sort.iSort(array, from, to, comp)
        }

        override fun isSorted(array: IntArray, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (comp.compare(array[i], array[i + 1]) > 0) return false
            }
            return true
        }
    }

    private class FloatKeyIntDataEntry(private val sort: IntSort, private val keys: FloatArray, data0: IntArray) :
        IntDataEntry(data0) {
        override fun fullSort(array: IntArray) {
            val k = keys.copyOf()
            sort.iSort(array, k)
            assert(k.contentEquals(keys)) { "Keys modified" }
        }

        override fun partialSort(array: IntArray, from: Int, to: Int) {
            val k = keys.copyOf()
            sort.iSort(array, from, to, k)
            assert(k.contentEquals(keys)) { "Keys modified" }
        }

        override fun isSorted(array: IntArray, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (keys[array[i]] > keys[array[i + 1]]) return false
            }
            return true
        }
    }

    private class IntKeyIntDataEntry(private val sort: IntSort, private val keys: IntArray, data0: IntArray) :
        IntDataEntry(data0) {
        override fun fullSort(array: IntArray) {
            val k = keys.copyOf()
            sort.iSort(array, k)
            assert(k.contentEquals(keys)) { "Keys modified" }
        }

        override fun partialSort(array: IntArray, from: Int, to: Int) {
            val k = keys.copyOf()
            sort.iSort(array, from, to, k)
            assert(k.contentEquals(keys)) { "Keys modified" }
        }

        override fun isSorted(array: IntArray, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (keys[array[i]] > keys[array[i + 1]]) return false
            }
            return true
        }
    }

    private class NaturalOrderIntDataEntry(private val sort: IntSort, data0: IntArray) : IntDataEntry(data0) {
        override fun fullSort(array: IntArray) {
            sort.iSort(array)
        }

        override fun partialSort(array: IntArray, from: Int, to: Int) {
            sort.iSort(array, from, to)
        }

        override fun isSorted(array: IntArray, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (array[i] > array[i + 1]) return false
            }
            return true
        }
    }

    private sealed class IntDataEntry(private val data0: IntArray) : DataEntry<IntArray> {
        private val count = count(data0)
        override val data: IntArray
            get() = data0.copyOf()

        private fun count(array: IntArray): Int2IntMap {
            val result = Int2IntOpenHashMap()
            for (i in array.indices) {
                result.put(array[i], result.getOrDefault(array[i], 0) + 1)
            }
            return result
        }

        override fun isContentEqual(array: IntArray): Boolean {
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

        override fun isEqual(array: IntArray, from: Int, to: Int): Boolean {
            for (i in from until to) {
                if (data0[i] != array[i]) return false
            }
            return true
        }
    }
}