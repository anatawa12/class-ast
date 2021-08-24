package com.anatawa12.classAst.annotation

import com.anatawa12.classAst.*

/**
 * The `element_value` structure
 * @spec 4.7.16.1
 */
sealed class ElementValue private constructor(val tag: UByte) {
    private constructor(tag: kotlin.Char): this(tag.code.toUByte())

    class Byte(val value: ConstantInteger): ElementValue('B')
    class Char(val value: ConstantInteger): ElementValue('C')
    class Double(val value: ConstantDouble): ElementValue('D')
    class Float(val value: ConstantFloat): ElementValue('F')
    class Int(val value: ConstantInteger): ElementValue('I')
    class Long(val value: ConstantLong): ElementValue('J')
    class Short(val value: ConstantInteger): ElementValue('S')
    class Boolean(val value: ConstantInteger): ElementValue('Z')
    class String(val value: ConstantUtf8): ElementValue('s')
    class Enum(val type: ConstantUtf8, val name: ConstantUtf8): ElementValue('e')
    class Class(val value: ConstantClass): ElementValue('c')
    class Annotation(val value: ClassFileAnnotation): ElementValue('@')
    class Array(val value: MutableList<ElementValue>): ElementValue('[')
}
