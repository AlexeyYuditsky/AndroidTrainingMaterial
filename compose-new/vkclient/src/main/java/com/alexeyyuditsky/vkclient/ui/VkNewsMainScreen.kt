package com.alexeyyuditsky.vkclient.ui

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alexeyyuditsky.vkclient.navigation.AppNavGraph
import com.alexeyyuditsky.vkclient.navigation.NavigationState
import com.alexeyyuditsky.vkclient.navigation.rememberNavigationState

@Composable
fun MainScreen() {
    val navigationState: NavigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = arrayOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )

                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(
                        selected = selected,
                        onClick = { if (!selected) navigationState.navigateTo(item.screen.route) },
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
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                HomeScreen(
                    paddingValues = paddingValues,
                    onCommentClickListener = { feedPost ->
                        navigationState.navigateToComments(feedPost)
                    }
                )
            },
            commentsScreenContent = { feedPost ->
                CommentsScreen(
                    feedPost = feedPost,
                    onBackPressed = { navigationState.navHostController.popBackStack() }
                )
            },
            favouriteScreenContent = { TextCounter(name = "Favourite") },
            profileScreenContent = { TextCounter(name = "Profile") }
        )
    }
}

@Composable
fun TextCounter(name: String) {
    var count by rememberSaveable {
        mutableIntStateOf(0)
    }
    Text(
        modifier = Modifier.clickable { count++ },
        text = "$name counter: $count",
        color = Color.Black
    )
}
