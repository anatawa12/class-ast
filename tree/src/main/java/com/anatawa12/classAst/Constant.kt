package com.anatawa12.classAst

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.autoVisitor.GenerateAccept
import com.anatawa12.autoVisitor.GenerateVisitor
import com.anatawa12.autoVisitor.HasVisitor


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
data class ConstantDynamic(val bootstrapMethod: Int, val nameAndType: ConstantNameAndType) :
    LoadableConstant(17u, CV55_0)

/**
 * The constant pool element represents a dynamic linking function.
 * @spec 4.4.10
 */
data class ConstantInvokeDynamic(val bootstrapMethod: Int, val nameAndType: ConstantNameAndType) :
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
