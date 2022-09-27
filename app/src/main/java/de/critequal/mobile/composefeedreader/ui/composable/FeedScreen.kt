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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.critequal.mobile.composefeedreader.FeedViewModel
import de.critequal.mobile.composefeedreader.dto.Item
import de.critequal.mobile.composefeedreader.dto.RSS
import de.critequal.mobile.composefeedreader.dto.toFeedItem
import de.critequal.mobile.composefeedreader.ui.theme.ComposefeedreaderTheme
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    navController: NavController = rememberNavController(),
    viewModel: FeedViewModel = get()
) {
    val channels:List<RSS>? by viewModel.getFeeds().observeAsState(null)
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
                            visible = channels == null,
                            exit = fadeOut(animationSpec = tween(durationMillis = 1500))
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(128.dp)

                            )
                        }
                        AnimatedVisibility(
                            visible = channels != null && channels!!.isEmpty(),
                            exit = fadeOut(animationSpec = tween(durationMillis = 1500))
                        ) {
                            Text(
                                text = "No feeds available",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.wrapContentHeight()
                            )
                        }
                        channels?.let { channels ->
                            val items = ArrayList<Item>()
                            for (channel in channels) {
                                channel.channel?.let { chn ->
                                    items.addAll(chn.items)
                                }
                            }
                            items.sortBy{ item ->
                                item.toFeedItem().timeDiff.seconds
                            }
                            FeedList(items = items)
                        }
                    }
                }
            }
        }
    )
}

