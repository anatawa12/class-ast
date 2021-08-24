package com.anatawa12.classAst.attributes.gen

import com.anatawa12.classAst.attributes.gen.AttributeTarget.*
import java.io.File

fun AttributesGenerator.configure() {
    // definition of custom types

    val ExceptionTable = custom("ExceptionTable")
    val Attribute = custom("Attribute")
    val StackMapFrame = custom("StackMapFrame")
    val InnerClassInformation = custom("InnerClassInformation")
    val LineNumberPair = custom("LineNumberPair")
    val LocalVariableTableEntry = custom("LocalVariableTableEntry")
    val LocalVariableTypeTableEntry = custom("LocalVariableTypeTableEntry")
    val ClassFileAnnotation = custom("ClassFileAnnotation")
    val ClassFileTypeAnnotation = custom("ClassFileTypeAnnotation")
    val ElementValue = custom("ElementValue")
    val BootstrapMethod = custom("BootstrapMethod")
    val ParameterNameAccessPair = custom("ParameterNameAccessPair")
    val AccessFlags = custom("AccessFlags")
    val ModuleRequire = custom("ModuleRequire")
    val ModuleExports = custom("ModuleExports")
    val ModuleOpens = custom("ModuleOpens")
    val ModuleProvides = custom("ModuleProvides")
    val RecordComponent = custom("RecordComponent")

    "ConstantValue" {
        section = "4.7.2"
        minimal = "45.3"
        targets(FieldInfo)

        "value"(cValue)
    }
    "Code" {
        section = "4.7.3"
        minimal = "45.3"
        targets(MethodInfo)

        "maxStack"(u2)
        "macLocals"(u2)
        "code"(mutableCustom("Code"))
        "exceptions"(u2List(ExceptionTable))
        "attributes"(u2List(Attribute))
    }
    "StackMapTable" {
        section = "4.7.4"
        minimal = "50.0"
        targets(Code)

        "entries"(u2List(StackMapFrame))
    }
    "Exceptions" {
        section = "4.7.5"
        minimal = "45.3"
        targets(MethodInfo)

        "exceptions"(u2List(cClass))
    }
    "InnerClasses" {
        section = "4.7.6"
        minimal = "45.3"
        targets(ClassFile)

        "classes"(u2List(InnerClassInformation))
    }
    "EnclosingMethod" {
        section = "4.7.7"
        minimal = "49.0"
        targets(ClassFile)

        "outerClass"(cClass)
        "method"(cNameAndType.nullable())
    }
    "Synthetic" {
        section = "4.7.8"
        minimal = "45.3"
        targets(ClassFile, FieldInfo, MethodInfo)
    }
    "Signature" {
        section = "4.7.9"
        minimal = "49.0"
        targets(ClassFile, FieldInfo, MethodInfo, RecordComponentInfo)

        "signature"(cUtf8)
    }
    "SourceFile" {
        section = "4.7.10"
        minimal = "45.3"
        targets(ClassFile)

        "sourceFile"(cUtf8)
    }
    "SourceDebugExtension" {
        section = "4.7.11"
        minimal = "49.0"
        targets(ClassFile)

        "debugData"(u4ByteArray)
    }
    "LineNumberTable" {
        section = "4.7.12"
        minimal = "45.3"
        targets(Code)

        "table"(u2List(LineNumberPair))
    }
    "LocalVariableTable" {
        section = "4.7.13"
        minimal = "45.3"
        targets(Code)

        "table"(u2List(LocalVariableTableEntry))
    }
    "LocalVariableTypeTable" {
        section = "4.7.14"
        minimal = "49.0"
        targets(Code)

        "table"(u2List(LocalVariableTypeTableEntry))
    }
    "Deprecated" {
        section = "4.7.15"
        minimal = "45.3"
        targets(ClassFile, FieldInfo, MethodInfo)
    }
    "RuntimeVisibleAnnotations" {
        section = "4.7.16"
        minimal = "49.0"
        targets(ClassFile, FieldInfo, MethodInfo, RecordComponentInfo)

        "annotations"(u2List(ClassFileAnnotation))
    }
    "RuntimeInvisibleAnnotations" {
        section = "4.7.17"
        minimal = "49.0"
        targets(ClassFile, FieldInfo, MethodInfo, RecordComponentInfo)

        "annotations"(u2List(ClassFileAnnotation))
    }
    "RuntimeVisibleParameterAnnotations" {
        section = "4.7.18"
        minimal = "49.0"
        targets(MethodInfo)

        "annotations"(u2List(u2List(ClassFileAnnotation)))
    }
    "RuntimeInvisibleParameterAnnotations" {
        section = "4.7.19"
        minimal = "49.0"
        targets(MethodInfo)

        "annotations"(u2List(u2List(ClassFileAnnotation)))
    }
    "RuntimeVisibleTypeAnnotations" {
        section = "4.7.20"
        minimal = "52.0"
        targets(ClassFile, FieldInfo, MethodInfo, Code, RecordComponentInfo)

        "annotations"(u2List(ClassFileTypeAnnotation))
    }
    "RuntimeInvisibleTypeAnnotations" {
        section = "4.7.21"
        minimal = "52.0"
        targets(ClassFile, FieldInfo, MethodInfo, Code, RecordComponentInfo)

        "annotations"(u2List(ClassFileTypeAnnotation))
    }
    "AnnotationDefault" {
        section = "4.7.22"
        minimal = "49.0"
        targets(MethodInfo)

        "defaultValue"(ElementValue)
    }
    "BootstrapMethods" {
        section = "4.7.23"
        minimal = "51.0"
        targets(ClassFile)

        "boostrapMethods"(u2List(BootstrapMethod))
    }
    "MethodParameters" {
        section = "4.7.24"
        minimal = "52.0"
        targets(MethodInfo)

        "parameters"(u2List(ParameterNameAccessPair))
    }
    "Module" {
        section = "4.7.25"
        minimal = "53.0"
        targets(ClassFile)

        "moduleName"(cUtf8)
        "moduleFlags"(AccessFlags)
        "moduleVersion"(cUtf8)
        "requires"(u2List(ModuleRequire))
        "exports"(u2List(ModuleExports))
        "opens"(u2List(ModuleOpens))
        "uses"(u2List(cClass))
        "provides"(u2List(ModuleProvides))
    }
    "ModulePackages" {
        section = "4.7.26"
        minimal = "53.0"
        targets(ClassFile)

        "packages"(u2List(cPackage))
    }
    "ModuleMainClass" {
        section = "4.7.27"
        minimal = "53.0"
        targets(ClassFile)

        "mainClass"(cClass)
    }
    "NestHost" {
        section = "4.7.28"
        minimal = "55.0"
        targets(ClassFile)

        "hostClass"(cClass)
    }
    "NestMembers" {
        section = "4.7.29"
        minimal = "55.0"
        targets(ClassFile)

        "classes"(u2List(cClass))
    }
    "Record" {
        section = "4.7.30"
        minimal = "60.0"
        targets(ClassFile)

        "components"(u2List(RecordComponent))
    }
}

