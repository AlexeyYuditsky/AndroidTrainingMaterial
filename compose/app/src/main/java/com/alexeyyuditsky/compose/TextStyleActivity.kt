package com.alexeyyuditsky.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

class TextStyleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fontFamily = FontFamily(
            Font(R.font.dancing_script_bold, FontWeight.Bold),
            Font(R.font.dancing_script_medium, FontWeight.Medium),
            Font(R.font.dancing_script_regular, FontWeight.Normal),
        )

        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF101010))
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Green,
                                fontSize = 70.sp
                            )
                        ) {
                            append("J")
                        }
                        append("etpack")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Green,
                                fontSize = 70.sp
                            )
                        ) {
                            append("C")
                        }
                        append("ompose")
                    },
                    color = Color.White,
                    fontSize = 50.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }

}