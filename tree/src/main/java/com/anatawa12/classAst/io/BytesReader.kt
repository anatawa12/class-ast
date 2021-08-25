package com.anatawa12.classAst.io

abstract class BytesReader {
    @Throws(ClassFileException::class)
    abstract fun readU1(): UByte
    @Throws(ClassFileException::class)
    abstract fun readU2(): UShort
    @Throws(ClassFileException::class)
    abstract fun readU4(): UInt
    @Throws(ClassFileException::class)
    abstract fun readU8(): ULong
    @Throws(ClassFileException::class)
    abstract fun read(data: ByteArray)
}
