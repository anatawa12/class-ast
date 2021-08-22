package com.anatawa12.classAst

internal fun ByteArray.toHexString(): String {
    val chars = CharArray(size * 2)
    for (i in indices) {
        chars[i * 2 + 0] = "0123456789abcdef"[this[i].toUByte().toInt().shl(4)]
        chars[i * 2 + 1] = "0123456789abcdef"[this[i].toUByte().and(0xFu).toInt()]
    }
    return String(chars)
}
