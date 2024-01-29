package com.alexeyyuditsky.vkclient.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexeyyuditsky.vkclient.ui.theme.VkClientTheme

@Composable
fun ProfileScreen(
    viewModel: FeedPostsViewModel,
    paddingValues: PaddingValues
) {
    var counter by remember {
        mutableIntStateOf(0)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.clickable { counter++ },
            text = "Profile, counter = $counter",
            color = Color.Black
        )
    }
}

@Composable
@Preview
private fun ProfileScreenPreview() = VkClientTheme {
    ProfileScreen(viewModel = FeedPostsViewModel(), paddingValues = PaddingValues(0.dp))
}