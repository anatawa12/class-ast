package com.anatawa12.classAst

@JvmInline
value class ClassFileVersion private constructor(
    private val value: UInt
): Comparable<ClassFileVersion> {
    constructor(
        major: UShort,
        minor: UShort = 0u,
    ) : this(major.toUInt().shl(16).or(minor.toUInt()))

    /**
     * The major part of class file version. This field can be used to determinate minimal jdk version.
     */
    val major: UShort get() = value.shr(16).toUShort()

    /**
     * The minor part of class file version. 
     * This field can be used to determinate if this class requires experimental features.
     */
    val minor: UShort get() = value.toUShort()

    /**
     * Compares the versions. newer is greater.
     */
    override fun compareTo(other: ClassFileVersion): Int =
        this.value.compareTo(other.value)

    override fun toString(): String {
        return "$major.$minor"
    }
}
