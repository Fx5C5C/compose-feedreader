package de.critequal.mobile.composefeedreader.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavRoutes(
    val route: String,
    val title: String,
    val icon: ImageVector)
{
    object Home : NavRoutes("home", "Home", Icons.Default.Home)
    object AddFeed : NavRoutes("config", "Add", Icons.Default.Settings)
}