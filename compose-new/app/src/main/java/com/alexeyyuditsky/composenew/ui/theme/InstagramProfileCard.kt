package com.alexeyyuditsky.composenew.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexeyyuditsky.composenew.InstagramModel
import com.alexeyyuditsky.composenew.R

@Composable
fun InstagramProfileCard(
    model: InstagramModel,
    onFollowedButtonClickListener: (InstagramModel) -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = null
                )
                UserStatistics(title = "6,950", value = "Posts")
                UserStatistics(title = "436M", value = "Followers")
                UserStatistics(title = "76", value = "Following")
            }
            SimpleBigCursiveText("Following${model.id}")
            SimpleSmallText("#${model.title}")
            SimpleSmallText("www.facebook.com/emotional_health")
            FollowButton(isFollowed = model.isFollowed) { onFollowedButtonClickListener(model) }
        }
    }
}

@Composable
private fun FollowButton(
    isFollowed: Boolean,
    clickListener: () -> Unit
) {
    Button(
        onClick = { clickListener.invoke() },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFollowed) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.primary
            }
        )
    ) {
        val text = if (isFollowed) {
            stringResource(R.string.unfollow)
        } else {
            stringResource(R.string.follow)
        }
        Text(text = text)
    }
}

@Composable
fun UserStatistics(title: String, value: String) {
    Column(
        modifier = Modifier.height(60.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Cursive,
        )
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold
        )
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