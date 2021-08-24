@file:JvmName("Main")

package com.anatawa12.classAst.gen

import com.anatawa12.classAst.attributes.gen.AttributesGenerator
import java.io.File

fun main(args: Array<String>) {
    val sourceRoot = File(args[0])

    AttributesGenerator.doGenerate(sourceRoot)
}
