package com.anatawa12.classAst

enum class JavaVersions(
    val classFileVersion: ClassFileVersion,
) {
    V1_1(ClassFileVersion(45u, 3u)),
    V1_2(ClassFileVersion(46u)),
    V1_3(ClassFileVersion(47u)),
    V1_4(ClassFileVersion(48u)),
    V5_0(ClassFileVersion(49u)),
    V6(ClassFileVersion(50u)),
    V7(ClassFileVersion(51u)),
    V8(ClassFileVersion(52u)),
    V9(ClassFileVersion(53u)),
    V10(ClassFileVersion(54u)),
    V11(ClassFileVersion(55u)),
    V12(ClassFileVersion(56u)),
    V13(ClassFileVersion(57u)),
    V14(ClassFileVersion(58u)),
    V15(ClassFileVersion(59u)),
    V16(ClassFileVersion(60u)),
}
