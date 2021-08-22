package com.anatawa12.classAst.attributes

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.*

/**
 * @spec 4.7.2
 */
@AutoToString("value")
class ConstantValueAttribute(name: ConstantUtf8, var value: ConstantValue) : Attribute(name, "ConstantValue")

@AutoToString("exceptions")
class ExceptionsAttribute(
    name: ConstantUtf8,
    val exceptions: MutableList<ConstantClass>,
) : Attribute(name, "Exceptions")

@AutoToString("classes")
class InnerClassesAttribute(
    name: ConstantUtf8,
    val classes: MutableList<InnerClassInformation>,
) : Attribute(name, "InnerClasses")

@AutoToString("innerClass", "outerClass", "innerName", "flags")
class InnerClassInformation(
    var innerClass: ConstantClass,
    var outerClass: ConstantClass?,
    var innerName: ConstantUtf8,
    var flags: AccessFlags,
)

@AutoToString("outerClass", "method")
class EnclosingMethodAttribute(
    name: ConstantUtf8,
    var outerClass: ConstantClass,
    var method: ConstantNameAndType?,
) : Attribute(name, "EnclosingMethod")

@AutoToString()
class SyntheticAttribute(name: ConstantUtf8) : Attribute(name, "Synthetic")

@AutoToString("signature")
class SignatureAttribute(name: ConstantUtf8, var signature: ConstantUtf8) : Attribute(name, "Signature")

@AutoToString("sourceFile")
class SourceFileAttribute(
    name: ConstantUtf8,
    var sourceFile: ConstantUtf8,
) : Attribute(name, "SourceFile")

class SourceDebugExtensionAttribute(
    name: ConstantUtf8,
    var debugData: ByteArray,
) : Attribute(name, "SourceDebugExtension") {
    override fun toString(): String {
        return "SourceDebugExtensionAttribute(debugData=${debugData.toHexString()})"
    }
}
