package com.anatawa12.classAst.attributes

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.*
import com.anatawa12.classAst.annotation.ClassFileAnnotation
import com.anatawa12.classAst.annotation.ClassFileTypeAnnotation
import com.anatawa12.classAst.annotation.ElementValue
import com.anatawa12.classAst.code.Code
import com.anatawa12.classAst.code.Instruction
import com.anatawa12.classAst.code.StackMapFrame

/**
 * The base class for Attributes.
 * 
 * @spec 4.7
 */
abstract class Attribute(val name: ConstantUtf8) {
    constructor(name: ConstantUtf8, expects: String) : this(name) {
        require(this.name.value == expects) {
            "the name of ${this::class.simpleName} must be '$expects'"
        }
    }
}

@AutoToString()
class DeprecatedAttribute(name: ConstantUtf8): Attribute(name, "Deprecated")

@AutoToString("annotations")
class RuntimeVisibleAnnotationsAttribute(
    name: ConstantUtf8,
    val annotations: MutableList<ClassFileAnnotation>,
) : Attribute(name, "RuntimeVisibleAnnotations")

@AutoToString("annotations")
class RuntimeInvisibleAnnotationsAttribute(
    name: ConstantUtf8,
    val annotations: MutableList<ClassFileAnnotation>,
) : Attribute(name, "RuntimeInvisibleAnnotations")

@AutoToString("annotations")
class RuntimeVisibleParameterAnnotationsAttribute(
    name: ConstantUtf8,
    val annotations: MutableList<MutableList<ClassFileAnnotation>>,
) : Attribute(name, "RuntimeVisibleParameterAnnotations")

@AutoToString("annotations")
class RuntimeInvisibleParameterAnnotationsAttribute(
    name: ConstantUtf8,
    val annotations: MutableList<MutableList<ClassFileAnnotation>>,
) : Attribute(name, "RuntimeInvisibleParameterAnnotations")

@AutoToString("annotations")
class RuntimeVisibleTypeAnnotationsAttribute(
    name: ConstantUtf8,
    val annotations: MutableList<ClassFileTypeAnnotation>,
) : Attribute(name, "RuntimeVisibleTypeAnnotations")

@AutoToString("annotations")
class RuntimeInvisibleTypeAnnotationsAttribute(
    name: ConstantUtf8,
    val annotations: MutableList<ClassFileTypeAnnotation>,
) : Attribute(name, "RuntimeInvisibleTypeAnnotations")

@AutoToString("defaultValue")
class AnnotationDefaultAttribute(
    name: ConstantUtf8,
    var defaultValue: ElementValue,
) : Attribute(name, "AnnotationDefault")

@AutoToString("boostrapMethods")
class BootstrapMethodsAttribute(
    name: ConstantUtf8,
    val boostrapMethods: MutableList<BootstrapMethod>,
) : Attribute(name, "BootstrapMethods")

@AutoToString("bootstrapMethod", "arguments")
class BootstrapMethod(
    var bootstrapMethod: ConstantMethodHandle,
    val arguments: MutableList<LoadableConstant>
)

@AutoToString("parameters")
class MethodParametersAttribute(
    name: ConstantUtf8,
    val parameters: MutableList<ParameterNameAccessPair>,
) : Attribute(name, "MethodParameters")

@AutoToString("accessFlags")
class ParameterNameAccessPair(
    val name: ConstantUtf8?,
    val accessFlags: AccessFlags,
)

class ModuleAttribute(
    name: ConstantUtf8,
    var moduleName: ConstantUtf8,
    var moduleFlags: AccessFlags,
    var moduleVersion: ConstantUtf8,
    val requires: MutableList<ModuleRequire>,
    val exports: MutableList<ModuleExports>,
    val opens: MutableList<ModuleOpens>,
    val uses: MutableList<ConstantClass>,
    val provides: MutableList<ModuleProvides>,
)

@AutoToString("requires", "flags", "version")
class ModuleRequire(
    var requires: ConstantUtf8,
    var flags: AccessFlags,
    var version: ConstantUtf8,
)

@AutoToString("exports", "flags", "exportsTo")
class ModuleExports(
    var exports: ConstantPackage,
    var flags: AccessFlags,
    val exportsTo: MutableList<ConstantModule>,
)

@AutoToString("opens", "flags", "exportsTo")
class ModuleOpens(
    var opens: ConstantPackage,
    var flags: AccessFlags,
    val exportsTo: MutableList<ConstantModule>,
)

@AutoToString("provides", "with")
class ModuleProvides(
    var provides: ConstantClass,
    var with: MutableList<ConstantClass>,
)

@AutoToString("packages")
class ModulePackagesAttribute(
    name: ConstantUtf8,
    val packages: MutableList<ConstantPackage>
) : Attribute(name, "ModulePackages")

@AutoToString("mainClass")
class ModuleMainClassAttribute(
    name: ConstantUtf8,
    var mainClass: ConstantClass,
) : Attribute(name, "ModuleMainClass")

@AutoToString("hostClass")
class NestHostAttribute(
    name: ConstantUtf8,
    var hostClass: ConstantClass,
) : Attribute(name, "NestHost")

@AutoToString("classes")
class NestMembersAttribute(
    name: ConstantUtf8,
    val classes: MutableList<ConstantClass>,
) : Attribute(name, "NestMembers")

@AutoToString("components")
class RecordAttribute(
    name: ConstantUtf8,
    val components: MutableList<RecordComponent>,
) : Attribute(name, "Record")

@AutoToString("name", "descriptor", "attributes")
class RecordComponent(
    var name: ConstantUtf8,
    var descriptor: ConstantUtf8,
    val attributes: MutableList<Attribute>,
)
