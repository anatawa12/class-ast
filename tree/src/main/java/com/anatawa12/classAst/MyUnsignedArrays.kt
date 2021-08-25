package com.anatawa12.classAst

/**
 * The class for array of UShort until kotlin.UShortArray become stable.
 */
@JvmInline
internal value class UShortArray internal constructor(private val storage: ShortArray) {
    val size get() = storage.size

    /** Creates a new array of the specified [size], with all elements initialized to zero. */
    constructor(size: Int) : this(ShortArray(size))

    /**
     * Returns the array element at the given [index]. This method can be called using the index operator.
     *
     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except in Kotlin/JS
     * where the behavior is unspecified.
     */
    operator fun get(index: Int): UShort = storage[index].toUShort()

    /**
     * Sets the element at the given [index] to the given [value]. This method can be called using the index operator.
     *
     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except in Kotlin/JS
     * where the behavior is unspecified.
     */
    operator fun set(index: Int, value: UShort) {
        storage[index] = value.toShort()
    }
}

/**
 * Creates a new array of the specified [size], where each element is calculated by calling the specified
 * [init] function.
 *
 * The function [init] is called for each array element sequentially starting from the first one.
 * It should return the value for an array element given its index.
 */
internal inline fun UShortArray(size: Int, init: (Int) -> UShort): UShortArray {
    return UShortArray(ShortArray(size) { index -> init(index).toShort() })
}

internal fun ushortArrayOf(
    element0: UShort,
): UShortArray = UShortArray(shortArrayOf(
    element0.toShort(),
))
internal fun ushortArrayOf(
    element0: UShort, 
    element1: UShort,
): UShortArray = UShortArray(shortArrayOf(
    element0.toShort(), 
    element1.toShort(),
))
internal fun ushortArrayOf(
    element0: UShort, 
    element1: UShort, 
    element2: UShort,
): UShortArray = UShortArray(shortArrayOf(
    element0.toShort(), 
    element1.toShort(), 
    element2.toShort(),
))
internal fun ushortArrayOf(
    element0: UShort, 
    element1: UShort, 
    element2: UShort, 
    element3: UShort,
): UShortArray = UShortArray(shortArrayOf(
    element0.toShort(), 
    element1.toShort(), 
    element2.toShort(), 
    element3.toShort(),
))
internal fun ushortArrayOf(
    element0: UShort, 
    element1: UShort, 
    element2: UShort, 
    element3: UShort, 
    element4: UShort,
): UShortArray = UShortArray(shortArrayOf(
    element0.toShort(), 
    element1.toShort(), 
    element2.toShort(), 
    element3.toShort(), 
    element4.toShort(),
))
internal fun ushortArrayOf(
    element0: UShort, 
    element1: UShort, 
    element2: UShort, 
    element3: UShort, 
    element4: UShort, 
    element5: UShort,
): UShortArray = UShortArray(shortArrayOf(
    element0.toShort(), 
    element1.toShort(), 
    element2.toShort(), 
    element3.toShort(), 
    element4.toShort(), 
    element5.toShort(),
))
