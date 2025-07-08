package com.hadithtime.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.hadithtime.HadithViewModel
import com.hadithtime.R
import com.hadithtime.model.Hadith

@Composable
fun PlayerControls(
    navController: NavController,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    level: Int,
    dua: Hadith,
    viewModel: HadithViewModel
) {
    var sliderValue by remember { mutableStateOf(7f) }
    var isPlaying by remember { mutableStateOf(false) }

    val MyCountFont = FontFamily(Font(R.font.fredoka_regular))
    val filteredDuas by viewModel.filteredDuas.collectAsState()
    val updatedDua = filteredDuas.find { it.id == dua.id } ?: dua
    val currentIndex = filteredDuas.indexOfFirst { it.id == dua.id } + 1
    val totalCount = filteredDuas.size

    Surface(color = Color.White, modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 7.dp, end = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "$currentIndex of $totalCount",
                    fontSize = 14.sp,
                    fontFamily = MyCountFont,
                    color = colorResource(R.color.black_arabic)
                )
            }


            val sliderColor = when (level) {
                1 -> colorResource(id = R.color.slider_level1)
                2 -> colorResource(id = R.color.slider_level2)
                3 -> colorResource(id = R.color.slider_level3)
                4 -> colorResource(id = R.color.slider_level4)
                5 -> colorResource(id = R.color.slider_level5)
                6 -> colorResource(id = R.color.slider_level6)
                7 -> colorResource(id = R.color.slider_level7)
                else -> colorResource(id = R.color.slider_color)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 7.dp, end = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("0:07", fontFamily = MyCountFont, fontSize = 14.sp, color = colorResource(R.color.black_arabic))
                Text("0:40", fontFamily = MyCountFont, fontSize = 14.sp, color = colorResource(R.color.black_arabic))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Repeat logic */ }) {
                    Image(painter = painterResource(id = R.drawable.ic_repeat), contentDescription = "Repeat", modifier = Modifier.size(24.dp))
                }

                IconButton(onClick = onPreviousClick) {
                    Image(painter = painterResource(id = R.drawable.ic_previous), contentDescription = "Previous", modifier = Modifier.size(20.dp))
                }

                val interactionSource = remember { MutableInteractionSource() }
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clickable(interactionSource = interactionSource, indication = null) {
                            isPlaying = !isPlaying
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = if (isPlaying) R.drawable.icon_pause else R.drawable.icon_play),
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        modifier = Modifier.size(50.dp)
                    )
                }

                IconButton(onClick = onNextClick) {
                    Image(painter = painterResource(id = R.drawable.icon_next), contentDescription = "Next", modifier = Modifier.size(20.dp))
                }

                val favoriteIcon = if (updatedDua.isFavorite) R.drawable.icon_filled_favorite else R.drawable.icon_favorite

                IconButton(onClick = { viewModel.toggleFavorite(updatedDua) }) {
                    AnimatedContent(targetState = favoriteIcon, transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    }) { icon ->
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
