package de.critequal.mobile.composefeedreader

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import de.critequal.mobile.composefeedreader.persistence.FeedDatabase
import de.critequal.mobile.composefeedreader.ui.composable.FeedNavHost
import org.koin.android.ext.android.get

class FeedActivity : ComponentActivity() {

    private val database: FeedDatabase = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel =  FeedViewModel(database)
        viewModel.error.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this@FeedActivity, message, Toast.LENGTH_LONG).show()
            }
        }

        setContent {
            FeedNavHost()
        }
    }
}