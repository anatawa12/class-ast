package com.anatawa12.classAst.code

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.ConstantClass

sealed class StackMapFrame(val target: Instruction)
// same_frame, 0-63
@AutoToString("target")
class SameFrame(target: Instruction) : StackMapFrame(target)
// same_frame_extended, 251
@AutoToString("target")
class SameFrameExtended(target: Instruction) : StackMapFrame(target)
// same_locals_1_stack_item_frame, 64-127
@AutoToString("target", "stack")
class SameLocals1StackItemFrame(target: Instruction, val stack: VerificationTypeInfo) : StackMapFrame(target)
// same_locals_1_stack_item_frame_extended, 247
@AutoToString("target", "stack")
class SameLocals1StackItemFrameExtended(target: Instruction, val stack: VerificationTypeInfo) : StackMapFrame(target)
// chop_frame, 248-250
@AutoToString("target", "count")
class ChopFrame(target: Instruction, val count: UByte) : StackMapFrame(target) {
    init {
        require(count in 1u..3u) { "chop count must be in 1..3" }
    }
}
// append_frame, 252-254
@AutoToString("target", "locals")
class AppendFrame(target: Instruction, val locals: List<VerificationTypeInfo>) : StackMapFrame(target) {
    init {
        require(locals.size in 1..3) { "you can't append four or more" }
    }
}
// full_frame, 255
@AutoToString("target", "locals", "stacks")
class FullFrame(
    target: Instruction,
    val locals: List<VerificationTypeInfo>,
    val stacks: List<VerificationTypeInfo>,
) : StackMapFrame(target) {
}

sealed class VerificationTypeInfo private constructor(
    val tag: UByte,
) {
    @AutoToString()
    object Top : VerificationTypeInfo(0u)
    @AutoToString()
    object Integer : VerificationTypeInfo(1u)
    @AutoToString()
    object Float : VerificationTypeInfo(2u)
    @AutoToString()
    object Null : VerificationTypeInfo(5u)
    @AutoToString()
    object UninitializedThis : VerificationTypeInfo(6u)
    @AutoToString("type")
    class Object(val type: ConstantClass) : VerificationTypeInfo(7u)
    @AutoToString("newAt")
    class Uninitialized(val newAt: Instruction) : VerificationTypeInfo(8u)
    @AutoToString()
    object Long : VerificationTypeInfo(4u)
    @AutoToString()
    object Double : VerificationTypeInfo(3u)
}
