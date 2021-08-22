package com.anatawa12.classAst.dokka.uri.spec

import com.anatawa12.classAst.dokka.uri.SpecURLConnection
import java.net.URL
import java.net.URLConnection
import java.net.URLStreamHandler

class Handler : URLStreamHandler() {
    override fun openConnection(u: URL): URLConnection {
        return SpecURLConnection(u)
    }
}
