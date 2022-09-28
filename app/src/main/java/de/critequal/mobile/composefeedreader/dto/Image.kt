package de.critequal.mobile.composefeedreader.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "enclosure", strict = false)
data class Image @JvmOverloads constructor(
    @field:Element(name = "url", required = false) var url: String? = "",
    @field:Element(name = "title", required = false) var title: String? = "",
    @field:Element(name = "link", required = false) var link: String? = "",
)
