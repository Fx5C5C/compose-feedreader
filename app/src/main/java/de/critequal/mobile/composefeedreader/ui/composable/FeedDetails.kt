package de.critequal.mobile.composefeedreader.ui.composable

import android.content.Intent
import android.net.Uri
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import coil.compose.rememberAsyncImagePainter
import de.critequal.mobile.composefeedreader.dto.Item

@OptIn(ExperimentalUnitApi::class)
@Composable
fun FeedDetails(item: Item, onDismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        shape = MaterialTheme.shapes.small,
        tonalElevation = 8.dp,
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Column {
                Text(
                    text = item.title,
                    fontSize = TextUnit(14F, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                )
                Divider(thickness = 1.dp, modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp))
            }
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                item.enclosure?.let {
                    Image(
                        painter = rememberAsyncImagePainter(item.enclosure!!.url),
                        contentDescription = item.enclosure!!.type,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(128.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                AndroidView(
                    modifier = Modifier.padding(top = 8.dp),
                    factory = { context -> TextView(context) },
                    update = { textView ->
                        textView.text = HtmlCompat.fromHtml(item.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
                        textView.movementMethod = LinkMovementMethod.getInstance()
                        textView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
                    }
                )
            }
        },
        dismissButton = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onDismiss() },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Close")
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                        startActivity(context, browserIntent, null)
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Visit")
                }
            }
        },
    )
}