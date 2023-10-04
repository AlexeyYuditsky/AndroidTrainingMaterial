package com.alexeyyuditsky.vkclient.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainScreen() {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = arrayOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEach {
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = { Icon(imageVector = it.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = it.titleResId)) }
                    )
                }
            }
        }
    ) {
        it
    }
}