package com.alexeyyuditsky.vkclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alexeyyuditsky.vkclient.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
        var stateFavorite by remember { mutableIntStateOf(0) }
        var stateProfile by remember { mutableIntStateOf(0) }

        when (selectedNavItem) {
            is NavigationItem.Home -> HomeScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            )

            is NavigationItem.Favorite -> TextCounter(name = "Favorite", stateFavorite) { stateFavorite++ }
            is NavigationItem.Profile -> TextCounter(name = "Profile", stateProfile) { stateProfile++ }
        }
    }
}

@Composable
fun TextCounter(name: String, state: Int, clickListener: () -> Int) {
    Text(
        modifier = Modifier.clickable { clickListener() },
        text = "$name counter: $state",
        color = Color.Black
    )
}
