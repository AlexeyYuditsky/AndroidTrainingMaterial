package com.alexeyyuditsky.composenew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.alexeyyuditsky.composenew.ui.theme.ComposeNewTheme
import com.alexeyyuditsky.composenew.ui.theme.InstagramProfileCard

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Test() }
    }

    @Composable
    private fun Test() {
        val models: State<List<InstagramModel>> = viewModel.models.observeAsState(listOf())
        ComposeNewTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            )
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(models.value) { model ->
                    InstagramProfileCard(
                        model = model,
                        onFollowedButtonClickListener = {
                            viewModel.changeFollowingStatus(it)
                        }
                    )
                }
            }
        }
    }
}

