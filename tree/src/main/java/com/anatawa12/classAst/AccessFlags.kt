package com.anatawa12.classAst

@JvmInline
value class AccessFlags(val value: UShort) {
    operator fun plus(flag: AccessFlags) = AccessFlags(this.value.or(flag.value))
    operator fun minus(flag: AccessFlags) = AccessFlags(this.value.and(flag.value.inv()))

    operator fun contains(flag: AccessFlags) = this.value.and(flag.value) == flag.value

    companion object {
        val ACC_PUBLIC = AccessFlags(1u.shl(0).toUShort())
        val ACC_PRIVATE = AccessFlags(1u.shl(1).toUShort())
        val ACC_PROTECTED = AccessFlags(1u.shl(2).toUShort())
        val ACC_STATIC = AccessFlags(1u.shl(3).toUShort())
        val ACC_FINAL = AccessFlags(1u.shl(4).toUShort())
        val ACC_SUPER = AccessFlags(1u.shl(5).toUShort())
        val ACC_SYNCHRONIZED = AccessFlags(1u.shl(5).toUShort())
        val ACC_TRANSITIVE = AccessFlags(1u.shl(5).toUShort())
        val ACC_OPEN = AccessFlags(1u.shl(5).toUShort())
        val ACC_VOLATILE = AccessFlags(1u.shl(6).toUShort())
        val ACC_STATIC_PHASE = AccessFlags(1u.shl(6).toUShort())
        val ACC_BRIDGE = AccessFlags(1u.shl(6).toUShort())
        val ACC_TRANSIENT = AccessFlags(1u.shl(7).toUShort())
        val ACC_VARARGS = AccessFlags(1u.shl(7).toUShort())
        val ACC_NATIVE = AccessFlags(1u.shl(8).toUShort())
        val ACC_INTERFACE = AccessFlags(1u.shl(9).toUShort())
        val ACC_ABSTRACT = AccessFlags(1u.shl(10).toUShort())
        val ACC_STRICT = AccessFlags(1u.shl(11).toUShort())
        val ACC_SYNTHETIC = AccessFlags(1u.shl(12).toUShort())
        val ACC_ANNOTATION = AccessFlags(1u.shl(13).toUShort())
        val ACC_ENUM = AccessFlags(1u.shl(14).toUShort())
        val ACC_MANDATED = AccessFlags(1u.shl(15).toUShort())
        val ACC_MODULE = AccessFlags(1u.shl(15).toUShort())
    }
}
