package com.anatawa12.classAst

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.attributes.Attribute

@AutoToString("accessFlags", "name", "descriptor", "attributes")
class MethodData(
    var accessFlags: AccessFlags,
    var name: ConstantUtf8,
    var descriptor: ConstantUtf8,
    val attributes: MutableList<Attribute>,
) {
    
}
