package com.alexeyyuditsky.vkclient.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alexeyyuditsky.vkclient.MainViewModel

@Composable
fun FavoriteScreen(
    viewModel: MainViewModel,
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