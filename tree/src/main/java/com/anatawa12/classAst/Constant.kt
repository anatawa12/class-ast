package com.anatawa12.classAst

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.autoVisitor.GenerateAccept
import com.anatawa12.autoVisitor.GenerateVisitor
import com.anatawa12.autoVisitor.HasVisitor


/**
 * The Constant Pool Elements
 * See Jvm Specification section 4.4
 *
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

data class ConstantUtf8(val value: String) :
    Constant(1u, CV45_3)

data class ConstantInteger(val value: Int) :
    ConstantValue(3u, CV45_3)

data class ConstantFloat(val value: Int) :
    ConstantValue(4u, CV45_3) {
    constructor(value: Float) : this(java.lang.Float.floatToRawIntBits(value))
}

data class ConstantLong(val value: Long) :
    ConstantValue(5u, CV45_3)

data class ConstantDouble(val value: Long) :
    ConstantValue(6u, CV45_3) {
    constructor(value: Double) : this(java.lang.Double.doubleToRawLongBits(value))
}

data class ConstantClass(val name: ConstantUtf8) :
    Constant(7u, CV45_3)

data class ConstantString(val string: ConstantUtf8) :
    ConstantValue(8u, CV45_3)

data class ConstantFieldRef(val owner: ConstantClass, val nameAndType: ConstantNameAndType) :
    ConstantRef(9u, CV45_3)

data class ConstantMethodRef(val owner: ConstantClass, val nameAndType: ConstantNameAndType) :
    ConstantAnyMethodRef(10u, CV45_3)

data class ConstantInterfaceMethodRef(val owner: ConstantClass, val nameAndType: ConstantNameAndType) :
    ConstantAnyMethodRef(11u, CV45_3)

data class ConstantNameAndType(val name: ConstantUtf8, val descriptor: ConstantUtf8) :
    Constant(12u, CV45_3)

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

data class ConstantMethodType(val descriptor: ConstantUtf8) :
    LoadableConstant(16u, CV51_0)

// TODO: make bootstrapMethod reference
data class ConstantDynamic(val bootstrapMethod: Int, val nameAndType: ConstantNameAndType) :
    LoadableConstant(17u, CV55_0)

data class ConstantInvokeDynamic(val bootstrapMethod: Int, val nameAndType: ConstantNameAndType) :
    Constant(18u, CV51_0)

data class ConstantModule(val name: ConstantUtf8) :
    Constant(19u, CV53_0)

data class ConstantPackage(val name: ConstantUtf8) :
    Constant(20u, CV53_0)

@GenerateVisitor(Constant::class)
abstract class ConstantVisitor<R>
