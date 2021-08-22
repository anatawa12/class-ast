package com.anatawa12.classAst.dokka.uri.spec

import java.net.URLStreamHandler
import java.net.URLStreamHandlerFactory
import java.util.*

class PropertyBasedURLStreamHandlerFactory : URLStreamHandlerFactory {
    override fun createURLStreamHandler(protocol: String?): URLStreamHandler? {
        var packagePrefixList = System.getProperty("java.protocol.handler.pkgs", "")

        return StringTokenizer(packagePrefixList, "|")
            .asSequence()
            .map { it.toString().trim { c -> c <= ' ' } }
            .map { "$it.$protocol.Handler" }
            .mapNotNull { clsName ->
                try {
                    val cls = try {
                        Class.forName(clsName)
                    } catch (e: ClassNotFoundException) {
                        ClassLoader.getSystemClassLoader()?.loadClass(clsName)
                    }
                    cls?.newInstance()?.let { it as URLStreamHandler }
                } catch (e: Exception) {
                    // any number of exceptions can get thrown here
                    null
                }
            }
            .firstOrNull()
    }
}
