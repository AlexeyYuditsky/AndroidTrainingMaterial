package com.alexeyyuditsky.vkclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alexeyyuditsky.vkclient.ui.MainScreen
import com.alexeyyuditsky.vkclient.ui.theme.VkClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { VkClientTheme { MainScreen() } }
    }
}