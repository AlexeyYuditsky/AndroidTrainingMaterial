package com.alexeyyuditsky.vkclient.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import com.alexeyyuditsky.vkclient.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val screenState: State<HomeScreenState> =
        viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when (val state = screenState.value) {
        is HomeScreenState.Initial -> {}

        is HomeScreenState.Posts -> {
            FeedPost(
                viewModel = viewModel,
                paddingValues = paddingValues,
                feedPosts = state.feedPosts
            )
        }

        is HomeScreenState.Comments -> {
            CommentScreen(
                feedPost = state.feedPost,
                comments = state.comments,
                onBackPressed = { viewModel.closeCommentScreen() }
            )
        }
    }
}