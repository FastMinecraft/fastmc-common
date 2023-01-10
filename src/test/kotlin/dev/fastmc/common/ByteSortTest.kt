package dev.fastmc.common

import dev.fastmc.common.collection.mapArray
import dev.fastmc.common.sort.*
import it.unimi.dsi.fastutil.bytes.Byte2IntMap
import it.unimi.dsi.fastutil.bytes.Byte2IntOpenHashMap
import it.unimi.dsi.fastutil.ints.Int2IntMap
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
import org.junit.jupiter.api.Test

class ByteSortTest : SortTest() {
    @Test
    fun heapsort() {
        testSort(ByteHeapsort.INSTANCE)
    }

    @Test
    fun insertionSort() {
        testSort(ByteInsertionSort.INSTANCE, 10_000)
    }

    @Test
    fun introsort() {
        testSort(ByteIntrosort.INSTANCE)
    }

    private fun testSort(sort: ByteSort, maxSize: Int = Int.MAX_VALUE) {
        testSort(
            maxSize,
            *naturalOrders(sort),
            *reverseOrders(sort),
            *intKeys(sort),
            *floatKeys(sort)
        )
    }

    private fun floatKeys(sort: ByteSort): Array<(Int) -> ByteDataEntry> {
        return arrayOf(
            { n ->
                val keys = floatArrayOf(0.0f)
                val array = ByteArray(n)
                FloatKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArrayUtil.ascending(n)
                val array = ByteArrayUtil.ascending(n)
                FloatKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArrayUtil.randomAscending(n)
                val array = ByteArrayUtil.randomAscending(n)
                FloatKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArray(n)
                val array = ByteArrayUtil.randomAscending(n)
                FloatKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArrayUtil.random(n)
                val array = ByteArrayUtil.randomAscending(n)
                FloatKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArrayUtil.closeSorted(n)
                val array = ByteArrayUtil.randomAscending(n)
                FloatKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = FloatArrayUtil.duplicated(n)
                val array = ByteArrayUtil.randomAscending(n)
                FloatKeyByteDataEntry(sort, keys, array)
            },
        )
    }

    private fun intKeys(sort: ByteSort): Array<(Int) -> ByteDataEntry> {
        return arrayOf(
            { n ->
                val keys = intArrayOf(0)
                val array = ByteArray(n)
                IntKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArrayUtil.ascending(n)
                val array = ByteArrayUtil.ascending(n)
                IntKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArrayUtil.randomAscending(n)
                val array = ByteArrayUtil.randomAscending(n)
                IntKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArray(n)
                val array = ByteArrayUtil.randomAscending(n)
                IntKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArrayUtil.random(n)
                val array = ByteArrayUtil.randomAscending(n)
                IntKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArrayUtil.closeSorted(n)
                val array = ByteArrayUtil.randomAscending(n)
                IntKeyByteDataEntry(sort, keys, array)
            },
            { n ->
                val keys = IntArrayUtil.duplicated(n)
                val array = ByteArrayUtil.randomAscending(n)
                IntKeyByteDataEntry(sort, keys, array)
            },
        )
    }

    private fun reverseOrders(sort: ByteSort): Array<(Int) -> ByteDataEntry> {
        return naturalOrders(sort).mapArray {
            { n ->
                ComparatorByteDataEntry(sort, it(n).data, ByteComparator.REVERSE_ORDER)
            }
        }
    }

    private fun naturalOrders(sort: ByteSort): Array<(Int) -> ByteDataEntry> {
        return arrayOf(
            { n ->
                NaturalOrderByteDataEntry(sort, ByteArrayUtil.ascending(n))
            },
            { n ->
                NaturalOrderByteDataEntry(sort, ByteArrayUtil.descending(n))
            },
            { n ->
                NaturalOrderByteDataEntry(sort, ByteArrayUtil.randomAscending(n))
            },
            { n ->
                NaturalOrderByteDataEntry(sort, ByteArrayUtil.random(n))
            },
            { n ->
                NaturalOrderByteDataEntry(sort, ByteArrayUtil.duplicated(n))
            },
            { n ->
                NaturalOrderByteDataEntry(sort, ByteArrayUtil.closeSorted(n))
            }
        )
    }

    private class ComparatorByteDataEntry(private val sort: ByteSort, data0: ByteArray, private val comp: ByteComparator) :
        ByteDataEntry(data0) {
        override fun fullSort(array: ByteArray) {
            sort.iSort(array, comp)
        }

        override fun partialSort(array: ByteArray, from: Int, to: Int) {
            sort.iSort(array, from, to, comp)
        }

        override fun isSorted(array: ByteArray, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (comp.compare(array[i], array[i + 1]) > 0) return false
            }
            return true
        }
    }

    private class FloatKeyByteDataEntry(private val sort: ByteSort, private val keys: FloatArray, data0: ByteArray) :
        ByteDataEntry(data0) {
        override fun fullSort(array: ByteArray) {
            val k = keys.copyOf()
            sort.iSort(array, k)
            assert(k.contentEquals(keys)) { "Keys modified" }
        }

        override fun partialSort(array: ByteArray, from: Int, to: Int) {
            val k = keys.copyOf()
            sort.iSort(array, from, to, k)
            assert(k.contentEquals(keys)) { "Keys modified" }
        }

        override fun isSorted(array: ByteArray, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (keys[array[i].toInt() and 0xFF] > keys[array[i + 1].toInt() and 0xFF]) return false
            }
            return true
        }
    }

    private class IntKeyByteDataEntry(private val sort: ByteSort, private val keys: IntArray, data0: ByteArray) :
        ByteDataEntry(data0) {
        override fun fullSort(array: ByteArray) {
            val k = keys.copyOf()
            sort.iSort(array, k)
            assert(k.contentEquals(keys)) { "Keys modified" }
        }

        override fun partialSort(array: ByteArray, from: Int, to: Int) {
            val k = keys.copyOf()
            sort.iSort(array, from, to, k)
            assert(k.contentEquals(keys)) { "Keys modified" }
        }

        override fun isSorted(array: ByteArray, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (keys[array[i].toInt() and 0xFF] > keys[array[i + 1].toInt() and 0xFF]) return false
            }
            return true
        }
    }

    private class NaturalOrderByteDataEntry(private val sort: ByteSort, data0: ByteArray) : ByteDataEntry(data0) {
        override fun fullSort(array: ByteArray) {
            sort.iSort(array)
        }

        override fun partialSort(array: ByteArray, from: Int, to: Int) {
            sort.iSort(array, from, to)
        }

        override fun isSorted(array: ByteArray, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (array[i] > array[i + 1]) return false
            }
            return true
        }
    }

    private sealed class ByteDataEntry(private val data0: ByteArray) : DataEntry<ByteArray> {
        private val count = count(data0)
        override val data: ByteArray
            get() = data0.copyOf()

        private fun count(array: ByteArray): Byte2IntMap {
            val result = Byte2IntOpenHashMap()
            for (i in array.indices) {
                result.put(array[i], result.getOrDefault(array[i], 0) + 1)
            }
            return result
        }

        override fun isContentEqual(array: ByteArray): Boolean {
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

        override fun isEqual(array: ByteArray, from: Int, to: Int): Boolean {
            for (i in from until to) {
                if (data0[i] != array[i]) return false
            }
            return true
        }
    }
}