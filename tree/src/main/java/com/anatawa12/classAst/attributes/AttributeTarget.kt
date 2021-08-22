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
        /**
         * Indicates the [field_info structure](spec:4.5)
         */
        @JvmField
        val FieldInfo = AttributeTarget("FieldInfo")
        /**
         * Indicates the [method_info structure](spec:4.6)
         */
        @JvmField
        val MethodInfo = AttributeTarget("MethodInfo")
        /**
         * Indicates the [Code_attribute structure](spec:4.7.3)
         */
        @JvmField
        val Code = AttributeTarget("Code")
        /**
         * Indicates the [record_component_info structure](spec:4.7.30)
         */
        @JvmField
        val RecordComponentInfo = AttributeTarget("RecordComponentInfo")
    }
}
