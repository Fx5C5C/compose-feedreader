package de.critequal.mobile.composefeedreader.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.critequal.mobile.composefeedreader.R
import de.critequal.mobile.composefeedreader.ui.theme.DarkColorScheme
import de.critequal.mobile.composefeedreader.ui.theme.LightColorScheme

@Composable
fun AppBar() {
    val colors = if(isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
    androidx.compose.material.TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = colors.primary,
        contentColor = Color.White,
        title = {
            Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                Image(
                    painterResource(R.drawable.rss),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(24.dp).width(24.dp).align(CenterVertically)
                )
                Text(text = "Feedreader", modifier = Modifier.align(CenterVertically).padding(18.dp, 0.dp, 0.dp, 0.dp), color = Color.White)
            }
        }
    )
}