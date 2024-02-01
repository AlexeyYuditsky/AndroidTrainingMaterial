package com.alexeyyuditsky.vkclient.navigation

import com.alexeyyuditsky.vkclient.domain.FeedPost

sealed class Screen(
    val route: String
) {
    object Home : Screen(ROUTE_HOME)
    object Favourite : Screen(ROUTE_FAVOURITE)
    object Profile : Screen(ROUTE_PROFILE)

    object NewsFeed : Screen(ROUTE_NEWS_FEED)

    object Comments : Screen(ROUTE_COMMENTS) {

        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPost): String = "$ROUTE_FOR_ARGS/${feedPost.encode()}"
    }

    companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"

        const val KEY_FEED_POST = "feedPost"

        const val ROUTE_NEWS_FEED = "newsFeed"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST}"
    }
}