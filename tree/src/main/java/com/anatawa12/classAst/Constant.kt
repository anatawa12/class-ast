package com.anatawa12.classAst

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.autoVisitor.GenerateAccept
import com.anatawa12.autoVisitor.GenerateVisitor
import com.anatawa12.autoVisitor.HasVisitor
import com.anatawa12.classAst.io.ClassFileException
import com.anatawa12.classAst.io.ClassFileReader
import java.util.*


/**
 * The Constant Pool Elements
 * @spec 4.4
 */
@GenerateAccept
@HasVisitor(
    ConstantVisitor::class,
    hasCustomDataParam = false,
    acceptName = "accept",
    subclasses = [
        Constant::class,
        ConstantRef::class,
        ConstantAnyMethodRef::class,
        LoadableConstant::class,
        ConstantValue::class,
        ConstantUtf8::class,
        ConstantInteger::class,
        ConstantFloat::class,
        ConstantLong::class,
        ConstantDouble::class,
        ConstantClass::class,
        ConstantString::class,
        ConstantFieldRef::class,
        ConstantMethodRef::class,
        ConstantInterfaceMethodRef::class,
        ConstantNameAndType::class,
        ConstantMethodHandle::class,
        ConstantMethodType::class,
        ConstantDynamic::class,
        ConstantInvokeDynamic::class,
        ConstantModule::class,
        ConstantPackage::class,
    ],
)
sealed class Constant(
    val tag: UByte,
    val sinceVersion: ClassFileVersion,
) {
    internal var ofClass: ClassFile? = null

    // negative for
    internal var index: Int = -1

    companion object Reader {
        fun readConstantPool(reader: ClassFileReader): Array<Constant?> {
            val constantPoolCount = reader.readU2().toInt()
            val (hasFlags, data) = readDataList(reader, constantPoolCount)
            checkIndices(hasFlags, data)
            val values = arrayOfNulls<Constant>(constantPoolCount + 1)
            var initialized = 0
            while (hasFlags.cardinality() != initialized)
                initialized += initializeConstantPool(data, values)
            return values
        }

        // read data to short array. [0] is tag.
        private fun readDataList(reader: ClassFileReader, constantPoolCount: Int): Pair<BitSet, Array<UShortArray?>> {
            val data = arrayOfNulls<UShortArray>(constantPoolCount + 1)
            val hasValues = BitSet()
            var index = 1
            while (index <= constantPoolCount) {
                val value = readData(reader)
                hasValues[index] = true
                data[index++] = value
                if (value[0] == 5.toUShort() || value[0] == 6.toUShort()) {
                    if (index >= constantPoolCount)
                        throw ClassFileException("invalid constant_pool_count. the last element is long or double")
                    index++
                }
            }
            return hasValues to data
        }

        private fun readData(reader: ClassFileReader): UShortArray = when (val tag = reader.readU1().toInt()) {
            // ConstantUtf8
            1 -> parseConstantUtf8(ByteArray(reader.readU1().toInt()).apply(reader::read))
            // ConstantInteger: value(u4)
            3 -> ushortArrayOf(3u, reader.readU2(), reader.readU2())
            // ConstantFloat: value(u4)
            4 -> ushortArrayOf(4u, reader.readU2(), reader.readU2())
            // ConstantLong: value(u8)
            5 -> ushortArrayOf(5u, reader.readU2(), reader.readU2(), reader.readU2(), reader.readU2())
            // ConstantDouble: value(u8)
            6 -> ushortArrayOf(6u, reader.readU2(), reader.readU2(), reader.readU2(), reader.readU2())
            // ConstantClass: name index(u2)
            7 -> ushortArrayOf(7u, reader.readU2())
            // ConstantString: value index(u2)
            8 -> ushortArrayOf(8u, reader.readU2())
            // ConstantFieldRef: class index(u2) and name and type index(u2)
            9 -> ushortArrayOf(9u, reader.readU2(), reader.readU2())
            // ConstantMethodRef: class index(u2) and name and type index(u2)
            10 -> ushortArrayOf(10u, reader.readU2(), reader.readU2())
            // ConstantInterfaceMethodRef: class index(u2) and name and type index(u2)
            11 -> ushortArrayOf(11u, reader.readU2(), reader.readU2())
            // ConstantNameAndType: name index(u2) and descriptor index(u2)
            12 -> ushortArrayOf(12u, reader.readU2(), reader.readU2())
            // ConstantMethodHandle: kind(u1) and reference index(u2)
            15 -> ushortArrayOf(13u, reader.readU1().toUShort(), reader.readU2())
            // ConstantMethodType: descriptor index(u2)
            16 -> ushortArrayOf(16u, reader.readU2())
            // ConstantDynamic: bootstrap method attr index(u2) and name and type index (u2)
            17 -> ushortArrayOf(17u, reader.readU2(), reader.readU2())
            // ConstantInvokeDynamic: bootstrap method attr index(u2) and name and type index (u2)
            18 -> ushortArrayOf(18u, reader.readU2(), reader.readU2())
            // ConstantModule: name index(u2)
            19 -> ushortArrayOf(19u, reader.readU2())
            // ConstantModule: name index(u2)
            20 -> ushortArrayOf(19u, reader.readU2())
            else -> throw ClassFileException("invalid tag: $tag")
        }

        private fun parseConstantUtf8(data: ByteArray): UShortArray {
            // count
            val shorts = UShortArray(countUtf8Chars(data) + 1)
            shorts[0] = 1u

            var charIndex = 1
            var index = 0
            while (index < data.size) {
                val byte = data[index].toUByte()
                val char = when {
                    (byte and 0x80u) == 0x00u.toUByte() -> {
                        data[index++].toUByte().toUShort()
                    }
                    (byte and 0xE0u) == 0xC0u.toUByte() -> {
                        (data[index++].toInt() and 0x1f shl 6)
                            .or(data[index++].toInt() and 0x3f)
                            .toUShort()
                    }
                    (byte and 0xF0u) == 0xE0u.toUByte() -> {
                        (data[index++].toInt() and 0x0f shl 12)
                            .or(data[index++].toInt() and 0x3f shl 6)
                            .or(data[index++].toInt() and 0x3f)
                            .toUShort()
                    }
                    else -> error("logic failure")
                }
                when {
                    (byte and 0x80u) == 0x00u.toUByte() ->
                        if (char !in 0x0001u..0x007Fu)
                            throw ClassFileException("invalid utf8 format: invalid for 1 bytes: ${char}")
                    (byte and 0xE0u) == 0xC0u.toUByte() ->
                        if (char !in 0x0080u..0x07FFu && char != 0x0000u.toUShort())
                            throw ClassFileException("invalid utf8 format: invalid for 2 bytes: ${char}")
                    (byte and 0xF0u) == 0xE0u.toUByte() ->
                        if (char !in 0x0800u..0xFFFFu)
                            throw ClassFileException("invalid utf8 format: invalid for 3 bytes: ${char}")
                }
                shorts[charIndex++] = char
            }

            return shorts
        }

        private fun countUtf8Chars(data: ByteArray): Int {
            if (data.isEmpty()) return 0
            var count = 0
            var index = 0

            while (index < data.size) {
                val byte = data[index].toUByte()
                count++
                index += when {
                    (byte and 0x80u) == 0x00u.toUByte() -> 1
                    (byte and 0xE0u) == 0xC0u.toUByte() -> 2
                    (byte and 0xF0u) == 0xE0u.toUByte() -> 3
                    else -> throw ClassFileException("invalid byte")
                }
            }
            if (index != data.size)
                throw ClassFileException("invalid byte")
            return count
        }

        private fun checkIndices(hasFlags: BitSet, data: Array<UShortArray?>) {
            for (datum in data) {
                if (datum == null) continue
                when (datum[0].toInt()) {
                    1,// ConstantUtf8: no ref
                    3,// ConstantInteger: no ref
                    4,// ConstantFloat: no ref
                    5,// ConstantLong: no ref
                    6,// ConstantDouble: no ref
                    -> {
                    }
                    7, // ConstantClass: name index(u2)
                    8, // ConstantString: value index(u2)
                    9, // ConstantFieldRef: class index(u2) and name and type index(u2)
                    10, // ConstantMethodRef: class index(u2) and name and type index(u2)
                    11, // ConstantInterfaceMethodRef: class index(u2) and name and type index(u2)
                    12, // ConstantNameAndType: name index(u2) and descriptor index(u2)
                    15, // ConstantMethodHandle: kind(u1) and reference index(u2)
                    16, // ConstantMethodType: descriptor index(u2)
                    //17, not here
                    //18, not here
                    19, // ConstantModule: name index(u2)
                    20, // ConstantPackage: name index(u2)
                    -> {
                        for (i in 1 until datum.size) {
                            if (!hasFlags[datum[i].toInt()])
                                throw ClassFileException("invalid index: ${datum[i].toInt()}")
                        }
                    }
                    17, // ConstantDynamic: bootstrap method attr index(u2) and name and type index (u2)
                    18, // ConstantInvokeDynamic: bootstrap method attr index(u2) and name and type index (u2)
                    -> {
                        if (!hasFlags[datum[2].toInt()])
                            throw ClassFileException("invalid index: ${datum[2].toInt()}")
                    }
                    else -> error("logic failure")
                }
            }
        }

        private fun initializeConstantPool(data: Array<UShortArray?>, values: Array<Constant?>): Int {
            var initialized = 0
            for (i in data.indices) {
                if (values[i] != null) continue
                val datum = data[i] ?: continue
                values[i] = when (val tag = datum[0].toInt()) {
                    // ConstantUtf8
                    1 -> ConstantUtf8(String(CharArray(datum.size - 1) { datum[it - 1].toInt().toChar() }))
                    // ConstantInteger: value(u4)
                    3 -> ConstantInteger((datum[1].toInt() shl 16)
                        .or(datum[2].toInt() shl 0))
                    // ConstantFloat: value(u4)
                    4 -> ConstantFloat((datum[1].toInt() shl 16)
                        .or(datum[2].toInt() shl 0))
                    // ConstantLong: value(u8)
                    5 -> ConstantLong((datum[1].toLong() shl 48)
                        .or(datum[2].toLong() shl 32)
                        .or(datum[3].toLong() shl 16)
                        .or(datum[4].toLong() shl 0))
                    // ConstantDouble: value(u8)
                    6 -> ConstantDouble((datum[1].toLong() shl 48)
                        .or(datum[2].toLong() shl 32)
                        .or(datum[3].toLong() shl 16)
                        .or(datum[4].toLong() shl 0))
                    // ConstantClass: name index(u2)
                    7 -> ConstantClass(values.getUtf8(datum[1]) ?: continue)
                    // ConstantString: value index(u2)
                    8 -> ConstantString(values.getUtf8(datum[1]) ?: continue)
                    // ConstantFieldRef: class index(u2) and name and type index(u2)
                    9 -> ConstantFieldRef(values.getClass(datum[1]) ?: continue, values.getNameAndType(datum[2]) ?: continue)
                    // ConstantMethodRef: class index(u2) and name and type index(u2)
                    10 -> ConstantMethodRef(values.getClass(datum[1]) ?: continue, values.getNameAndType(datum[2]) ?: continue)
                    // ConstantInterfaceMethodRef: class index(u2) and name and type index(u2)
                    11 -> ConstantInterfaceMethodRef(values.getClass(datum[1]) ?: continue, values.getNameAndType(datum[2]) ?: continue)
                    // ConstantNameAndType: name index(u2) and descriptor index(u2)
                    12 -> ConstantNameAndType(values.getUtf8(datum[1]) ?: continue, values.getUtf8(datum[2]) ?: continue)
                    // ConstantMethodHandle: kind(u1) and reference index(u2)
                    15 -> {
                        when (datum[1].toInt()) {
                            1 -> ConstantMethodHandle.getField(values.getFieldRef(datum[2]) ?: continue)
                            2 -> ConstantMethodHandle.getStatic(values.getFieldRef(datum[2]) ?: continue)
                            3 -> ConstantMethodHandle.putField(values.getFieldRef(datum[2]) ?: continue)
                            4 -> ConstantMethodHandle.putStatic(values.getFieldRef(datum[2]) ?: continue)
                            5 -> ConstantMethodHandle.invokeVirtual(values.getMethodRef(datum[2]) ?: continue)
                            6 -> ConstantMethodHandle.invokeStatic(values.getAnyMethodRef(datum[2]) ?: continue)
                            7 -> ConstantMethodHandle.invokeSpecial(values.getAnyMethodRef(datum[2]) ?: continue)
                            8 -> ConstantMethodHandle.newInvokeSpecial(values.getMethodRef(datum[2]) ?: continue)
                            9 -> ConstantMethodHandle.invokeInterface(values.getInterfaceMethodRef(datum[2]) ?: continue)
                            else -> throw ClassFileException("invalid reference kind: ${datum[1]}")
                        }
                    }
                    // ConstantMethodType: descriptor index(u2)
                    16 -> ConstantMethodType(values.getUtf8(datum[1]) ?: continue)
                    // ConstantDynamic: bootstrap method attr index(u2) and name and type index (u2)
                    17 -> ConstantDynamic(datum[1], values.getNameAndType(datum[2]) ?: continue)
                    // ConstantInvokeDynamic: bootstrap method attr index(u2) and name and type index (u2)
                    18 -> ConstantInvokeDynamic(datum[1], values.getNameAndType(datum[2]) ?: continue)
                    // ConstantModule: name index(u2)
                    19 -> ConstantModule(values.getUtf8(datum[1]) ?: continue)
                    // ConstantPackage: name index(u2)
                    20 -> ConstantPackage(values.getUtf8(datum[1]) ?: continue)
                    else -> error("logic failure $tag")
                }
                initialized++
            }
            return initialized
        }

        private inline fun <reified T : Constant> Constant.castConstant(index: UShort) = 
            this as? T ?: throw ClassFileException("constant pool#$index is not ${T::class.simpleName}")

        private fun Array<Constant?>.getRef(index: UShort) = this[index.toInt()]?.castConstant<ConstantRef>(index)
        private fun Array<Constant?>.getAnyMethodRef(index: UShort) = this[index.toInt()]?.castConstant<ConstantAnyMethodRef>(index)
        private fun Array<Constant?>.getLoadable(index: UShort) = this[index.toInt()]?.castConstant<LoadableConstant>(index)
        private fun Array<Constant?>.getValue(index: UShort) = this[index.toInt()]?.castConstant<ConstantValue>(index)
        private fun Array<Constant?>.getUtf8(index: UShort) = this[index.toInt()]?.castConstant<ConstantUtf8>(index)
        private fun Array<Constant?>.getInteger(index: UShort) = this[index.toInt()]?.castConstant<ConstantInteger>(index)
        private fun Array<Constant?>.getFloat(index: UShort) = this[index.toInt()]?.castConstant<ConstantFloat>(index)
        private fun Array<Constant?>.getLong(index: UShort) = this[index.toInt()]?.castConstant<ConstantLong>(index)
        private fun Array<Constant?>.getDouble(index: UShort) = this[index.toInt()]?.castConstant<ConstantDouble>(index)
        private fun Array<Constant?>.getClass(index: UShort) = this[index.toInt()]?.castConstant<ConstantClass>(index)
        private fun Array<Constant?>.getString(index: UShort) = this[index.toInt()]?.castConstant<ConstantString>(index)
        private fun Array<Constant?>.getFieldRef(index: UShort) = this[index.toInt()]?.castConstant<ConstantFieldRef>(index)
        private fun Array<Constant?>.getMethodRef(index: UShort) = this[index.toInt()]?.castConstant<ConstantMethodRef>(index)
        private fun Array<Constant?>.getInterfaceMethodRef(index: UShort) = this[index.toInt()]?.castConstant<ConstantInterfaceMethodRef>(index)
        private fun Array<Constant?>.getNameAndType(index: UShort) = this[index.toInt()]?.castConstant<ConstantNameAndType>(index)
        private fun Array<Constant?>.getMethodHandle(index: UShort) = this[index.toInt()]?.castConstant<ConstantMethodHandle>(index)
        private fun Array<Constant?>.getMethodType(index: UShort) = this[index.toInt()]?.castConstant<ConstantMethodType>(index)
        private fun Array<Constant?>.getDynamic(index: UShort) = this[index.toInt()]?.castConstant<ConstantDynamic>(index)
        private fun Array<Constant?>.getInvokeDynamic(index: UShort) = this[index.toInt()]?.castConstant<ConstantInvokeDynamic>(index)
        private fun Array<Constant?>.getModule(index: UShort) = this[index.toInt()]?.castConstant<ConstantModule>(index)
        private fun Array<Constant?>.getPackage(index: UShort) = this[index.toInt()]?.castConstant<ConstantPackage>(index)
    }
}

