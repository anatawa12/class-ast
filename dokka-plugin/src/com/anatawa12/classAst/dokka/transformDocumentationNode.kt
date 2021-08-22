package com.anatawa12.classAst.dokka

import org.jetbrains.dokka.model.*
import org.jetbrains.dokka.model.doc.DocumentationNode

private typealias DocumentationNodeTransform = (DocumentationNode) -> DocumentationNode

fun <T : Documentable> T.transformDocumentationNode(transformer: DocumentationNodeTransform): T {
    val result = when (this::class) {
        DModule::class -> (this as DModule).copy(
            packages = packages.map { it.transformDocumentationNode(transformer) },
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DPackage::class -> (this as DPackage).copy(
            functions = functions.map { it.transformDocumentationNode(transformer) },
            properties = properties.map { it.transformDocumentationNode(transformer) },
            classlikes = classlikes.map { it.transformDocumentationNode(transformer) },
            typealiases = typealiases.map { it.transformDocumentationNode(transformer) },
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DClass::class -> (this as DClass).copy(
            constructors = constructors.map { it.transformDocumentationNode(transformer) },
            functions = functions.map { it.transformDocumentationNode(transformer) },
            properties = properties.map { it.transformDocumentationNode(transformer) },
            classlikes = classlikes.map { it.transformDocumentationNode(transformer) },
            companion = companion?.transformDocumentationNode(transformer),
            generics = generics.map { it.transformDocumentationNode(transformer) },
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DEnum::class -> (this as DEnum).copy(
            entries = entries.map { it.transformDocumentationNode(transformer) },
            functions = functions.map { it.transformDocumentationNode(transformer) },
            properties = properties.map { it.transformDocumentationNode(transformer) },
            classlikes = classlikes.map { it.transformDocumentationNode(transformer) },
            companion = companion?.transformDocumentationNode(transformer),
            constructors = constructors.map { it.transformDocumentationNode(transformer) },
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DEnumEntry::class -> (this as DEnumEntry).copy(
            functions = functions.map { it.transformDocumentationNode(transformer) },
            properties = properties.map { it.transformDocumentationNode(transformer) },
            classlikes = classlikes.map { it.transformDocumentationNode(transformer) },
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DFunction::class -> (this as DFunction).copy(
            parameters = parameters.map { it.transformDocumentationNode(transformer) },
            generics = generics.map { it.transformDocumentationNode(transformer) },
            receiver = receiver?.transformDocumentationNode(transformer),
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DInterface::class -> (this as DInterface).copy(
            functions = functions.map { it.transformDocumentationNode(transformer) },
            properties = properties.map { it.transformDocumentationNode(transformer) },
            classlikes = classlikes.map { it.transformDocumentationNode(transformer) },
            companion = companion?.transformDocumentationNode(transformer),
            generics = generics.map { it.transformDocumentationNode(transformer) },
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DObject::class -> (this as DObject).copy(
            functions = functions.map { it.transformDocumentationNode(transformer) },
            properties = properties.map { it.transformDocumentationNode(transformer) },
            classlikes = classlikes.map { it.transformDocumentationNode(transformer) },
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DAnnotation::class -> (this as DAnnotation).copy(
            functions = functions.map { it.transformDocumentationNode(transformer) },
            properties = properties.map { it.transformDocumentationNode(transformer) },
            classlikes = classlikes.map { it.transformDocumentationNode(transformer) },
            companion = companion?.transformDocumentationNode(transformer),
            constructors = constructors.map { it.transformDocumentationNode(transformer) },
            generics = generics.map { it.transformDocumentationNode(transformer) },
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DProperty::class -> (this as DProperty).copy(
            receiver = receiver?.transformDocumentationNode(transformer),
            getter = getter?.transformDocumentationNode(transformer),
            setter = setter?.transformDocumentationNode(transformer),
            generics = generics.map { it.transformDocumentationNode(transformer) },
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DParameter::class -> (this as DParameter).copy(
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DTypeParameter::class -> (this as DTypeParameter).copy(
            documentation = documentation.transformDocumentationNode(transformer),
        )

        DTypeAlias::class -> (this as DTypeAlias).copy(
            generics = generics.map { it.transformDocumentationNode(transformer) },
            documentation = documentation.transformDocumentationNode(transformer),
        )

        else -> error("unknown (unsupported) documentable type: ${this::class.simpleName ?: this::class.qualifiedName}")
    }

    return result as T
}

private fun SourceSetDependent<DocumentationNode>.transformDocumentationNode(transformer: DocumentationNodeTransform) =
    mapValues { (_, v) -> transformer(v) }
