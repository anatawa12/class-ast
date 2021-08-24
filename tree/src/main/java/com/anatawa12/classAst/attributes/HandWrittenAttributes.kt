package com.anatawa12.classAst.attributes

import com.anatawa12.classAst.CV49_0
import com.anatawa12.classAst.ConstantUtf8
import com.anatawa12.classAst.toHexString

/**
 * The SourceDebugExtension Attribute.
 * @spec 4.7.11
 */
class SourceDebugExtensionAttribute(
    name: ConstantUtf8,
    var debugData: ByteArray,
) : Attribute(name, Type) {

    override fun toString(): String = "SourceDebugExtensionAttribute(debugData=${debugData.toHexString()})"

    companion object Type : AttributeType(
        SourceDebugExtensionAttribute::class,
        "SourceDebugExtension",
        CV49_0,
        AttributeTarget.ClassFile,
    )
}
