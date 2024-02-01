package com.alexeyyuditsky.vkclient.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.FeedPostType

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST) {
                    type = NavType.FeedPostType
                }
            )
        ) {
            val arguments = it.arguments ?: throw RuntimeException("args is null")
            val feedPost = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments.getParcelable(Screen.KEY_FEED_POST, FeedPost::class.java)
            } else {
                @Suppress("DEPRECATION")
                arguments.getParcelable(Screen.KEY_FEED_POST)
            } as FeedPost

            commentsScreenContent(feedPost)
        }
    }
}