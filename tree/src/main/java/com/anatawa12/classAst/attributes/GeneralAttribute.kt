package com.anatawa12.classAst.attributes

import com.anatawa12.classAst.ConstantUtf8

/**
 * The class for user-defined, unknown attribute.
 * 
 * @spec 4.7.1
 */
@Suppress("DEPRECATION")
class GeneralAttribute(name: ConstantUtf8, var value: ByteArray) : Attribute(name, 0) {
}
