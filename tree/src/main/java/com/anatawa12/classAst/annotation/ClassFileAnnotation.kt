package com.anatawa12.classAst.annotation

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.ConstantUtf8

/**
 * The `annotation` structure
 * @spec 4.7.16
 */
@AutoToString("type", "valuePairs")
class ClassFileAnnotation(
    var type: ConstantUtf8,
    val valuePairs: MutableList<AnnotationNameValuePair>,
)

/**
 * The structure of `annotation.element_value_pairs[]`.
 */
data class AnnotationNameValuePair(
    val name: ConstantUtf8,
    val value: ElementValue,
)
