package de.critequal.mobile.composefeedreader.dto

import de.critequal.mobile.composefeedreader.model.SimpleTime
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@Root(name = "item", strict = false)
data class Item @JvmOverloads constructor(
    var source: String = "",
    @field:Element(name = "title", required = false) var title: String = "",
    @field:Element(name = "link", required = false) var link: String = "",
    @field:Element(name = "description", required = false) var description: String = "",
    @field:Element(name = "pubDate", required = false) var pubDate: String = "",
    @field:Element(name = "enclosure", required = false) var enclosure: Enclosure? = null
)

fun Item.getTimePassed(): SimpleTime {
    var pubDate: LocalDateTime
    try {
        pubDate = LocalDateTime.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(this.pubDate))
    } catch (ex: Exception) {
        pubDate = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(this.pubDate)
            ?.toInstant()
            ?.atZone(ZoneId.systemDefault())
            ?.toLocalDateTime()!!
    }
    val diff: Long = System.currentTimeMillis() - Timestamp.from(pubDate.toInstant(ZoneOffset.UTC)).time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return SimpleTime(
        seconds,
        minutes,
        hours,
        days
    )
}
