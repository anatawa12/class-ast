package com.anatawa12.classAst

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.attributes.Attribute

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
)
