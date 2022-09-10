package de.critequal.mobile.composefeedreader

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import de.critequal.mobile.composefeedreader.ui.composable.FeedScreen

class FeedActivity : ComponentActivity() {

    private val viewModel: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.error.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this@FeedActivity, message, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.getFeeds().observe(this) { channels ->
            setContent {
                FeedScreen(ctx = this, channels = channels)
            }
        }
    }
}