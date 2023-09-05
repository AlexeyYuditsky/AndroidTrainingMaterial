package com.alexeyyuditsky.composenew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserInfoPreview()
        }
    }
}

@Preview
@Composable
fun UserInfoPreview() {
    UserInfo(name = "John", age = 28)
}

@Composable
fun UserInfo(name: String, age: Int) {
    Column {
        Text(
            text = "Привет $name тебе $age",
            color = Color.Red
        )
        Text(
            text = "Привет ",
            color = Color.Green
        )
    }
}