sealed class ConstantRef(tag: UByte, sinceVersion: ClassFileVersion) : Constant(tag, sinceVersion)
sealed class ConstantAnyMethodRef(tag: UByte, sinceVersion: ClassFileVersion) : ConstantRef(tag, sinceVersion)
sealed class LoadableConstant(tag: UByte, sinceVersion: ClassFileVersion) : Constant(tag, sinceVersion)
sealed class ConstantValue(tag: UByte, sinceVersion: ClassFileVersion) : LoadableConstant(tag, sinceVersion)

/**
 * The constant pool element holds a unicode codepoint list, a string in java.
 * @spec 4.4.7
 */
data class ConstantUtf8(val value: String) :
    Constant(1u, CV45_3)

/**
 * The constant pool element represents a integer value.
 * @spec 4.4.4
 */
data class ConstantInteger(val value: Int) :
    ConstantValue(3u, CV45_3)

/**
 * The constant pool element represents a float value.
 * @spec 4.4.4
 */
data class ConstantFloat(val value: Int) :
    ConstantValue(4u, CV45_3) {
    constructor(value: Float) : this(java.lang.Float.floatToRawIntBits(value))
}

/**
 * The constant pool element represents a long value.
 * @spec 4.4.5
 */
data class ConstantLong(val value: Long) :
    ConstantValue(5u, CV45_3)

