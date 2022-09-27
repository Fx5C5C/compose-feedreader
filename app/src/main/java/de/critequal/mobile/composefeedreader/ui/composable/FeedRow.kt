package de.critequal.mobile.composefeedreader.ui.composable

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter
import de.critequal.mobile.composefeedreader.dto.Item
import de.critequal.mobile.composefeedreader.dto.toFeedItem
import de.critequal.mobile.composefeedreader.ui.theme.BlackTranslucent40


@OptIn(ExperimentalUnitApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FeedRow (item: Item) {
    val context = LocalContext.current
    Card(
        elevation = CardDefaults.cardElevation(48.dp),
        modifier = Modifier.padding(12.dp, 6.dp, 12.dp, 24.dp),
        colors = CardDefaults.cardColors(
            Color.White
        ),
        onClick = {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
            startActivity(context, browserIntent, null)
        }
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
                fontSize = TextUnit(12F,
                    TextUnitType.Sp
                ),
                letterSpacing = TextUnit(-.5F, TextUnitType.Sp),
                lineHeight = TextUnit(14F, TextUnitType.Sp),
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
            Row(
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = item.toFeedItem().timeDiff.format(),
                    modifier = Modifier
                        .padding(0.dp, 2.dp, 4.dp, 12.dp),
                    fontSize = TextUnit(12F, TextUnitType.Sp),
                    fontStyle = FontStyle.Italic
                )
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "Follow"
                )
            }
        }
    }
}