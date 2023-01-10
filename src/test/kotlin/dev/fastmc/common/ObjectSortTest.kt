package dev.fastmc.common

import dev.fastmc.common.collection.mapArray
import dev.fastmc.common.sort.ObjectHeapsort
import dev.fastmc.common.sort.ObjectInsertionSort
import dev.fastmc.common.sort.ObjectIntrosort
import dev.fastmc.common.sort.ObjectSort
import it.unimi.dsi.fastutil.objects.Reference2IntMap
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap
import org.junit.jupiter.api.Test
import java.util.*

class ObjectSortTest : SortTest() {
    @Test
    fun heapsort() {
        testSort(ObjectHeapsort.INSTANCE)
    }

    @Test
    fun insertionSort() {
        testSort(ObjectInsertionSort.INSTANCE, 10_000)
    }

    @Test
    fun introsort() {
        testSort(ObjectIntrosort.INSTANCE)
    }

    private fun testSort(sort: ObjectSort, maxSize: Int = Int.MAX_VALUE) {
        testSort(
            maxSize,
            *naturalOrders(sort),
            *reverseOrders(sort),
            *customComparator(sort),
            *customComparable(sort),
        )
    }

    private fun customComparable(sort: ObjectSort): Array<(Int) -> ObjectDataEntry<*>> {
        val arr = arrayOf<(Int) -> ObjectDataEntry<*>>(
            { n ->
                NaturalOrderObjectDataEntry(sort, Array(n) { TestObject()})
            },
            { n ->
                val random = Random()
                NaturalOrderObjectDataEntry(sort, Array(n) {TestObject(random)})
            }
        )

        return arr
    }


    private data class TestObject(val v1: Int, val v2: Double, val v3: String) : Comparable<TestObject> {
        constructor() : this(0, 0.0, "")

        constructor(random: Random) : this(
            random.nextInt(),
            random.nextDouble(),
            buildString { repeat(5) { random.nextInt(0, 65536).toChar() } }
        )

        override fun compareTo(other: TestObject): Int {
            var result = v1.compareTo(other.v1)
            if (result == 0) {
                result = v2.compareTo(other.v2)
            }
            if (result == 0) {
                result = v3.compareTo(other.v3)
            }
            return result
        }
    }

    private fun customComparator(sort: ObjectSort): Array<(Int) -> ObjectDataEntry<Int>> {
        return naturalOrders(sort).mapArray {
            { n ->
                ComparatorObjectDataEntry(
                    sort,
                    it(n).data,
                    compareBy<Int> { it % 69 }.thenBy { it % 420 }.thenBy { it and 1 })
            }
        }
    }

    private fun reverseOrders(sort: ObjectSort): Array<(Int) -> ObjectDataEntry<Int>> {
        return naturalOrders(sort).mapArray {
            { n ->
                ComparatorObjectDataEntry(sort, it(n).data, Comparator.reverseOrder())
            }
        }
    }

    private fun naturalOrders(sort: ObjectSort): Array<(Int) -> ObjectDataEntry<Int>> {
        return arrayOf(
            { n ->
                NaturalOrderObjectDataEntry(sort, BoxedIntArrayUtil.ascending(n))
            },
            { n ->
                NaturalOrderObjectDataEntry(sort, BoxedIntArrayUtil.descending(n))
            },
            { n ->
                NaturalOrderObjectDataEntry(sort, BoxedIntArrayUtil.randomAscending(n))
            },
            { n ->
                NaturalOrderObjectDataEntry(sort, BoxedIntArrayUtil.random(n))
            },
            { n ->
                NaturalOrderObjectDataEntry(sort, BoxedIntArrayUtil.duplicated(n))
            },
            { n ->
                NaturalOrderObjectDataEntry(sort, BoxedIntArrayUtil.closeSorted(n))
            }
        )
    }

    private class ComparatorObjectDataEntry<T>(
        private val sort: ObjectSort,
        data0: Array<T>,
        private val comp: Comparator<T>
    ) : ObjectDataEntry<T>(data0) {
        override fun fullSort(array: Array<T>) {
            sort.iSort(array, comp)
        }

        override fun partialSort(array: Array<T>, from: Int, to: Int) {
            sort.iSort(array, from, to, comp)
        }

        override fun isSorted(array: Array<T>, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (comp.compare(array[i], array[i + 1]) > 0) return false
            }
            return true
        }
    }

    private class NaturalOrderObjectDataEntry<T : Comparable<T>>(private val sort: ObjectSort, data0: Array<T>) :
        ObjectDataEntry<T>(data0) {
        override fun fullSort(array: Array<T>) {
            sort.iSort(array)
        }

        override fun partialSort(array: Array<T>, from: Int, to: Int) {
            sort.iSort(array, from, to)
        }

        override fun isSorted(array: Array<T>, from: Int, to: Int): Boolean {
            for (i in from until to - 1) {
                if (array[i] > array[i + 1]) return false
            }
            return true
        }
    }

    private sealed class ObjectDataEntry<T>(private val data0: Array<T>) : DataEntry<Array<T>> {
        private val count = count(data0)
        override val data: Array<T>
            get() = data0.copyOf()

        private fun count(array: Array<T>): Reference2IntMap<T> {
            val result = Reference2IntOpenHashMap<T>()
            for (i in array.indices) {
                result.put(array[i], result.getOrDefault(array[i], 0) + 1)
            }
            return result
        }

        override fun isContentEqual(array: Array<T>): Boolean {
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

        override fun isEqual(array: Array<T>, from: Int, to: Int): Boolean {
            for (i in from until to) {
                if (data0[i] != array[i]) return false
            }
            return true
        }
    }
}