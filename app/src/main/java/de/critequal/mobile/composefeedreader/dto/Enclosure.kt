package de.critequal.mobile.composefeedreader.dto

import org.simpleframework.xml.Attribute

data class Enclosure @JvmOverloads constructor(
    @field:Attribute(name = "length") var length: String = "",
    @field:Attribute(name = "type") var type: String = "",
    @field:Attribute(name = "url") var url: String = ""
)