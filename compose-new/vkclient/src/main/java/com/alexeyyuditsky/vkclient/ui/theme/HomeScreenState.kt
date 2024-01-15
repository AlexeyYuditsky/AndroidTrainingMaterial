package com.alexeyyuditsky.vkclient.ui.theme

import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.PostComment

sealed interface HomeScreenState {

    object Initial : HomeScreenState

    class FeedPosts(val feedPosts: List<FeedPost>) : HomeScreenState

    class Comments(val feedPost: FeedPost, val comments: List<PostComment>) : HomeScreenState
}