/**
 * The constant pool element represents a double value.
 * @spec 4.4.5
 */
data class ConstantDouble(val value: Long) :
    ConstantValue(6u, CV45_3) {
    constructor(value: Double) : this(java.lang.Double.doubleToRawLongBits(value))
}

/**
 * The constant pool element represents a reference type.
 * @spec 4.4.1
 */
data class ConstantClass(val name: ConstantUtf8) :
    Constant(7u, CV45_3)

/**
 * The constant pool element represents a String instance.
 * @spec 4.4.3
 */
data class ConstantString(val string: ConstantUtf8) :
    ConstantValue(8u, CV45_3)

/**
 * The constant pool element represents a field.
 * @spec 4.4.2
 */
data class ConstantFieldRef(val owner: ConstantClass, val nameAndType: ConstantNameAndType) :
    ConstantRef(9u, CV45_3)

/**
 * The constant pool element represents a method.
 * @spec 4.4.2
 */
data class ConstantMethodRef(val owner: ConstantClass, val nameAndType: ConstantNameAndType) :
    ConstantAnyMethodRef(10u, CV45_3)

/**
 * The constant pool element represents a method in interface.
 * @spec 4.4.2
 */
data class ConstantInterfaceMethodRef(val owner: ConstantClass, val nameAndType: ConstantNameAndType) :
    ConstantAnyMethodRef(11u, CV45_3)

