package com.alexeyyuditsky.vkclient.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexeyyuditsky.vkclient.R

@Composable
fun PostCard() {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.post_comunity_thumbnail),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "/dev/null",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "14:00",
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipsini asdsadsa sdas elit.",
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.post_content_image),
                contentDescription = null,
                contentScale = ContentScale.Inside
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.weight(2f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_views_count),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "916",
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Row(
                    modifier = Modifier.weight(0.8f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "7",
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Row(
                    modifier = Modifier.weight(0.8f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_comment),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "8",
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_like),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "23",
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Preview
@Composable
private fun PostCardLight() {
    VkClientTheme(darkTheme = false) {
        PostCard()
    }
}

@Preview
@Composable
private fun PostCardDark() {
    VkClientTheme(darkTheme = true) {
        PostCard()
    }
}