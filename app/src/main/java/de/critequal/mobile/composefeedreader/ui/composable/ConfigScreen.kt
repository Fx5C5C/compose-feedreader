package de.critequal.mobile.composefeedreader.ui.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.critequal.mobile.composefeedreader.FeedViewModel
import de.critequal.mobile.composefeedreader.persistence.FeedURL
import de.critequal.mobile.composefeedreader.ui.theme.ComposefeedreaderTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(
    navController: NavController,
    viewModel: FeedViewModel = get(),
    rememberedScope: CoroutineScope = rememberCoroutineScope()
) {
    val urls: List<FeedURL> by viewModel.getURLs().observeAsState(listOf())

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
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    for (url in urls) {
                        Text(
                            text = url.url,
                            modifier = Modifier.padding(8.dp).background(Color.LightGray)
                        )
                    }
                }
            }
        }
    )
}