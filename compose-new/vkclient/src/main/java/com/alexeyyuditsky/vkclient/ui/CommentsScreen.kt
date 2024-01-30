package com.alexeyyuditsky.vkclient.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.PostComment
import com.alexeyyuditsky.vkclient.ui.theme.VkClientTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    feedPost: FeedPost,
    onBackPressed: () -> Unit
) {
    val viewModel = viewModel<CommentsViewModel>(factory = ViewModelFactory(feedPost))

    val screenState = viewModel.screenState.observeAsState(CommentsScreenState.Initial)
    val currentState = screenState.value

    if (currentState !is CommentsScreenState.Comments) return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Comments for FeedPost id:${currentState.feedPost.contentText}") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 86.dp
            )
        ) {
            items(
                items = currentState.comments,
                key = { it.id }
            ) { comment ->
                CommentItem(comment)
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: PostComment
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
    ) {
        Image(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape),
            painter = painterResource(id = comment.authorAvatarId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "${comment.authorName} CommentId: ${comment.id}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.commentText,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.publicationDate,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
@Preview
private fun PreviewCommentItem() = VkClientTheme {
    CommentsScreen(
        feedPost = FeedPost(),
        onBackPressed = {}
    )
}