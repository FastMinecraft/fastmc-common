package dev.fastmc.common

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RoundingTest {
    @Test
    fun floorFloat() {
        assertEquals(0, 0.0f.floorToInt())
        assertEquals(0, 0.1f.floorToInt())
        assertEquals(0, 0.5f.floorToInt())
        assertEquals(0, 0.9f.floorToInt())

        assertEquals(-1, (-0.1f).floorToInt())
        assertEquals(-1, (-0.5f).floorToInt())
        assertEquals(-1, (-0.9f).floorToInt())

        assertEquals(9999, 9999.99f.floorToInt())
        assertEquals(9999, 9999.66f.floorToInt())
        assertEquals(9999, 9999.11f.floorToInt())

        assertEquals(-10000, (-9999.99f).floorToInt())
        assertEquals(-10000, (-9999.66f).floorToInt())
        assertEquals(-10000, (-9999.11f).floorToInt())
    }


    @Test
    fun floorDouble() {
        assertEquals(0, 0.0.floorToInt())
        assertEquals(0, 0.1.floorToInt())
        assertEquals(0, 0.5.floorToInt())
        assertEquals(0, 0.9.floorToInt())

        assertEquals(-1, (-0.1).floorToInt())
        assertEquals(-1, (-0.5).floorToInt())
        assertEquals(-1, (-0.9).floorToInt())

        assertEquals(99999, 99999.99.floorToInt())
        assertEquals(99999, 99999.66.floorToInt())
        assertEquals(99999, 99999.11.floorToInt())

        assertEquals(-100000, (-99999.99).floorToInt())
        assertEquals(-100000, (-99999.66).floorToInt())
        assertEquals(-100000, (-99999.11).floorToInt())
    }

    @Test
    fun ceilFloat() {
        assertEquals(0, 0.0f.ceilToInt())
        assertEquals(1, 0.1f.ceilToInt())
        assertEquals(1, 0.5f.ceilToInt())
        assertEquals(1, 0.9f.ceilToInt())

        assertEquals(0, (-0.1f).ceilToInt())
        assertEquals(0, (-0.5f).ceilToInt())
        assertEquals(0, (-0.9f).ceilToInt())

        assertEquals(100000, 99999.99f.ceilToInt())
        assertEquals(100000, 99999.66f.ceilToInt())
        assertEquals(100000, 99999.11f.ceilToInt())

        assertEquals(-99999, (-99999.99f).ceilToInt())
        assertEquals(-99999, (-99999.66f).ceilToInt())
        assertEquals(-99999, (-99999.11f).ceilToInt())
    }

    @Test
    fun ceilDouble() {
        assertEquals(0, 0.0.ceilToInt())
        assertEquals(1, 0.1.ceilToInt())
        assertEquals(1, 0.5.ceilToInt())
        assertEquals(1, 0.9.ceilToInt())

        assertEquals(0, (-0.1).ceilToInt())
        assertEquals(0, (-0.5).ceilToInt())
        assertEquals(0, (-0.9).ceilToInt())

        assertEquals(100000, 99999.99.ceilToInt())
        assertEquals(100000, 99999.66.ceilToInt())
        assertEquals(100000, 99999.11.ceilToInt())

        assertEquals(-99999, (-99999.99).ceilToInt())
        assertEquals(-99999, (-99999.66).ceilToInt())
        assertEquals(-99999, (-99999.11).ceilToInt())
    }
}