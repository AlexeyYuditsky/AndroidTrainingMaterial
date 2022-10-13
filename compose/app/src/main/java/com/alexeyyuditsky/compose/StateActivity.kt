package com.alexeyyuditsky.compose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

class StateActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val color = remember { mutableStateOf(Color.Yellow) }

            Row {
                ColorBox(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) { color.value = it }
                Box(
                    modifier = Modifier
                        .background(color.value)
                        .fillMaxSize()
                        .weight(1f)
                ).apply { Log.d("MyLog", "Box") }
            }
        }
    }

}

@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    updateColor: (color: Color) -> Unit
) {
    Box(modifier = modifier
        .apply { Log.d("MyLog", "ColorBox") }
        .background(Color.Red)
        .clickable {
            updateColor(
                Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat()
                )
            )
        }
    )
}