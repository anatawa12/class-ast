package com.anatawa12.classAst.dokka

import org.jetbrains.dokka.model.doc.*
import org.jetbrains.dokka.model.doc.Section
import org.jetbrains.dokka.model.doc.Deprecated
import org.jetbrains.dokka.model.doc.Suppress

fun TagWrapper.transformDocTag(transform: (DocTag) -> DocTag): TagWrapper {
    return when (this::class) {
        See::class -> (this as See).copy(root = root.transformDocTag(transform))
        Param::class -> (this as Param).copy(root = root.transformDocTag(transform))
        Throws::class -> (this as Throws).copy(root = root.transformDocTag(transform))
        Sample::class -> (this as Sample).copy(root = root.transformDocTag(transform))
        Property::class -> (this as Property).copy(root = root.transformDocTag(transform))
        CustomTagWrapper::class -> (this as CustomTagWrapper).copy(root = root.transformDocTag(transform))
        Description::class -> (this as Description).copy(root = root.transformDocTag(transform))
        Author::class -> (this as Author).copy(root = root.transformDocTag(transform))
        Version::class -> (this as Version).copy(root = root.transformDocTag(transform))
        Since::class -> (this as Since).copy(root = root.transformDocTag(transform))
        Return::class -> (this as Return).copy(root = root.transformDocTag(transform))
        Receiver::class -> (this as Receiver).copy(root = root.transformDocTag(transform))
        Constructor::class -> (this as Constructor).copy(root = root.transformDocTag(transform))
        Deprecated::class -> (this as Deprecated).copy(root = root.transformDocTag(transform))
        Suppress::class -> (this as Suppress).copy(root = root.transformDocTag(transform))
        else -> error("unknown TagWrapper: ${this::class.simpleName}")
    }
}

