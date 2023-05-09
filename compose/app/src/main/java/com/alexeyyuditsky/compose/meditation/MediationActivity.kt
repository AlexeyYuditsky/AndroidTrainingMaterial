package com.alexeyyuditsky.compose.meditation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alexeyyuditsky.compose.ui.theme.ComposeTheme

class MediationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                HomeScreen()
            }
        }
    }

}