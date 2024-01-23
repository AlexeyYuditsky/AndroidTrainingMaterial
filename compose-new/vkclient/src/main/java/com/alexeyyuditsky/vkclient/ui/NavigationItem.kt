package com.alexeyyuditsky.vkclient.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.alexeyyuditsky.vkclient.R
import com.alexeyyuditsky.vkclient.navigation.Screen

sealed class NavigationItem(
    @StringRes val id: Int,
    val screen: Screen,
    val icon: ImageVector
) {
    object Home : NavigationItem(
        id = R.string.navigation_item_main,
        screen = Screen.Home,
        icon = Icons.Outlined.Home
    )

    object Favorite : NavigationItem(
        id = R.string.navigation_item_favorite,
        screen = Screen.Favourite,
        icon = Icons.Outlined.Favorite
    )

    object Profile : NavigationItem(
        id = R.string.navigation_item_profile,
        screen = Screen.Profile,
        icon = Icons.Outlined.Person
    )
}