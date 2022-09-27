package de.critequal.mobile.composefeedreader.model

data class FeedItem(
    val title: String,
    val summary: String,
    val link: String,
    val timeDiff: SimpleTime
)

data class SimpleTime(
    val seconds: Long,
    val minutes: Long,
    val hours: Long,
    val days: Long
) {
    fun format(): String {
        return if (days > 0) {
            "$days d"
        } else if (hours > 0) {
            "$hours h"
        } else if (minutes > 0) {
            "$minutes m"
        } else {
            "just now"
        }
    }
}
