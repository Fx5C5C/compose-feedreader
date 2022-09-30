package de.critequal.mobile.composefeedreader.ui.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import de.critequal.mobile.composefeedreader.FeedViewModel
import de.critequal.mobile.composefeedreader.R
import de.critequal.mobile.composefeedreader.ui.theme.ComposefeedreaderTheme
import de.critequal.mobile.composefeedreader.ui.theme.RedTranslucent40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class, ExperimentalUnitApi::class)
@Composable
fun ConfigScreen(
    navController: NavController,
    viewModel: FeedViewModel = get(),
    rememberedScope: CoroutineScope = rememberCoroutineScope()
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.swipe))
    rememberedScope.launch {
        viewModel.updateFeedURLs()
    }

    Scaffold(
        floatingActionButton = {
            ComposefeedreaderTheme {
                FloatingActionButton(onClick = { navController.navigateUp()  }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )

                }
            }
        },
        content = {
            ComposefeedreaderTheme {
                val textState = remember { mutableStateOf(TextFieldValue()) }
                Column (
                    modifier = Modifier.wrapContentWidth(
                        align = Alignment.CenterHorizontally
                    )) {
                    TextField(
                        value = textState.value,
                        placeholder = { Text("Feed Url") },
                        onValueChange = { textState.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    Button(
                        onClick = {
                            rememberedScope.launch {
                                viewModel.addFeedURL(textState.value.text)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add"
                        )
                        Text(text = "Add")
                    }
                    Divider(
                        thickness = 2.dp,
                        modifier = Modifier.padding(8.dp)
                    )
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 8.dp, 10.dp, 8.dp)
                            .size(24.dp)
                            .scale(4F)
                    )
                    LazyColumn {
                        items(items = viewModel.urls, key = { it.uid }) { url ->
                            var dismissState = rememberDismissState(initialValue = DismissValue.Default)
                            if (dismissState.isDismissed((DismissDirection.EndToStart))) {
                                rememberedScope.launch {
                                    viewModel.removeURL(url)
                                    dismissState = DismissState(DismissValue.Default)
                                }
                            }
                            SwipeToDismiss(
                                state = dismissState,
                                background = {
                                    val color = when (dismissState.dismissDirection) {
                                        DismissDirection.StartToEnd -> Color.Green
                                        DismissDirection.EndToStart -> RedTranslucent40
                                        null -> Color.Transparent
                                    }
                                    val direction = dismissState.dismissDirection

                                    if (direction == DismissDirection.EndToStart) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(color)
                                                .padding(8.dp)
                                        ) {
                                            Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                                )
                                                Spacer(modifier = Modifier.heightIn(5.dp))
                                                Text(
                                                    text = "Remove",
                                                    textAlign = TextAlign.Center,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.LightGray
                                                )

                                            }
                                        }
                                    }
                                },
                                dismissContent = {
                                    Card(
                                        elevation = CardDefaults.cardElevation(2.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(4.dp),
                                        colors = CardDefaults.cardColors(
                                            Color.White
                                        )
                                    ) {
                                        Text(
                                            text = url.url,
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .align(Alignment.Start),
                                            fontSize = TextUnit(12F, TextUnitType.Sp),
                                            maxLines = 1
                                        )
                                    }
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                        }
                    }
                }
            }
        }
    )
}