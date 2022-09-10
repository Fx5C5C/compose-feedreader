package de.critequal.mobile.composefeedreader.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object AddFeed : NavRoutes("addFeed")
}