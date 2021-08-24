package com.anatawa12.classAst.annotation

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.code.ExceptionTable
import com.anatawa12.classAst.code.Instruction

/**
 * The structure corresponds values of `target_type` and `target_path` of `type_annotation`.
 * @spec 4.7.20.1
 */
sealed class TargetInfo private constructor(id: UByte) {


    /**
     * Instance of this class specifies the ([index]+1)-th type parameter in type parameters of the class.
     *
     * @constructor Constructs [TypeParameterOfClass].
     * @param index The value of [index].
     */
    data class TypeParameterOfClass(
        /** the index of type parameter. See [TypeParameterOfClass] for details. */
        val index: UByte,
    ) : TargetInfo(0x00u)

    /**
     * Instance of this class specifies the ([index]+1)-th type parameter in type parameters of the method.
     *
     * @constructor Constructs [TypeParameterOfMethod].
     * @param index The value of [index].
     */
    data class TypeParameterOfMethod(
        /** the index of type parameter. See [TypeParameterOfMethod] for details. */
        val index: UByte,
    ) : TargetInfo(0x01u)

    /**
     * If [index] is 65535, instance of this class specifies super class.
     * Otherwise, instance of this class specifies the ([index]+1)-th super interface in super interfaces of the class.
     *
     * @constructor Constructs [SuperType].
     * @param index The value of [index].
     */
    data class SuperType(
        /** The index of super interface or 65535. See [SuperType] for details. */
        val index: UShort,
    ) : TargetInfo(0x10u)

    /**
     * Instance of this class specifies the ([boundIndex]+1)-th bound of
     * ([typeParameterIndex]+1)-th type parameter of the class.
     *
     * @constructor Constructs [TypeParameterBoundOfClass].
     * @param typeParameterIndex The value of [typeParameterIndex].
     * @param boundIndex The value of [boundIndex].
     */
    data class TypeParameterBoundOfClass(
        /** The index of type parameter. see [TypeParameterBoundOfClass] for details. */
        val typeParameterIndex: Int,
        /** The index of type bound. see [TypeParameterBoundOfClass] for details. */
        val boundIndex: Int,
    ) : TargetInfo(0x11u)

    /**
     * Instance of this class specifies the ([boundIndex]+1)-th bound of
     * ([typeParameterIndex]+1)-th type parameter of the method.
     *
     * @constructor Constructs [TypeParameterBoundOfMethod].
     * @param typeParameterIndex The value of [typeParameterIndex].
     * @param boundIndex The value of [boundIndex].
     */
    data class TypeParameterBoundOfMethod(
        /** The index of type parameter. see [TypeParameterBoundOfMethod] for details. */
        val typeParameterIndex: Int,
        /** The index of type bound. see [TypeParameterBoundOfMethod] for details. */
        val boundIndex: Int,
    ) : TargetInfo(0x12u)

    /**
     * This specifies the type of the field.
     */
    @AutoToString
    object FieldType : TargetInfo(0x13u)

    /**
     * This specifies the type of return type of the method.
     */
    @AutoToString
    object ReturnType : TargetInfo(0x14u)

    /**
     * This specifies the receiver type of method or constructor.
     */
    @AutoToString
    object ReceiverType : TargetInfo(0x15u)

    /**
     * Instance of this class specifies ([index]+1)-th formal parameter type.
     *
     * @constructor Constructs [FormalParameterType].
     * @param index The value of [index].
     */
    data class FormalParameterType(
        /** The index of formal parameter. See [FormalParameterType] for details. */
        val index: UByte,
    ) : TargetInfo(0x16u)

    /**
     * Instance of this class specifies ([index]+1)-th thows type.
     *
     * @constructor Constructs [ThrowsType].
     * @param index The value of [index].
     */
    data class ThrowsType(
        /** The index of throws type. See [ThrowsType] for details. */
        val index: UShort,
    ) : TargetInfo(0x17u)


    /**
     * Instance of this class specifies a local variable matches the [table].
     *
     * @constructor Constructs [LocalVariableType].
     * @param table the value of [table].
     */
    data class LocalVariableType(
        /** The list of variables this annotation targets. */
        val table: MutableList<RangedLocalVariable>,
    ) : TargetInfo(0x40u)

    /**
     * Instance of this class specifies a local variable matches the [table], declared in try-with-resources.
     *
     * @constructor Constructs [TryWithResourceVariableType].
     * @param table the value of [table].
     */
    data class TryWithResourceVariableType(
        /** The list of variables this annotation targets. */
        val table: MutableList<RangedLocalVariable>,
    ) : TargetInfo(0x41u)

    /**
     * Instance of this class specifies the handling type of [exceptionTable].
     *
     * @constructor Constructs [TryWithResourceVariableType].
     * @param exceptionTable the value of [exceptionTable].
     */
    data class CatchParameter(
        /** The ExceptionTable. See [CatchParameter] for details. */
        val exceptionTable: ExceptionTable,
    ) : TargetInfo(0x42u)

