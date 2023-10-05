package com.alexeyyuditsky.vkclient.ui.theme

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.alexeyyuditsky.vkclient.R

sealed class NavigationItem(
    @StringRes val id: Int,
    val icon: ImageVector
) {
    object Home : NavigationItem(
        id = R.string.navigation_item_main,
        icon = Icons.Outlined.Home
    )

    object Favorite : NavigationItem(
        id = R.string.navigation_item_favorite,
        icon = Icons.Outlined.Favorite
    )

    object Profile : NavigationItem(
        id = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )
}