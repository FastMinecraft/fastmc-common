package dev.fastmc.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.management.ManagementFactory
import kotlin.random.Random

sealed class SortTest {
    private val debug = ManagementFactory.getRuntimeMXBean().inputArguments.toString().indexOf("-agentlib:jdwp") > 0

    private val sizes = listOf(
        1 to 10,
        2 to 10,
        3 to 10,
        4 to 10,
        5 to 10,
        10 to 50,
        100 to 100,
        1_000 to 50,
        10_000 to 10,
        100_000 to 5,
        1_000_000 to 2,
    )

    protected fun testSort(maxSize: Int, vararg testDataProviders: (Int) -> DataEntry<*>) {
        if (debug) {
            testSortSingle(maxSize, *testDataProviders)
        } else {
            testSortParallel(maxSize, *testDataProviders)
        }
    }

    private fun testSortSingle(maxSize: Int, vararg testDataProviders: (Int) -> DataEntry<*>) {
        for ((size, n) in sizes) {
            if (size > maxSize) break
            repeat(n) {
                for (provider in testDataProviders) {
                    @Suppress("UNCHECKED_CAST")
                    val entry = provider(size) as DataEntry<Any>

                    val a = entry.data
                    entry.fullSort(a)
                    assert(entry.isSorted(a, 0, size)) { "Array is not fully sorted" }
                    assert(entry.isContentEqual(a)) { "Array content modified" }

                    val b = entry.data
                    val from = Random.nextInt(size)
                    val to = Random.nextInt(from, size)
                    entry.partialSort(b, from, to)
                    assert(entry.isSorted(b, from, to)) { "Array is not partially sorted" }
                    assert(entry.isEqual(b, 0, from)) { "Arrays modified before from" }
                    assert(entry.isEqual(b, to, size)) { "Arrays modified after to" }
                    assert(entry.isContentEqual(b)) { "Array content modified" }
                }
            }
            println("Passed testing for size %,d".format(size))
        }
    }

    private fun testSortParallel(maxSize: Int, vararg testDataProviders: (Int) -> DataEntry<*>) {
        runBlocking {
            for ((size, n) in sizes) {
                if (size > maxSize) break
                coroutineScope {
                    repeat(n) {
                        for (provider in testDataProviders) {
                            launch(Dispatchers.Default) {
                                @Suppress("UNCHECKED_CAST")
                                val entry = provider(size) as DataEntry<Any>

                                launch {
                                    val a = entry.data
                                    entry.fullSort(a)
                                    assert(entry.isSorted(a, 0, size)) { "Array is not fully sorted" }
                                    assert(entry.isContentEqual(a)) { "Array content modified" }
                                }

                                launch {
                                    val b = entry.data
                                    val from = Random.nextInt(size)
                                    val to = Random.nextInt(from, size)
                                    entry.partialSort(b, from, to)
                                    assert(entry.isSorted(b, from, to)) { "Array is not partially sorted" }
                                    assert(entry.isEqual(b, 0, from)) { "Arrays modified before from" }
                                    assert(entry.isEqual(b, to, size)) { "Arrays modified after to" }
                                    assert(entry.isContentEqual(b)) { "Array content modified" }
                                }
                            }
                        }
                    }
                }
                println("Passed testing for size %,d".format(size))
            }
        }
    }

    interface DataEntry<T> {
        val data: T

        fun fullSort(array: T)
        fun partialSort(array: T, from: Int, to: Int)

        fun isSorted(array: T, from: Int, to: Int): Boolean
        fun isEqual(array: T, from: Int, to: Int): Boolean
        fun isContentEqual(array: T): Boolean
    }
}
