package com.alexeyyuditsky.composenew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexeyyuditsky.composenew.ui.theme.ComposeNewTheme
import com.alexeyyuditsky.composenew.ui.theme.InstagramProfileCard

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Test() }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Test() {
        val models: State<List<InstagramModel>> = viewModel.models.observeAsState(listOf())
        ComposeNewTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            )
            LazyColumn {
                items(models.value, key = { it.id }) { model ->
                    val dismissState = rememberDismissState()

                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        viewModel.deleteItem(model)
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                    .background(Color.Red.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    modifier = Modifier.padding(end = 16.dp),
                                    text = "delete item",
                                    color = Color.Black,
                                    fontSize = 24.sp
                                )
                            }
                        },
                        dismissContent = {
                            InstagramProfileCard(
                                model = model,
                                onFollowedButtonClickListener = {
                                    viewModel.changeFollowingStatus(it)
                                }
                            )
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                }
            }
        }
    }
}

