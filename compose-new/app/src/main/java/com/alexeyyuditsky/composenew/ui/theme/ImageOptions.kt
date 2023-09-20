package com.alexeyyuditsky.composenew.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TestImage() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.Cyan)
    ) {
        Image(
            modifier = Modifier
                .background(Color.Green)
                .padding(25.dp)
                .clip(CircleShape)
                .size(100.dp)
                .background(Color.Red)
                .padding(25.dp),
            painter = ColorPainter(Color.Yellow),
            contentDescription = "",
            contentScale = ContentScale.FillHeight
        )
    }
}