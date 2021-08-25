package com.anatawa12.classAst.io

import com.anatawa12.classAst.Constant
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

/**
 * The class to read class file.
 */
class ClassFileReader(private val bytesReader: BytesReader) {
    private lateinit var constantPool: Array<Constant?>
    internal fun setConstantPool(pool: Array<Constant?>) {
        constantPool = pool
    }

    @Throws(ClassFileException::class)
    fun readU1() = bytesReader.readU1()

    @Throws(ClassFileException::class)
    fun readU2() = bytesReader.readU2()

    @Throws(ClassFileException::class)
    fun readU4() = bytesReader.readU4()

    @Throws(ClassFileException::class)
    fun readU8() = bytesReader.readU8()

    @Throws(ClassFileException::class)
    fun readS1() = bytesReader.readU1().toByte()

    @Throws(ClassFileException::class)
    fun readS2() = bytesReader.readU2().toShort()

    @Throws(ClassFileException::class)
    fun readS4() = bytesReader.readU4().toInt()

    @Throws(ClassFileException::class)
    fun readS8() = bytesReader.readU8().toLong()

    @Throws(ClassFileException::class)
    fun read(data: ByteArray) = bytesReader.read(data)

    @Throws(ClassFileException::class)
    fun readConstantRef(nullable: Boolean): Constant? = 
        readConstantRef(Constant::class, nullable)

    @Throws(ClassFileException::class)
    fun <T : Constant> readConstantRef(type: Class<T>, nullable: Boolean): T? = 
        readConstantRef(type.kotlin, nullable)

    @Throws(ClassFileException::class)
    fun <T : Constant> readConstantRef(type: KClass<T>, nullable: Boolean): T? {
        val index = readU2().toInt()
        if (nullable && index == 0) return null
        val value = constantPool.getOrNull(index)
        if (value != null && value::class == type)
            @Suppress("UNCHECKED_CAST")
            return value as T
        throw ClassFileException("requested constant#$index is not a ${type.simpleName}")
    }

    @Throws(ClassFileException::class)
    inline fun <reified T : Constant?> readConstantRef(): T {
        @Suppress("UNCHECKED_CAST")
        @OptIn(ExperimentalStdlibApi::class)
        return readConstantRef(T::class as KClass<Constant>, typeOf<T>().isMarkedNullable) as T
    }
}
