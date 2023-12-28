package com.alexeyyuditsky.vkclient.ui.theme

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.alexeyyuditsky.vkclient.R
import com.alexeyyuditsky.vkclient.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    @StringRes val id: Int,
    val icon: ImageVector
) {
    object Home : NavigationItem(
        screen = Screen.NewsFeed,
        id = R.string.navigation_item_main,
        icon = Icons.Outlined.Home
    )

    object Favorite : NavigationItem(
        screen = Screen.Favourite,
        id = R.string.navigation_item_favorite,
        icon = Icons.Outlined.Favorite
    )

    object Profile : NavigationItem(
        screen = Screen.Profile,
        id = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )
}