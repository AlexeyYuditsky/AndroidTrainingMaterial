package com.alexeyyuditsky.vkclient.ui

import com.alexeyyuditsky.vkclient.domain.FeedPost

sealed interface FeedPostScreenState {

    object Initial : FeedPostScreenState

    class Posts(val posts: List<FeedPost>) : FeedPostScreenState
}