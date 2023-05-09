package com.alexeyyuditsky.compose

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alexeyyuditsky.compose.util.log

class MusicKnobActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center) {
                MusicKnob(onValueChange = {})
            }
        }
    }

}

@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
    onValueChange: (Float) -> Unit
) {
    var rotation by remember { mutableStateOf(limitingAngle) }
    var touchX by remember { mutableStateOf(0f) }
    var touchY by remember { mutableStateOf(0f) }
    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }

    Image(
        painter = painterResource(id = R.drawable.music_knob),
        contentDescription = "Music knob",
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates: LayoutCoordinates ->
                val windowSize = coordinates.boundsInWindow().size
                centerX = windowSize.width / 2f
                centerY = windowSize.height / 2f
            }
            /*.pointerInteropFilter { event: MotionEvent ->
                touchX = event.x
                touchY = event.y
            }*/
    )

   /* log(centerX)
    log(centerY)*/

}