/**
 * The constant pool element represents a name and descriptor pair. 
 * @spec 4.4.6
 */
data class ConstantNameAndType(val name: ConstantUtf8, val descriptor: ConstantUtf8) :
    Constant(12u, CV45_3)

/**
 * The constant pool element represents a `java.lang.invoke.MethodHandle` instance.
 * @spec 4.4.8
 */
@AutoToString("kind", "reference")
class ConstantMethodHandle private constructor(val kind: Kind, val reference: ConstantRef) :
    LoadableConstant(15u, CV51_0) {
    enum class Kind(val id: UByte) {
        GetField(1u),
        GetStatic(2u),
        PutField(3u),
        PutStatic(4u),
        InvokeVirtual(5u),
        InvokeStatic(6u),
        InvokeSpecial(7u),
        NewInvokeSpecial(8u),
        InvokeInterface(9u),
    }

    companion object {
        fun getField(reference: ConstantFieldRef) = ConstantMethodHandle(Kind.GetField, reference)
        fun getStatic(reference: ConstantFieldRef) = ConstantMethodHandle(Kind.GetStatic, reference)
        fun putField(reference: ConstantFieldRef) = ConstantMethodHandle(Kind.PutField, reference)
        fun putStatic(reference: ConstantFieldRef) = ConstantMethodHandle(Kind.PutStatic, reference)
        fun invokeVirtual(reference: ConstantMethodRef) = ConstantMethodHandle(Kind.InvokeVirtual, reference)
        fun invokeStatic(reference: ConstantAnyMethodRef) = ConstantMethodHandle(Kind.InvokeStatic, reference)
        fun invokeSpecial(reference: ConstantAnyMethodRef) = ConstantMethodHandle(Kind.InvokeSpecial, reference)
        fun newInvokeSpecial(reference: ConstantMethodRef) = ConstantMethodHandle(Kind.NewInvokeSpecial, reference)
        fun invokeInterface(reference: ConstantInterfaceMethodRef) = ConstantMethodHandle(Kind.InvokeInterface, reference)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConstantMethodHandle

        if (kind != other.kind) return false
        if (reference != other.reference) return false

        return true
    }

    override fun hashCode(): Int {
        var result = kind.hashCode()
        result = 31 * result + reference.hashCode()
        return result
    }
}

/**
 * The constant pool element represents a `java.lang.invoke.MethodType` instance.
 * @spec 4.4.9
 */
data class ConstantMethodType(val descriptor: ConstantUtf8) :
    LoadableConstant(16u, CV51_0)

// TODO: make bootstrapMethod reference
/**
 * The constant pool element represents a dynamically computing constants.
 * @spec 4.4.10
 */
data class ConstantDynamic(val bootstrapMethod: UShort, val nameAndType: ConstantNameAndType) :
    LoadableConstant(17u, CV55_0)

/**
 * The constant pool element represents a dynamic linking function.
 * @spec 4.4.10
 */
data class ConstantInvokeDynamic(val bootstrapMethod: UShort, val nameAndType: ConstantNameAndType) :
    Constant(18u, CV51_0)

/**
 * The constant pool element represents a module.
 * @spec 4.4.11
 */
data class ConstantModule(val name: ConstantUtf8) :
    Constant(19u, CV53_0)

/**
 * The constant pool element represents a package.
 * @spec 4.4.12
 */
data class ConstantPackage(val name: ConstantUtf8) :
    Constant(20u, CV53_0)

@GenerateVisitor(Constant::class)
abstract class ConstantVisitor<R>
