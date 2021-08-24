package com.anatawa12.classAst.annotation

import com.anatawa12.autoToString.AutoToString

/**
 * The `type_annotation` structure
 * @spec 4.7.20
 */
@AutoToString("target", "typePath", "annotation")
class ClassFileTypeAnnotation (
    var target: TargetInfo,
    val typePath: TypePath,
    val annotation: ClassFileAnnotation,
)
