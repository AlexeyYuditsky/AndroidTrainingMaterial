package com.alexeyyuditsky.composenew.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun InstagramProfileCard() {
    Card(
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Yellow)
            )

            UserStatistics("Posts", "6,950")
            UserStatistics("Followers", "436M")
            UserStatistics("Following", "76")
        }

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Instagram")
            Text(text = "#YoursToMake")
            Text(text = "www.facebook.com/emotional_health")
            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape
            ) {
                Text(text = "Follow")
            }
        }
    }
}

@Composable
private fun UserStatistics(
    tittle: String,
    value: String
) {
    Column(
        modifier = Modifier.height(100.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = tittle,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun PreviewCardLight() {
    ComposeNewTheme(
        darkTheme = false
    ) {
        InstagramProfileCard()
    }
}

@Preview
@Composable
fun PreviewCardDark() {
    ComposeNewTheme(
        darkTheme = true
    ) {
        InstagramProfileCard()
    }
}