package com.alexeyyuditsky.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexeyyuditsky.compose.util.log
import kotlin.random.Random

class AnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var sizeState by remember { mutableStateOf(200.dp) }
            val size by animateDpAsState(
                targetValue = sizeState,
                /*animationSpec = keyframes {
                    durationMillis = 5000
                    sizeState at 0 with LinearEasing
                    sizeState * 1.5f at 1000 with FastOutLinearInEasing
                    sizeState * 2f at 5000
                }*/
                //animationSpec = spring(Spring.DampingRatioMediumBouncy)
                animationSpec = tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
            )
            val color by rememberInfiniteTransition().animateColor(
                initialValue = Color.Red,
                targetValue = Color.Green,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000),
                    repeatMode = RepeatMode.Reverse
                )
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(size)
                        .background(color),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { sizeState += 50.dp }) {
                        Text(text = "Increase size", fontSize = 17.sp, color = Color.White)
                    }
                }
            }
        }
    }

}