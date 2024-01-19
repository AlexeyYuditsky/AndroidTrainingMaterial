package com.alexeyyuditsky.vkclient.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.ui.theme.VkClientTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FeedPostsScreen(
    paddingValues: PaddingValues,
    feedPosts: List<FeedPost>,
    onCommentsClickListener: (FeedPost) -> Unit
) {
    val viewModel = viewModel<FeedPostsViewModel>()

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = feedPosts,
            key = { it.id }
        ) { feedPost ->
            val dismissState = rememberDismissState()

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.remove(feedPost)
            }

            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {},
                dismissContent = {
                    PostCard(
                        feedPost = feedPost,
                        onViewsClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onShareClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onCommentClickListener = {
                            onCommentsClickListener(feedPost)
                        },
                        onLikeClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        }
                    )
                }
            )
        }
    }
}

@Composable
@Preview
private fun FeedPostsPreview() = VkClientTheme {
    FeedPostsScreen(
        paddingValues = PaddingValues(),
        feedPosts = listOf(FeedPost(0), FeedPost(1), FeedPost(2), FeedPost(3), FeedPost(4))
    ) {}
}