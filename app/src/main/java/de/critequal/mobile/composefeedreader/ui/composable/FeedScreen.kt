package de.critequal.mobile.composefeedreader.ui.composable

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.critequal.mobile.composefeedreader.FeedViewModel
import de.critequal.mobile.composefeedreader.dto.Item
import de.critequal.mobile.composefeedreader.dto.getTimePassed
import de.critequal.mobile.composefeedreader.ui.theme.ComposefeedreaderTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    navController: NavController = rememberNavController(),
    viewModel: FeedViewModel = get()
) {
    rememberCoroutineScope().launch {
        viewModel.fetchFeeds()
    }
    Scaffold(
        topBar = { AppBar() },
        bottomBar = { BottomNavigationBar(navController) },
        content = {
            ComposefeedreaderTheme {
                Row {
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(0.dp, 52.dp, 0.dp, 0.dp),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        AnimatedVisibility(
                            visible = viewModel.feeds.isEmpty(),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500))
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(128.dp)

                            )
                        }
                        AnimatedVisibility(
                            visible = viewModel.feeds.isEmpty(),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500))
                        ) {
                            Text(
                                text = "No feeds available",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.wrapContentHeight()
                            )
                        }
                        val items = ArrayList<Item>()
                        for (rss in viewModel.feeds) {
                            rss.channel?.let { channel ->
                                val channelItems = channel.items
                                channelItems.forEach { it.source = channel.title }
                                items.addAll(channelItems)
                            }
                            items.sortBy { item ->
                                item.getTimePassed().seconds
                            }
                        }
                        if (items.size > 0) {
                            FeedList(items = items.distinct().filter { item -> item.getTimePassed().days < 31 })
                        }
                    }
                }
            }
        }
    )
}

