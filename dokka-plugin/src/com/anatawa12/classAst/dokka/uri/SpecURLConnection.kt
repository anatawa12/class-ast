package com.anatawa12.classAst.dokka.uri

import java.net.URL
import java.net.URLConnection

class SpecURLConnection(url: URL) : URLConnection(url) {
    override fun connect() {
        // nop
    }
}
