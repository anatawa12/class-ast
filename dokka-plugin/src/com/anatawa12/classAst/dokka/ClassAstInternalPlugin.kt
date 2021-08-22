package com.anatawa12.classAst.dokka

import com.anatawa12.classAst.dokka.uri.spec.PropertyBasedURLStreamHandlerFactory
import org.jetbrains.dokka.CoreExtensions
import org.jetbrains.dokka.model.*
import org.jetbrains.dokka.model.doc.*
import org.jetbrains.dokka.plugability.DokkaContext
import org.jetbrains.dokka.plugability.DokkaPlugin
import org.jetbrains.dokka.transformers.documentation.DocumentableTransformer
import org.jetbrains.dokka.utilities.cast
import java.net.URL
import java.util.*

class ClassAstInternalPlugin : DokkaPlugin() {
    init {
        addHandlerPackage("com.anatawa12.classAst.dokka.uri")
    }

    private fun addHandlerPackage(pkg: String) {
        kotlin.runCatching {
            URL.setURLStreamHandlerFactory(PropertyBasedURLStreamHandlerFactory())
        }
        val prop = "java.protocol.handler.pkgs"
        val value = System.getProperty(prop)
        if (value.isNullOrEmpty()) {
            System.setProperty(prop, pkg)
        } else if (StringTokenizer(value, "|").asSequence().contains(pkg)) {
            // if exists, nop
        } else {
            System.setProperty(prop, "$value|$pkg")
        }
    }


    val specTagTransformer by extending {
        CoreExtensions.documentableTransformer with SpecTagDocumentableTransformer
    }
    //*
    val specSchemaTransformer by extending {
        CoreExtensions.documentableTransformer with SpecSchemaDocumentableTransformer
    }
    // */
}

class Section(val numbers: IntArray) {
    override fun toString(): String = numbers.joinToString(".")

    fun jvmSpec(): String {
        return "https://docs.oracle.com/javase/specs/jvms/se16/html/jvms-${numbers[0]}.html#jvms-$this"
    }
}

fun String.parseSectionOrNull(): Section? = split('.')
    .map { it.toIntOrNull() }
    .nullIfAnyNull()
    ?.toIntArray()
    ?.let(::Section)

object SpecTagDocumentableTransformer : DocumentableTransformer {
    override fun invoke(original: DModule, context: DokkaContext): DModule = original.transformDocumentationNode { node ->
        val description = node.children.filterIsInstance<Description>().firstOrNull()
        val docTag = description?.root?.cast<CustomDocTag>()
            ?: CustomDocTag(name = "MARKDOWN_FILE")

        val seeTags = node.children
            .asSequence()
            //.onEach { println("test: $it") }
            .filterIsInstance<CustomTagWrapper>()
            .filter { it.name == "spec" }
            .mapNotNull { tag ->
                tag.root.children
                    .singleOrNull()
                    .safeCast<P>()
                    ?.children
                    ?.singleOrNull()
                    .safeCast<Text>()
                    ?.body
            }
            .map { body ->
                body.split(',')
                  .map { it.trim() }
                  .filter { it.isNotEmpty() }
                  .mapNotNull(String::parseSectionOrNull)
            }
            .map { sections ->
                val aTags = sections.map { section ->
                    A(listOf(Text("Section $section")), mapOf("href" to section.jvmSpec()))
                }

                when (aTags.size) {
                    1 -> {
                        P(listOf(
                            Text("See Java Virtual Machine Specification "),
                            aTags[0],
                        ))
                    }
                    2 -> {
                        P(listOf(
                            Text("See Java Virtual Machine Specification "),
                            aTags[0],
                            Text(" and "),
                            aTags[1],
                        ))
                    }
                    else -> {
                        val heading = aTags.dropLast(2)
                        val lastTwo = aTags.takeLast(2)
                        val tags = mutableListOf<DocTag>(
                            Text("See Java Virtual Machine Specification ")
                        )
                        for (headingTag in heading) {
                            tags.add(headingTag)
                            tags.add(Text(", "))
                        }
                        tags.add(lastTwo[0])
                        tags.add(Text(", and "))
                        tags.add(lastTwo[1])
                        P(tags)
                    }
                }
            }
            .toList()

        if (seeTags.isEmpty()) {
            node
        } else if (description == null) {
            node.copy(
                children = listOf(Description(docTag.copy(children = docTag.children + seeTags))) + node.children
            )
        } else {
            node.copy(
                children = node.children.map { tag ->
                    if (tag === description)
                        description.copy(docTag.copy(children = docTag.children + seeTags))
                    else
                        tag
                }
            )
        }
    }
}

object SpecSchemaDocumentableTransformer : DocumentableTransformer {
    override fun invoke(original: DModule, context: DokkaContext): DModule = original.transformDocumentationNode node@{ node ->
        node.copy(
            children = node.children.map {
                it.transformDocTag tag@ { tag ->
                    if (tag !is A) return@tag tag
                    val href = tag.params["href"] ?: return@tag tag
                    if (!href.startsWith("spec:")) return@tag tag
                    val section = href.removePrefix("spec:").trim().parseSectionOrNull() ?: return@tag tag
                    tag.copy(
                        params = tag.params + mapOf("href" to section.jvmSpec())
                    )
                }
            }
        )
    }
}

@Suppress("UNCHECKED_CAST")
private fun <E : Any> List<E?>.nullIfAnyNull(): List<E>? =
    if (this.any { it == null }) null else this as List<E>

private inline fun <reified T> Any?.safeCast(): T? = this as? T
