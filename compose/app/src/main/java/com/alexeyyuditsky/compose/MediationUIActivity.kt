package com.alexeyyuditsky.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alexeyyuditsky.compose.ui.HomeScreen
import com.alexeyyuditsky.compose.ui.theme.ComposeTheme

class MediationUIActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                HomeScreen()
            }
        }
    }

}