@Marker
class AttributesGenerator : PropTypeBuilder {
    companion object {
        @JvmStatic
        fun doGenerate(sourceRoot: File) {
            AttributesGenerator()
                .apply { configure() }
                .output(sourceRoot.resolve("com/anatawa12/classAst/attributes/GeneratedAttributes.kt"))
        }

        @JvmStatic
        fun main(args: Array<String>) {
            doGenerate(File(args[0]))
        }
    }

    val code = StringBuilder()

    init {
        // append header
        code.apply {
            appendLine("package com.anatawa12.classAst.attributes")
            appendLine("")
            appendLine("// THIS FILE IS GENERATED BY AttributesGenerator.kt.")
            appendLine("// DO NOT EDIT MANUALLY")
            appendLine("")
            appendLine("import com.anatawa12.autoToString.AutoToString")
            appendLine("import com.anatawa12.classAst.*")
            appendLine("import com.anatawa12.classAst.annotation.*")
            appendLine("import com.anatawa12.classAst.attributes.AttributeTarget.Companion.ClassFile")
            appendLine("import com.anatawa12.classAst.attributes.AttributeTarget.Companion.Code")
            appendLine("import com.anatawa12.classAst.attributes.AttributeTarget.Companion.FieldInfo")
            appendLine("import com.anatawa12.classAst.attributes.AttributeTarget.Companion.MethodInfo")
            appendLine("import com.anatawa12.classAst.attributes.AttributeTarget.Companion.RecordComponentInfo")
            appendLine("import com.anatawa12.classAst.code.Code")
            appendLine("import com.anatawa12.classAst.code.StackMapFrame")
            appendLine("")
        }
    }

