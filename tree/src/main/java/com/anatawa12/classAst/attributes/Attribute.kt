package com.anatawa12.classAst.attributes

import com.anatawa12.classAst.*

/**
 * The base class for Attributes.
 * 
 * @spec 4.7
 */
abstract class Attribute {
    val name: ConstantUtf8
    val type: AttributeType?

    constructor(name: ConstantUtf8, type: AttributeType) {
        this.name = name
        this.type = type
        require(this.name.value == type.name) {
            "the name of ${this::class.simpleName} must be '${type.name}'"
        }
    }

    /**
     * The constructor only for GeneralAttribute, you can't use this
     */
    @Suppress("UNUSED_PARAMETER")
    @Deprecated("only for GeneralAttribute")
    internal constructor(name: ConstantUtf8, placeHolder: Int) {
        require(this::class == GeneralAttribute::class)
        this.name = name
        this.type = null
    }
}
