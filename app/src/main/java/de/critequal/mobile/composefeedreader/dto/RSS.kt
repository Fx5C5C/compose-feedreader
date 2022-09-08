package de.critequal.mobile.composefeedreader.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RSS @JvmOverloads constructor(
    @field:Element(name = "channel", required = false) var channel: Channel? = null
)
