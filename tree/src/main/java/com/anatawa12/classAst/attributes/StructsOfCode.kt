package com.anatawa12.classAst.attributes

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.ConstantClass
import com.anatawa12.classAst.ConstantUtf8
import com.anatawa12.classAst.code.Instruction

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

@AutoToString("startInsn", "lineNumber")
class LineNumberPair(
    var startInsn: Instruction,
    var lineNumber: UShort,
)

@AutoToString("startInsn", "endInsn", "name", "descriptor", "index")
class LocalVariableTableEntry(
    var startInsn: Instruction,
    var endInsn: Instruction,
    var name: ConstantUtf8,
    var descriptor: ConstantUtf8,
    var index: UShort,
)

@AutoToString("startInsn", "endInsn", "name", "signature", "index")
class LocalVariableTypeTableEntry(
    var startInsn: Instruction,
    var endInsn: Instruction,
    var name: ConstantUtf8,
    var signature: ConstantUtf8,
    var index: UShort,
)
