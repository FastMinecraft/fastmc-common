package dev.fastmc.common

import kotlin.math.PI
import kotlin.math.ceil
import kotlin.math.floor

object MathUtils {
    @JvmStatic
    fun ceilToPOT(valueIn: Int): Int {
        var i = valueIn
        i--
        i = i or (i shr 1)
        i = i or (i shr 2)
        i = i or (i shr 4)
        i = i or (i shr 8)
        i = i or (i shr 16)
        i++
        return i
    }

    @JvmStatic
    fun lerp(from: Double, to: Double, delta: Double): Double {
        return from + (to - from) * delta
    }

    @JvmStatic
    fun lerp(from: Double, to: Double, delta: Float): Double {
        return from + (to - from) * delta
    }

    @JvmStatic
    fun lerp(from: Float, to: Float, delta: Double): Float {
        return from + (to - from) * delta.toFloat()
    }

    @JvmStatic
    fun lerp(from: Float, to: Float, delta: Float): Float {
        return from + (to - from) * delta
    }

    @JvmStatic
    fun clamp(value: Double, min: Double, max: Double): Double {
        return value.coerceIn(min, max)
    }

    @JvmStatic
    fun clamp(value: Float, min: Float, max: Float): Float {
        return value.coerceIn(min, max)
    }

    @JvmStatic
    fun clamp(value: Int, min: Int, max: Int): Int {
        return value.coerceIn(min, max)
    }
}

const val PI_FLOAT = 3.14159265358979323846f

const val FLOOR_DOUBLE_D = 1_073_741_824.0
const val FLOOR_DOUBLE_I = 1_073_741_824

const val FLOOR_FLOAT_F = 4_194_304.0f
const val FLOOR_FLOAT_I = 4_194_304

fun Double.floorToInt() = floor(this).toInt()
fun Float.floorToInt() = floor(this).toInt()

fun Double.ceilToInt() = ceil(this).toInt()
fun Float.ceilToInt() = ceil(this).toInt()

fun Double.fastFloor() = (this + FLOOR_DOUBLE_D).toInt() - FLOOR_DOUBLE_I
fun Float.fastFloor() = (this + FLOOR_FLOAT_F).toInt() - FLOOR_FLOAT_I

fun Double.fastCeil() = FLOOR_DOUBLE_I - (FLOOR_DOUBLE_D - this).toInt()
fun Float.fastCeil() = FLOOR_FLOAT_I - (FLOOR_FLOAT_F - this).toInt()

fun Float.toRadians() = this / 180.0f * PI_FLOAT
fun Double.toRadians() = this / 180.0 * PI

fun Float.toDegree() = this * 180.0f / PI_FLOAT
fun Double.toDegree() = this * 180.0 / PI

val Double.sq: Double get() = this * this
val Float.sq: Float get() = this * this
val Int.sq: Int get() = this * this

val Double.cubic: Double get() = this * this * this
val Float.cubic: Float get() = this * this * this
val Int.cubic: Int get() = this * this * this

val Double.quart: Double get() = this * this * this * this
val Float.quart: Float get() = this * this * this * this
val Int.quart: Int get() = this * this * this * this

val Double.quint: Double get() = this * this * this * this * this
val Float.quint: Float get() = this * this * this * this * this
val Int.quint: Int get() = this * this * this * this * this

val Int.isEven: Boolean get() = this and 1 == 0
val Int.isOdd: Boolean get() = this and 1 == 1