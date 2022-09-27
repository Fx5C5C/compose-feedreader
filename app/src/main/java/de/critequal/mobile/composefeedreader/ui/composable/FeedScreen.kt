package de.critequal.mobile.composefeedreader.ui.composable

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import de.critequal.mobile.composefeedreader.dto.toFeedItem
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
        floatingActionButton = {
            ComposefeedreaderTheme {
                FloatingActionButton(onClick = { navController.navigate("config")  }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "AddFeed"
                    )

                }
            }
        },
        content = {
            ComposefeedreaderTheme {
                Row {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
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
                            rss.channel?.let {
                                items.addAll(it.items)
                            }
                            items.sortBy { item ->
                                item.toFeedItem().timeDiff.seconds
                            }
                        }
                        if (items.size > 0) {
                            FeedList(items = items)
                        }
                    }
                }
            }
        }
    )
}

