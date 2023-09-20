package com.alexeyyuditsky.composenew.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexeyyuditsky.composenew.R

@Preview
@Composable
fun InstagramProfileCardNew() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = null
                )
                UserState("6,950", "Posts")
                UserState("436M", "Followers")
                UserState("76", "Following")
            }

            SimpleBigCursiveText("Instagram")
            SimpleSmallText("#YoursToMake")
            SimpleSmallText("www.facebook.com/emotional_health")
            Button(
                onClick = {},
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "Follow")
            }
        }
    }
}

@Composable
fun UserState(title: String, value: String) {
    Column {
        Text(text = value)
        Text(text = title)
    }
}

@Composable
fun SimpleSmallText(title: String) {
    Text(
        text = title,
        fontSize = 14.sp
    )
}

@Composable
fun SimpleBigCursiveText(title: String) {
    Text(
        text = title,
        fontSize = 32.sp,
        fontFamily = FontFamily.Cursive
    )
}