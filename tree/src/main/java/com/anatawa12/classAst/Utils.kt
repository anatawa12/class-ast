package com.anatawa12.classAst

internal fun ByteArray.toHexString(): String {
    val chars = CharArray(size * 2)
    for (i in indices) {
        chars[i * 2 + 0] = "0123456789abcdef"[this[i].toUByte().toInt().shl(4)]
        chars[i * 2 + 1] = "0123456789abcdef"[this[i].toUByte().and(0xFu).toInt()]
    }
    return String(chars)
}

internal val CV45_3 = ClassFileVersion(45u, 3u)
internal val CV46_0 = ClassFileVersion(46u, 0u)
internal val CV47_0 = ClassFileVersion(47u, 0u)
internal val CV48_0 = ClassFileVersion(48u, 0u)
internal val CV49_0 = ClassFileVersion(49u, 0u)
internal val CV50_0 = ClassFileVersion(50u, 0u)
internal val CV51_0 = ClassFileVersion(51u, 0u)
internal val CV52_0 = ClassFileVersion(52u, 0u)
internal val CV53_0 = ClassFileVersion(53u, 0u)
internal val CV54_0 = ClassFileVersion(54u, 0u)
internal val CV55_0 = ClassFileVersion(55u, 0u)
internal val CV56_0 = ClassFileVersion(56u, 0u)
internal val CV57_0 = ClassFileVersion(57u, 0u)
internal val CV58_0 = ClassFileVersion(58u, 0u)
internal val CV59_0 = ClassFileVersion(59u, 0u)
internal val CV60_0 = ClassFileVersion(60u, 0u)
