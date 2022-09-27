package de.critequal.mobile.composefeedreader.ui.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import de.critequal.mobile.composefeedreader.dto.Item

@Composable
fun FeedList(items: List<Item>) {
    LazyColumn {
        itemsIndexed(items) { _, item ->
            FeedRow(item = item)
        }
    }
}