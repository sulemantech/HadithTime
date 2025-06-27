package com.hadithtime.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hadithtime.R

@Composable
fun PlayerControls(
    navController: NavController,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    level: Int
)
{    var sliderValue by remember { mutableStateOf(7f) }
    var memorizeEnabled by remember { mutableStateOf(false) }
    val MyCountFont = FontFamily(Font(R.font.fredoka_regular))

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp,end = 12.dp, bottom = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(top = 12.dp, start = 7.dp, end = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "1 of 49",
                    fontSize = 14.sp,
                    fontFamily = MyCountFont,
                    color = colorResource(R.color.black_arabic)
                )

//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text(
//                        text = "Memorize",
//                        fontSize = 14.sp,
//                        color = Color.Black
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Switch(
//                        checked = memorizeEnabled,
//                        onCheckedChange = { memorizeEnabled = it }
//                    )
//                }
            }

            val sliderColor = when (level) {
                1 -> colorResource(id = R.color.slider_level1)
                2 -> colorResource(id = R.color.slider_level2)
                3 -> colorResource(id = R.color.slider_level3)
                4 -> colorResource(id = R.color.slider_level4)
                5 -> colorResource(id = R.color.slider_level5)
                6 -> colorResource(id = R.color.slider_level6)
                7 -> colorResource(id = R.color.slider_level7)
                else -> colorResource(id = R.color.slider_color) // default
            }

            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = 0f..40f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                colors = SliderDefaults.colors(
                    thumbColor = sliderColor,
                    activeTrackColor = sliderColor,
                    inactiveTrackColor = sliderColor.copy(alpha = 0.3f)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 7.dp, end = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "0:07", fontFamily = MyCountFont,fontSize = 14.sp, color = colorResource(R.color.black_arabic))
                Text(text = "0:40", fontFamily = MyCountFont, fontSize = 14.sp, color = colorResource(R.color.black_arabic))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp,bottom=8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_repeat),
                        contentDescription = "Repeat",
                        modifier = Modifier.size(24.dp, 24.dp)
                    )
                }
                IconButton(onClick = onPreviousClick) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_previous),
                        contentDescription = "Previous",
                        modifier = Modifier.size(20.dp, 20.dp)
                    )
                }
                var isPlaying by remember { mutableStateOf(false) }

                val interactionSource = remember { MutableInteractionSource() }

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null // disables ripple/focus
                        ) {
                            isPlaying = !isPlaying
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isPlaying) R.drawable.icon_pause else R.drawable.icon_play
                        ),
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        modifier = Modifier.size(50.dp)
                    )
                }

                IconButton(onClick = onNextClick) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next",
                        modifier = Modifier.size(20.dp, 20.dp)
                    )
                }
                var isFavorite by remember { mutableStateOf(false) }

                IconButton(
                    onClick = { isFavorite = !isFavorite }
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isFavorite) R.drawable.icon_filled_favorite else R.drawable.icon_favorite
                        ),
                        contentDescription = if (isFavorite) "Unfavorite" else "Favorite",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
