package com.alexeyyuditsky.vkclient.ui.theme

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.alexeyyuditsky.vkclient.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val selectedNavItem by viewModel.selectedNavItem.observeAsState(NavigationItem.Home)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = arrayOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    NavigationBarItem(
                        selected = selectedNavItem == item,
                        onClick = { viewModel.selectNavItem(item) },
                        icon = { Icon(imageVector = item.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = item.id)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        when (selectedNavItem) {
            is NavigationItem.Home -> HomeScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            )

            NavigationItem.Favorite -> FavoriteScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            )

            NavigationItem.Profile -> ProfileScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }
    }
}
