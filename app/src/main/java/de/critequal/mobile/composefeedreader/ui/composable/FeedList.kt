package de.critequal.mobile.composefeedreader.ui.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.critequal.mobile.composefeedreader.dto.Item

@Composable
fun FeedList(items: List<Item>) {
    Column(Modifier.verticalScroll(ScrollState(0))) {
        items.forEach { item ->
            FeedRow(item)
        }
    }
}