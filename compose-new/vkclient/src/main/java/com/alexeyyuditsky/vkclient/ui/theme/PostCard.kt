package com.alexeyyuditsky.vkclient.ui.theme

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexeyyuditsky.vkclient.R

@Composable
fun PostCard() {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
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

                Image(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null
                )
            }

            /*Text(
                text = "asdasdsadsadsadsd a sd sa dsad sdsadasdas da sadsadasdasdas"
            )

            Image(
                modifier = Modifier.size(500.dp),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )

            Row {
                Text(text = "206")
                Image(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_views_count),

                    contentDescription = null
                )
                Text(text = "206")
                Image(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_share),

                    contentDescription = null
                )
                Text(text = "11")
                Image(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_comment),

                    contentDescription = null
                )
                Text(text = "491")
                Image(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_like),
                    contentDescription = null
                )
            }*/
        }
    }
}

@Preview
@Composable
fun PostCardLight() {
    VkClientTheme(darkTheme = false) {
        PostCard()
    }
}

@Preview
@Composable
fun PostCardDark() {
    VkClientTheme(darkTheme = true) {
        PostCard()
    }
}