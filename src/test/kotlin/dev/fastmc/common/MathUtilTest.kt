package dev.fastmc.common

import org.junit.jupiter.api.Test
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.test.assertEquals

class MathUtilTest {
    @Test
    fun floorFloat() {
        fun test(v: Float) {
            assertEquals(floor(v).toInt(), v.floorToInt())
        }

        test(-0.0f)
        test(0.0f)
        test(0.1f)
        test(0.5f)
        test(0.9f)
        test(1.0f)

        test(-0.1f)
        test(-0.5f)
        test(-0.9f)
        test(-1.0f)

        test(170.57812f)
        test(211.07812f)

        test(-170.57812f)
        test(-211.07812f)

        test(99999.99f)
        test(99999.66f)
        test(99999.11f)
        test(99999.0f)

        test(-99999.99f)
        test(-99999.66f)
        test(-99999.11f)
        test(-99999.0f)
    }

    @Test
    fun floorDouble() {
        fun test(v: Double) {
            assertEquals(floor(v).toInt(), v.floorToInt())
        }

        test(-0.0)
        test(0.0)
        test(0.1)
        test(0.5)
        test(0.9)
        test(1.0)

        test(-0.1)
        test(-0.5)
        test(-0.9)
        test(-1.0)

        test(170.57812)
        test(211.07812)

        test(-170.57812)
        test(-211.07812)

        test(99999.99)
        test(99999.66)
        test(99999.11)
        test(99999.0)

        test(-99999.99)
        test(-99999.66)
        test(-99999.11)
        test(-99999.0)
    }

    @Test
    fun ceilFloat() {
        fun test(v: Float) {
            assertEquals(ceil(v).toInt(), v.ceilToInt())
        }

        test(-0.0f)
        test(0.0f)
        test(0.1f)
        test(0.5f)
        test(0.9f)
        test(1.0f)

        test(-0.1f)
        test(-0.5f)
        test(-0.9f)
        test(-1.0f)

        test(170.57812f)
        test(211.07812f)

        test(-170.57812f)
        test(-211.07812f)

        test(99999.99f)
        test(99999.66f)
        test(99999.11f)
        test(99999.0f)

        test(-99999.99f)
        test(-99999.66f)
        test(-99999.11f)
        test(-99999.0f)
    }

    @Test
    fun ceilDouble() {
        fun test(v: Double) {
            assertEquals(ceil(v).toInt(), v.ceilToInt())
        }

        test(-0.0)
        test(0.0)
        test(0.1)
        test(0.5)
        test(0.9)
        test(1.0)

        test(-0.1)
        test(-0.5)
        test(-0.9)
        test(-1.0)

        test(170.57812)
        test(211.07812)

        test(-170.57812)
        test(-211.07812)

        test(99999.99)
        test(99999.66)
        test(99999.11)
        test(99999.0)

        test(-99999.99)
        test(-99999.66)
        test(-99999.11)
        test(-99999.0)
    }
}