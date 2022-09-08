package de.critequal.mobile.composefeedreader.dto

import org.simpleframework.xml.Element

data class Image(
    @Element(name = "url") val url: String,
    @Element(name = "title") val title: String,
    @Element(name = "link") val link: String,
)
