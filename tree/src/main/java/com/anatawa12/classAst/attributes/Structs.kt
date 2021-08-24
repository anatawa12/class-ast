package com.anatawa12.classAst.attributes

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.*

@AutoToString("innerClass", "outerClass", "innerName", "flags")
class InnerClassInformation(
    var innerClass: ConstantClass,
    var outerClass: ConstantClass?,
    var innerName: ConstantUtf8,
    var flags: AccessFlags,
)

@AutoToString("accessFlags")
class ParameterNameAccessPair(
    val name: ConstantUtf8?,
    val accessFlags: AccessFlags,
)

@AutoToString("name", "descriptor", "attributes")
class RecordComponent(
    var name: ConstantUtf8,
    var descriptor: ConstantUtf8,
    val attributes: MutableList<Attribute>,
)

@AutoToString("bootstrapMethod", "arguments")
class BootstrapMethod(
    var bootstrapMethod: ConstantMethodHandle,
    val arguments: MutableList<LoadableConstant>
)
