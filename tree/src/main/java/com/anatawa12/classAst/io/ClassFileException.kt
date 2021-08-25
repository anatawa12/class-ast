package com.anatawa12.classAst.io

import java.io.IOException

/**
 * The exception happens reading/writing class file. 
 */
class ClassFileException : IOException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
