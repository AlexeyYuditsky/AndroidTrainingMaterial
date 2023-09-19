package com.alexeyyuditsky.composenew.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun TestText() {
    Text(
        text = "Hello World!",
        fontSize = 24.sp,
        fontWeight = FontWeight.W900,
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Cursive,
        textDecoration = TextDecoration.combine(
            listOf(
                TextDecoration.LineThrough,
                TextDecoration.Underline
            )
        ),
    )
}

@Preview
@Composable
fun TestText2() {
    Text(
        buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Hello")
            }
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                append(" ")
            }
            withStyle(SpanStyle(fontSize = 30.sp, textDecoration = TextDecoration.LineThrough)) {
                append("World!")
            }
        }
    )
}