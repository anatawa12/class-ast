package com.anatawa12.classAst.attributes

/**
 * Instance of this class indicates the location of Attribute.
 */
class AttributeTarget(private val viewName: String) {
    override fun toString(): String = viewName

    companion object {
        /**
         * Indicates the [ClassFile structure](spec:4.1)
         */
        @JvmField
        val ClassFile = AttributeTarget("ClassFile")
        @JvmField
        val FieldInfo = AttributeTarget("FieldInfo")
        @JvmField
        val MethodInfo = AttributeTarget("MethodInfo")
        @JvmField
        val Code = AttributeTarget("Code")
        @JvmField
        val RecordComponentInfo = AttributeTarget("RecordComponentInfo")
    }
}
