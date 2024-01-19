package com.alexeyyuditsky.vkclient.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit
) {
    val viewModel = viewModel<FeedPostsViewModel>()

    val screenState: State<FeedPostScreenState> =
        viewModel.screenState.observeAsState(FeedPostScreenState.Initial)

    when (val state = screenState.value) {
        is FeedPostScreenState.Initial -> {}
        is FeedPostScreenState.Posts -> FeedPostsScreen(
            paddingValues = paddingValues,
            feedPosts = state.posts,
            onCommentsClickListener = onCommentClickListener
        )
    }
}