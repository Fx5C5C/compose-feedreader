package de.critequal.mobile.composefeedreader.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Item @JvmOverloads constructor(
    @field:Element(name = "title", required = false) var title: String = "",
    @field:Element(name = "link", required = false) var link: String = "",
    @field:Element(name = "description", required = false) var description: String = "",
    @field:Element(name = "pubDate", required = false) var pubDate: String = "",
    @field:Element(name = "enclosure", required = false) var enclosure: Enclosure? = null
)
