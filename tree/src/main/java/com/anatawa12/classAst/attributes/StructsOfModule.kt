package com.anatawa12.classAst.attributes

import com.anatawa12.autoToString.AutoToString
import com.anatawa12.classAst.*


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
