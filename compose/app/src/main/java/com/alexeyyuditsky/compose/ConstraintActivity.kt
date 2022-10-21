package com.alexeyyuditsky.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

// part 9
class ConstraintActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val constraints = ConstraintSet {
                val flag = createRefFor("flag")
                val favorite = createRefFor("favorite")
                val code = createRefFor("code")
                val name = createRefFor("name")
                val value = createRefFor("value")
                val difference = createRefFor("difference")

                constrain(flag) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    width = Dimension.value(85.dp)
                    height = Dimension.value(50.dp)
                }
                constrain(code) {
                    top.linkTo(value.top)
                    bottom.linkTo(value.bottom)
                    start.linkTo(flag.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }
                constrain(name) {
                    top.linkTo(difference.top)
                    bottom.linkTo(difference.bottom)
                    start.linkTo(flag.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }
                constrain(favorite) {
                    top.linkTo(code.top)
                    start.linkTo(code.end)
                    bottom.linkTo(code.bottom)
                    width = Dimension.value(35.dp)
                    height = Dimension.value(35.dp)
                }
                constrain(value) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }
                constrain(difference) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }

                createVerticalChain(value, difference, chainStyle = ChainStyle.SpreadInside)
            }

            ConstraintLayout(
                constraintSet = constraints, modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.usd),
                    contentDescription = "usd",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .layoutId("flag")
                        .padding(end = 12.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_favorite_outline),
                    contentDescription = "favorite",
                    modifier = Modifier
                        .layoutId("favorite")
                        .padding(8.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = {}
                        )
                )
                Text(text = "USD", modifier = Modifier.layoutId("code"), fontSize = 19.sp)
                Text(text = "1 Доллар", modifier = Modifier.layoutId("name"), fontSize = 15.sp)
                Text(text = "59.3749 ₽", modifier = Modifier.layoutId("value"), fontSize = 17.sp)
                Text(text = "-0.0315", modifier = Modifier.layoutId("difference"), color = Color.Red, fontSize = 15.sp)
            }

        }

    }

}
