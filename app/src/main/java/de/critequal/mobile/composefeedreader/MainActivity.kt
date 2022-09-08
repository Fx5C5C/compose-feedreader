package de.critequal.mobile.composefeedreader

import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import de.critequal.mobile.composefeedreader.dto.Item
import de.critequal.mobile.composefeedreader.dto.RSS
import de.critequal.mobile.composefeedreader.facade.ApiFacade
import de.critequal.mobile.composefeedreader.ui.theme.BlackTranslucent40
import de.critequal.mobile.composefeedreader.ui.theme.ComposefeedreaderTheme
import org.apache.commons.lang3.time.DateUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        ApiFacade.create().getChannel().enqueue(object: Callback<RSS> {
            override fun onResponse(call: Call<RSS>, response: Response<RSS>) {
                response.body()?.let { channel ->
                    setContent {
                        ComposefeedreaderTheme {
                            // A surface container using the 'background' color from the theme
                            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                                FeedList(items = channel.channel!!.items)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RSS>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                throw (t)
            }

        })
    }
}

@Composable
fun FeedList(items: List<Item>) {
    Column(Modifier.verticalScroll(ScrollState(0))) {
        items.forEach { item ->
            FeedRow(item)
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun FeedRow (item: Item) {

    Card(
        elevation = CardDefaults.cardElevation(48.dp),
        modifier = Modifier.padding(12.dp, 6.dp, 12.dp, 24.dp),
        colors = CardDefaults.cardColors(
                    Color.White
                )
        ) {
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            Box {
                item.enclosure?.let {
                    Image(
                        painter = rememberAsyncImagePainter(item.enclosure!!.url),
                        contentDescription = item.enclosure!!.type,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(128.dp)
                    )
                }
                Text(
                    text = item.title,
                    style = TextStyle(
                        fontWeight = FontWeight(FontWeight.ExtraBold.weight)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .background(BlackTranslucent40)
                        .padding(8.dp),
                    color = Color.White,
                )
            }
            Text(
                text = item.description,
                modifier = Modifier
                    .padding(8.dp, 8.dp, 8.dp, 8.dp),
                textAlign = TextAlign.Justify,
                fontSize = TextUnit(18F,
                    TextUnitType.Sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Divider(
                color = BlackTranslucent40,
                modifier = Modifier.padding(
                    24.dp, 0.dp, 24.dp, 12.dp
                ),
                thickness = 1.dp
            )
            Text(
                text = item.pubDate,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(0.dp, 0.dp, 8.dp, 12.dp),
                fontStyle = FontStyle.Italic
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposefeedreaderTheme {
        FeedList(
            listOf(
                Item(
                    "TestFeed",
                    "Das ist ein TestFeed",
                    "https://www.google.de",
                    "David Dahncke",
                    null)))
    }
}