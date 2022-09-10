package de.critequal.mobile.composefeedreader.ui.composable

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.critequal.mobile.composefeedreader.dto.RSS
import de.critequal.mobile.composefeedreader.ui.theme.ComposefeedreaderTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(ctx: Context, channels: List<RSS>) {
    Scaffold(
        floatingActionButton = {
            ComposefeedreaderTheme {
                FloatingActionButton(onClick = { Toast.makeText(ctx, "Click", Toast.LENGTH_LONG).show() }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "AddFeed"
                    )

                }
            }

        },
        content = {
            ComposefeedreaderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    for (channel in channels) {
                        FeedList(items = channel.channel!!.items)
                    }
                }
            }
        }
    )
}