fun DocTag.transformDocTag(transform: (DocTag) -> DocTag): DocTag {
    val childrenProceed = when (this::class) {
        A::class -> (this as A).copy(children = children.map { it.transformDocTag(transform) })
        Big::class -> (this as Big).copy(children = children.map { it.transformDocTag(transform) })
        B::class -> (this as B).copy(children = children.map { it.transformDocTag(transform) })
        BlockQuote::class -> (this as BlockQuote).copy(children = children.map { it.transformDocTag(transform) })
        Cite::class -> (this as Cite).copy(children = children.map { it.transformDocTag(transform) })
        CodeInline::class -> (this as CodeInline).copy(children = children.map { it.transformDocTag(transform) })
        CodeBlock::class -> (this as CodeBlock).copy(children = children.map { it.transformDocTag(transform) })
        CustomDocTag::class -> (this as CustomDocTag).copy(children = children.map { it.transformDocTag(transform) })
        Dd::class -> (this as Dd).copy(children = children.map { it.transformDocTag(transform) })
        Dfn::class -> (this as Dfn).copy(children = children.map { it.transformDocTag(transform) })
        Dir::class -> (this as Dir).copy(children = children.map { it.transformDocTag(transform) })
        Div::class -> (this as Div).copy(children = children.map { it.transformDocTag(transform) })
        Dl::class -> (this as Dl).copy(children = children.map { it.transformDocTag(transform) })
        DocumentationLink::class -> (this as DocumentationLink).copy(children = children.map { it.transformDocTag(transform) })
        Dt::class -> (this as Dt).copy(children = children.map { it.transformDocTag(transform) })
        Em::class -> (this as Em).copy(children = children.map { it.transformDocTag(transform) })
        Font::class -> (this as Font).copy(children = children.map { it.transformDocTag(transform) })
        Footer::class -> (this as Footer).copy(children = children.map { it.transformDocTag(transform) })
        Frame::class -> (this as Frame).copy(children = children.map { it.transformDocTag(transform) })
        FrameSet::class -> (this as FrameSet).copy(children = children.map { it.transformDocTag(transform) })
        H1::class -> (this as H1).copy(children = children.map { it.transformDocTag(transform) })
        H2::class -> (this as H2).copy(children = children.map { it.transformDocTag(transform) })
        H3::class -> (this as H3).copy(children = children.map { it.transformDocTag(transform) })
        H4::class -> (this as H4).copy(children = children.map { it.transformDocTag(transform) })
        H5::class -> (this as H5).copy(children = children.map { it.transformDocTag(transform) })
        H6::class -> (this as H6).copy(children = children.map { it.transformDocTag(transform) })
        Head::class -> (this as Head).copy(children = children.map { it.transformDocTag(transform) })
        Header::class -> (this as Header).copy(children = children.map { it.transformDocTag(transform) })
        Html::class -> (this as Html).copy(children = children.map { it.transformDocTag(transform) })
        I::class -> (this as I).copy(children = children.map { it.transformDocTag(transform) })
        IFrame::class -> (this as IFrame).copy(children = children.map { it.transformDocTag(transform) })
        Img::class -> (this as Img).copy(children = children.map { it.transformDocTag(transform) })
        Index::class -> (this as Index).copy(children = children.map { it.transformDocTag(transform) })
        Input::class -> (this as Input).copy(children = children.map { it.transformDocTag(transform) })
        Li::class -> (this as Li).copy(children = children.map { it.transformDocTag(transform) })
        Link::class -> (this as Link).copy(children = children.map { it.transformDocTag(transform) })
        Listing::class -> (this as Listing).copy(children = children.map { it.transformDocTag(transform) })
        Main::class -> (this as Main).copy(children = children.map { it.transformDocTag(transform) })
        Menu::class -> (this as Menu).copy(children = children.map { it.transformDocTag(transform) })
        Meta::class -> (this as Meta).copy(children = children.map { it.transformDocTag(transform) })
        Nav::class -> (this as Nav).copy(children = children.map { it.transformDocTag(transform) })
        NoFrames::class -> (this as NoFrames).copy(children = children.map { it.transformDocTag(transform) })
        NoScript::class -> (this as NoScript).copy(children = children.map { it.transformDocTag(transform) })
        Ol::class -> (this as Ol).copy(children = children.map { it.transformDocTag(transform) })
        P::class -> (this as P).copy(children = children.map { it.transformDocTag(transform) })
        Pre::class -> (this as Pre).copy(children = children.map { it.transformDocTag(transform) })
        Script::class -> (this as Script).copy(children = children.map { it.transformDocTag(transform) })
        Section::class -> (this as Section).copy(children = children.map { it.transformDocTag(transform) })
        Small::class -> (this as Small).copy(children = children.map { it.transformDocTag(transform) })
        Span::class -> (this as Span).copy(children = children.map { it.transformDocTag(transform) })
        Strikethrough::class -> (this as Strikethrough).copy(children = children.map { it.transformDocTag(transform) })
        Strong::class -> (this as Strong).copy(children = children.map { it.transformDocTag(transform) })
        Sub::class -> (this as Sub).copy(children = children.map { it.transformDocTag(transform) })
        Sup::class -> (this as Sup).copy(children = children.map { it.transformDocTag(transform) })
        Table::class -> (this as Table).copy(children = children.map { it.transformDocTag(transform) })
        Text::class -> (this as Text).copy(children = children.map { it.transformDocTag(transform) })
        TBody::class -> (this as TBody).copy(children = children.map { it.transformDocTag(transform) })
        Td::class -> (this as Td).copy(children = children.map { it.transformDocTag(transform) })
        TFoot::class -> (this as TFoot).copy(children = children.map { it.transformDocTag(transform) })
        Th::class -> (this as Th).copy(children = children.map { it.transformDocTag(transform) })
        THead::class -> (this as THead).copy(children = children.map { it.transformDocTag(transform) })
        Title::class -> (this as Title).copy(children = children.map { it.transformDocTag(transform) })
        Tr::class -> (this as Tr).copy(children = children.map { it.transformDocTag(transform) })
        Tt::class -> (this as Tt).copy(children = children.map { it.transformDocTag(transform) })
        U::class -> (this as U).copy(children = children.map { it.transformDocTag(transform) })
        Ul::class -> (this as Ul).copy(children = children.map { it.transformDocTag(transform) })
        Var::class -> (this as Var).copy(children = children.map { it.transformDocTag(transform) })
        Caption::class -> (this as Caption).copy(children = children.map { it.transformDocTag(transform) })
        Br::class -> this
        HorizontalRule::class -> this
        else -> error("unknown DocTag: ${this::class.simpleName}")
    }
    return transform(childrenProceed)
}