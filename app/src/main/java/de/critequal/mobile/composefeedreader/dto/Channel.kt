package de.critequal.mobile.composefeedreader.dto

import org.simpleframework.xml.*

data class Channel @JvmOverloads constructor(
    @field:Element(name = "title", required = false) var title: String = "",
    @field:Element(name = "link", required = false) var link: String = "",
    @field:Element(name = "description", required = false) var description: String = "",
    @field:Element(name = "language", required = false) var language: String = "",
    @field:Element(name = "copyright", required = false) var copyright: String = "",
    @field:Element(name = "pubDate", required = false) var pubDate: String = "",
    @field:Element(name = "lastBuildDate", required = false) var lastBuildDate: String = "",
    @field:Element(name = "managingEditor", required = false) var managingEditor: String = "",
    @field:Element(name = "webMaster", required = false) var webMaster: String = "",
    @field:Element(name = "ttl", required = false) var ttl: String = "",
    @field:Element(name = "image", required = false) var image: Image? = null,
    @field:ElementList(name = "item", required = false, inline = true) var items: ArrayList<Item> = arrayListOf(),
)
