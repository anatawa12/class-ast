package com.anatawa12.classAst.attributes

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.ConstantClass
import com.anatawa12.classAst.ConstantUtf8
import com.anatawa12.classAst.code.Code
import com.anatawa12.classAst.code.Instruction
import com.anatawa12.classAst.code.StackMapFrame

/**
 * @spec 4.7.3
 */
@AutoToString("maxStack", "macLocals", "code", "exceptions", "attributes")
class CodeAttribute(
    name: ConstantUtf8,
    var maxStack: UShort,
    var macLocals: UShort,
    var code: Code,
    val exceptions: MutableList<ExceptionTable>,
    val attributes: MutableList<Attribute>,
) : Attribute(name, "Code")

@AutoToString(
    "startInsn",
    "endInsn",
    "handlerInsn",
    "catchType",
)
class ExceptionTable(
    var startInsn: Instruction,
    var endInsn: Instruction,
    // null for end of method
    var handlerInsn: Instruction?,
    var catchType: ConstantClass?,
)

@AutoToString("entries")
class StackMapTableAttribute(
    name: ConstantUtf8,
    val entries: MutableList<StackMapFrame>,
) : Attribute(name, "StackMapTable")

@AutoToString("table")
class LineNumberTableAttribute(
    name: ConstantUtf8,
    val table: MutableList<LineNumberPair>,
) : Attribute(name, "LineNumberTable")

@AutoToString("startInsn", "lineNumber")
class LineNumberPair(
    var startInsn: Instruction,
    var lineNumber: UShort,
)

@AutoToString("table")
class LocalVariableTableAttribute(
    name: ConstantUtf8,
    val table: MutableList<LocalVariableTableEntry>,
) : Attribute(name, "LocalVariableTable")

@AutoToString("startInsn", "endInsn", "name", "descriptor", "index")
class LocalVariableTableEntry(
    var startInsn: Instruction,
    var endInsn: Instruction,
    var name: ConstantUtf8,
    var descriptor: ConstantUtf8,
    var index: UShort,
)

@AutoToString("table")
class LocalVariableTypeTableAttribute(
    name: ConstantUtf8,
    val table: MutableList<LocalVariableTypeTableEntry>,
) : Attribute(name, "LocalVariableTypeTable")

@AutoToString("startInsn", "endInsn", "name", "signature", "index")
class LocalVariableTypeTableEntry(
    var startInsn: Instruction,
    var endInsn: Instruction,
    var name: ConstantUtf8,
    var signature: ConstantUtf8,
    var index: UShort,
)
