package com.alexeyyuditsky.vkclient.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    paddingValues: PaddingValues
) {
    val screenState: State<HomeScreenState> =
        viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when (val state = screenState.value) {
        is HomeScreenState.Initial -> {}

        is HomeScreenState.Posts -> {
            FeedPostScreen(
                feedPosts = state.posts
            )
        }

        is HomeScreenState.Comments -> {
            CommentScreen(
                onBackPressed = { viewModel.closeCommentScreen() }
            )
            BackHandler(onBack = { viewModel.closeCommentScreen() })
        }
    }
}