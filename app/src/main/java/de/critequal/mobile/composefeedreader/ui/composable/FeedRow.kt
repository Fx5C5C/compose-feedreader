package de.critequal.mobile.composefeedreader.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.*
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import de.critequal.mobile.composefeedreader.R
import de.critequal.mobile.composefeedreader.dto.Enclosure
import de.critequal.mobile.composefeedreader.dto.Item
import de.critequal.mobile.composefeedreader.dto.getTimePassed
import de.critequal.mobile.composefeedreader.ui.theme.BlackTranslucent40


@OptIn(ExperimentalUnitApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FeedRow(item: Item) {
    val composition by rememberLottieComposition(RawRes(R.raw.maximize))
    var showDetailsDialog by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(3.dp),
        modifier = Modifier
            .padding(12.dp, 6.dp, 12.dp, 4.dp),
        colors = CardDefaults.cardColors(
            Color.White
        ),
        onClick = { showDetailsDialog = true }
    ) {
        if (showDetailsDialog) {
            FeedDetails(item = item) { showDetailsDialog = false }
        }
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
                            .height(72.dp)
                    )
                }
                Text(
                    text = item.title,
                    style = TextStyle(
                        fontWeight = FontWeight(FontWeight.ExtraBold.weight),
                        fontSize = TextUnit(12F, TextUnitType.Sp)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                        .background(BlackTranslucent40)
                        .padding(8.dp),
                    color = Color.White,
                )
            }
            Row {
                Text(
                    text = item.source,
                    modifier = Modifier
                        .align(Alignment.Top)
                        .weight(1F)
                        .padding(8.dp, 8.dp, 0.dp, 8.dp)
                        .fillMaxWidth(),
                    fontSize = TextUnit(10F, TextUnitType.Sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .padding(0.dp, 8.dp, 0.dp, 8.dp)
                        .size(12.dp)
                        .scale(6F)
                )
                Box(modifier = Modifier
                    .weight(1F)) {
                    Text(
                        text = "~ ${item.getTimePassed().format()}",
                        modifier = Modifier
                            .padding(0.dp, 8.dp, 12.dp, 8.dp)
                            .align(Alignment.CenterEnd),
                        fontSize = TextUnit(10F, TextUnitType.Sp),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedRowPreview() {
    val item = Item(
        "FeedSource.com",
        "An example feed title of an interesting topic",
        "https://www.google.com",
        """
            This is the description with more detailed information about all the
            interesting things from a feed you want to read more about. Click the 
            feed item and you can read me.
        """,
        "Fri, 30 Sep 2022 19:52:00 GMT",
        Enclosure(
            "0",
            "image/jpg",
            "https://images.cgames.de/images/gamestar/112/gs-stadia-wird-beendet_6197841.jpg"
        )
    )
    FeedRow(item = item)
}