    inline operator fun String.invoke(block: AttributeBuilder.() -> Unit) {
        AttributeBuilder(this)
            .apply(block)
            .build(code)
        code.appendLine("")
    }

    fun output(to: File) {
        to.writeText(code.toString())
    }
}

@DslMarker
annotation class Marker

@Marker
class AttributeBuilder(val name: String) : PropTypeBuilder {
    var minimal: String? = null
    var section: String? = null
    val props = mutableListOf<Pair<String, PropType>>()
    val targets = mutableListOf<AttributeTarget>()

    operator fun String.invoke(type: PropType) =
        props.add(this to type)

    fun targets(vararg targets: AttributeTarget) =
        this.targets.addAll(targets)

    fun build(builder: StringBuilder) = builder.apply {
        val minimal = minimal ?: error("no minimal")
        val section = section ?: error("no section")

        appendLine("/**")
        appendLine(" * The $name Attribute.")
        appendLine(" * @spec $section")
        appendLine(" */")
        appendLine("@AutoToString(${props.joinToString { (name) -> "\"$name\"" }})")
        appendLine("class ${name}Attribute(")
        appendLine("    name: ConstantUtf8,")
        for ((name, type) in props)
            appendLine("    ${type.declType} $name: ${type.toType()},")
        appendLine(") : Attribute(name, Type) {")
        appendLine("    companion object Type : AttributeType(")
        appendLine("        ${name}Attribute::class, ")
        appendLine("        \"$name\", ")
        appendLine("        CV${minimal.replace('.', '_')}, ")
        for (target in targets)
            appendLine("        ${target},")
        appendLine("    )")
        appendLine("}")
    }
}

interface PropTypeBuilder {
    fun u2List(value: PropType) = PropType.U2List(value)
    fun PropType.nullable() = PropType.Nullable(this)

    // constant types
    val cRef get() = PropType.Constant("Ref")
    val cAnyMethodRef get() = PropType.Constant("AnyMethodRef")
    val cLoadable get() = PropType.Constant("Loadable")
    val cValue get() = PropType.Constant("Value")
    val cUtf8 get() = PropType.Constant("Utf8")
    val cInteger get() = PropType.Constant("Integer")
    val cFloat get() = PropType.Constant("Float")
    val cLong get() = PropType.Constant("Long")
    val cDouble get() = PropType.Constant("Double")
    val cClass get() = PropType.Constant("Class")
    val cString get() = PropType.Constant("String")
    val cFieldRef get() = PropType.Constant("FieldRef")
    val cMethodRef get() = PropType.Constant("MethodRef")
    val cInterfaceMethodRef get() = PropType.Constant("InterfaceMethodRef")
    val cNameAndType get() = PropType.Constant("NameAndType")
    val cMethodHandle get() = PropType.Constant("MethodHandle")
    val cMethodType get() = PropType.Constant("MethodType")
    val cDynamic get() = PropType.Constant("Dynamic")
    val cInvokeDynamic get() = PropType.Constant("InvokeDynamic")
    val cModule get() = PropType.Constant("Module")
    val cPackage get() = PropType.Constant("Package")

    // primitive types
    val u1 get() = PropType.Primitive("UByte")
    val u2 get() = PropType.Primitive("UShort")
    val u4ByteArray get() = PropType.U4ByteArray

    // custom types
    fun mutableCustom(name: String) = PropType.MutableCustom(name)
    fun custom(name: String) = PropType.Custom(name)
}

sealed class PropType(val declType: String) {
    abstract fun toType(): String

    data class U2List(val value: PropType) : PropType("val") {
        override fun toType(): String = "MutableList<${value.toType()}>"
    }

    object U4ByteArray : PropType("var") {
        override fun toType(): String = "ByteArray"
    }

    data class Nullable(val value: PropType) : PropType(value.declType) {
        override fun toType(): String = "${value.toType()}?"
    }

    data class Constant(val name: String) : PropType("var") {
        override fun toType(): String =
            if (name == "Loadable") "LoadableConstant" else "Constant$name"
    }

    data class Primitive(val name: String) : PropType("var") {
        override fun toType(): String = name
    }

    data class MutableCustom(val name: String) : PropType("val") {
        override fun toType(): String = name
    }

    data class Custom(val name: String) : PropType("var") {
        override fun toType(): String = name
    }
}

enum class AttributeTarget {
    ClassFile,
    FieldInfo,
    MethodInfo,
    Code,
    RecordComponentInfo,
}