    /**
     * Instance of this class specifies the type of `instanceof` expression.
     *
     * @constructor Constructs [InstanceOfType].
     * @param instruction The value of [instruction].
     */
    data class InstanceOfType(
        /** The instruction corresponding `instanceof` expression. */
        val instruction: Instruction,
    ) : TargetInfo(0x43u)

    /**
     * Instance of this class specifies the type of `new` expression.
     *
     * @constructor Constructs [InstanceOfType].
     * @param instruction The value of [instruction].
     */
    data class NewObject(
        /** The instruction corresponding `new` expression. */
        val instruction: Instruction,
    ) : TargetInfo(0x44u)

    /**
     * Instance of this class specifies the type parameters of `OwnerClass` of `OwnerClass<>::<>new`.
     * For that of `method`, use [GenericConstructorReferenceParameter]
     *
     * @constructor Constructs [ConstructorReferenceOwner].
     * @param instruction The value of [instruction].
     */
    data class ConstructorReferenceOwner(
        /** The instruction corresponding method reference expression. */
        val instruction: Instruction,
    ) : TargetInfo(0x45u)

    /**
     * Instance of this class specifies the type parameters of `OwnerClass` of `OwnerClass<>::<>method`.
     * For that of `method`, use [GenericMethodReferenceParameter]
     *
     * @constructor Constructs [MethodReferenceOwner].
     * @param instruction The value of [instruction].
     */
    data class MethodReferenceOwner(
        /** The instruction corresponding method reference expression. */
        val instruction: Instruction,
    ) : TargetInfo(0x46u)

    /**
     * Instance of this class specifies the ([index]+1)-th type parameters of the class an instance will be cast to.
     * @constructor Constructs [CastExpression].
     * @param instruction The value of [instruction].
     * @param index The value of [index].
     */
    data class CastExpression(
        /** The instruction corresponding cast expression. */
        val instruction: Instruction,
        /** the index of type parameter. See [CastExpression] for details. */
        val index: UByte,
    ) : TargetInfo(0x47u)

    /**
     * Instance of this class specifies the ([index]+1)-th type parameters of the constructor the [instruction] targets.
     * For type parameters of the type constructing, use [NewObject].
     *
     * @constructor Constructs [CastExpression].
     * @param instruction The value of [instruction].
     * @param index The value of [index].
     */
    data class GenericConstructorParameter(
        /** The instruction corresponding constructor invocation. */
        val instruction: Instruction,
        /** the index of type parameter. See [GenericConstructorParameter] for details. */
        val index: UByte,
    ) : TargetInfo(0x48u)

    /**
     * Instance of this class specifies the ([index]+1)-th type parameters of the method the [instruction] targets.
     *
     * @constructor Constructs [GenericMethodParameter].
     * @param instruction The value of [instruction].
     * @param index The value of [index].
     */
    data class GenericMethodParameter(
        /** The instruction corresponding method invocation. */
        val instruction: Instruction,
        /** the index of type parameter. See [GenericMethodParameter] for details. */
        val index: UByte,
    ) : TargetInfo(0x49u)

    /**
     * Instance of this class specifies the type parameters of constructor of `OwnerClass<>::<>new`.
     * For that of `OwnerClass`, use [ConstructorReferenceOwner]
     *
     * @constructor Constructs [GenericConstructorReferenceParameter].
     * @param instruction The value of [instruction].
     * @param index The value of [index].
     */
    data class GenericConstructorReferenceParameter(
        /** The instruction corresponding method invocation. */
        val instruction: Instruction,
        /** the index of type parameter. See [GenericConstructorReferenceParameter] for details. */
        val index: UByte,
    ) : TargetInfo(0x4Au)

    /**
     * Instance of this class specifies the type parameters of `method` of `OwnerClass<>::<>method`.
     * For that of `OwnerClass`, use [MethodReferenceOwner]
     *
     * @constructor Constructs [GenericMethodReferenceParameter].
     * @param instruction The value of [instruction].
     * @param index The value of [index].
     */
    data class GenericMethodReferenceParameter(
        /** The instruction corresponding method invocation. */
        val instruction: Instruction,
        /** the index of type parameter. See [GenericMethodReferenceParameter] for details. */
        val index: UByte,
    ) : TargetInfo(0x4Bu)
}

/**
 * A structure specifies local variable with range and index.
 * @constructor Constructs [RangedLocalVariable].
 * @param start The value of [start].
 * @param end The value of [end].
 * @param index The value of [index].
 */
data class RangedLocalVariable(
    /** The start instruction of range. inclusive. */
    val start: Instruction,
    /** The last instruction of range. null if end of code. exclusive. */
    val end: Instruction?,
    /** The index of local variable */
    val index: UShort,
)
