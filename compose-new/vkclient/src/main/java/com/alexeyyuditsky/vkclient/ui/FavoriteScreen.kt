package com.alexeyyuditsky.vkclient.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexeyyuditsky.vkclient.ui.theme.VkClientTheme

@Composable
fun FavoriteScreen(
    viewModel: FeedPostsViewModel,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Hello",
            color = Color.Black
        )
    }
}

@Composable
@Preview
private fun FavoriteScreenPreview() = VkClientTheme {
    FavoriteScreen(viewModel = FeedPostsViewModel(), paddingValues = PaddingValues(0.dp))
}