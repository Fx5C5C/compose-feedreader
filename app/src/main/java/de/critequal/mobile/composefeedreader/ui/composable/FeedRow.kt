package de.critequal.mobile.composefeedreader.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import de.critequal.mobile.composefeedreader.dto.Item
import de.critequal.mobile.composefeedreader.ui.theme.BlackTranslucent40

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