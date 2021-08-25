package com.anatawa12.classAst

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.attributes.Attribute
import com.anatawa12.classAst.io.ClassFileReader
import com.anatawa12.classAst.io.ClassFileException

@AutoToString(
    "version",
    "constants",
    "access",
    "thisClass",
    "superClass",
    "interfaces",
    "fields",
    "methods",
    "attributes",
)
class ClassFile(
    var version: ClassFileVersion,
    val constants: MutableList<Constant>,
    var access: AccessFlags,
    var thisClass: ConstantClass,
    var superClass: ConstantClass,
    val interfaces: MutableList<ConstantClass>,
    val fields: MutableList<FieldData>,
    val methods: MutableList<MethodData>,
    val attributes: MutableList<Attribute>,
) {
    companion object Reader {
        fun readClassFile(reader: ClassFileReader) : ClassFile {
            // heading
            if (reader.readU4() != 0xCAFEBABEu)
                throw ClassFileException("invalid magic. it must be 0xCAFEBABE")
            val minorVersion = reader.readU2()
            val majorVersion = reader.readU2()

            // constant pool
            val constantPool = Constant.readConstantPool(reader)
            reader.setConstantPool(constantPool)

            val access = AccessFlags(reader.readU2())
            TODO()
        }
    }
}
