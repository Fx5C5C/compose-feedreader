package de.critequal.mobile.composefeedreader.ui.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import de.critequal.mobile.composefeedreader.navigation.NavRoutes
import de.critequal.mobile.composefeedreader.ui.theme.DarkColorScheme
import de.critequal.mobile.composefeedreader.ui.theme.LightColorScheme

@Composable
fun BottomNavigationBar(navController: NavController) {
    val colors = if(isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
    val items = listOf(
        NavRoutes.Home,
        NavRoutes.AddFeed
    )
    BottomNavigation(
        backgroundColor = colors.primary,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                selected = currentRoute == item.route,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                onClick = {
                    if (!navController.isFragmentInBackStack(item.route)) {
                        navController.navigate(item.route)
                    } else {
                        navController.popBackStack(item.route, false)
                    }
                }
            )
        }
    }
}

fun NavController.isFragmentInBackStack(route: String) =
    try {
        getBackStackEntry(route)
        true
    } catch (e: Exception) {
        false
    }