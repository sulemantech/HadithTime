package com.hadithtime.levels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hadithtime.ui.theme.HadithTimeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.R

@Composable
fun LevelFourScreen(
    onNavigateToSettings: () -> Unit,
    onHomeClick: () -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    val navigationBarColor = colorResource(id = R.color.white)
    val statusBarColor = colorResource(id = R.color.level_title_four_color)
    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor)
        systemUiController.setNavigationBarColor(color = navigationBarColor)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
    ) {
        val MyArabicFont = FontFamily(Font(R.font.al_quran))
        val MyEnglishFont = FontFamily(Font(R.font.lato_regular))

        Image(
            painter = painterResource(id = R.drawable.dua4),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            TopBar4(
                title = "Purification",
                onSettingsClick = onNavigateToSettings,
                onHomeClick = onHomeClick
            )

            Spacer(modifier = Modifier.height(24.dp))
            HadithCard4()

            Spacer(modifier = Modifier.weight(1f))

        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            PlayerControls4()
        }
    }
}

@Composable
fun HadithCard4() {
    val MyArabicFont = FontFamily(Font(R.font.al_quran))
    val MyEnglishFont = FontFamily(Font(R.font.lato_regular))

    Box(
        modifier = Modifier
            .fillMaxWidth().padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(Color.White.copy(alpha = 0.5f))
                .shadow(0.dp, RoundedCornerShape(20.dp), clip = false)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 25.dp , start = 10.dp, end = 10.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "عَنْ أَبِي مَالِكٍ الأَشْعَرِيِّ رضى الله عنه، قَالَ\n" +
                            " قَالَ رَسُولُ اللَّهِﷺ\n" +
                            "الطُّهُورُ شَطْرُ الإِيمَانِ وَالْحَمْدُ لِلَّهِ\n" +
                            " تَمْلأُ الْمِيزَانَ",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontFamily = MyArabicFont,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "(صحيح مُسلم)",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    fontFamily = MyEnglishFont,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Abu Malik at-Ash'ari (May Allah be pleased with him) reported: \nThe Messenger of Allah ﷺ said:",
                    fontSize = 16.sp,
                    fontFamily = MyEnglishFont,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = " “Cleanliness is half of faith and\n" +
                            "al-Hamdu Lillah (all praise and gratitude is for Allah alone)\n" +
                            "fills the scale.”",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MyEnglishFont,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "(Sahih al-Bukhari)",
                    fontSize = 14.sp,
                    fontFamily = MyEnglishFont,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PlayerControls4() {
    var sliderValue by remember { mutableStateOf(7f) }
    var memorizeEnabled by remember { mutableStateOf(false) }
    val MyCountFont = FontFamily(Font(R.font.fredoka_expanded_regular))

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
                    .fillMaxWidth().padding(start = 7.dp, end = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "1 of 49",
                    fontSize = 14.sp,
                    fontFamily = MyCountFont,
                    color = Color.Black
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

            val sliderColor = colorResource(id = R.color.slider_color)

            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = 0f..40f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
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
                Text(text = "0:07", fontFamily = MyCountFont,fontSize = 14.sp)
                Text(text = "0:40", fontFamily = MyCountFont, fontSize = 14.sp)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
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
                IconButton(onClick = { }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_previous),
                        contentDescription = "Previous",
                        modifier = Modifier.size(20.dp, 20.dp)
                    )
                }
                IconButton(onClick = { },
                    modifier = Modifier.size(56.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = "Play",
                        modifier = Modifier.size(50.dp)
                    )
                }
                IconButton(onClick = { }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next",
                        modifier = Modifier.size(20.dp, 20.dp)
                    )
                }
                IconButton(onClick = { }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_tabler_heart),
                        contentDescription = "Favorite",
                        modifier = Modifier.size(24.dp, 24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar4(
    title: String,
    onSettingsClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Home Icon
        IconButton(
            onClick = { onHomeClick() },
            modifier = Modifier.size(56.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_icon),
                contentDescription = "Home",
                modifier = Modifier.size(42.dp)
            )
        }

        // Title centered
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        // Settings Icon
        IconButton(
            onClick = { onSettingsClick() },
            modifier = Modifier.size(56.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.setting_icon),
                contentDescription = "Settings",
                modifier = Modifier.size(42.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCleanlinessScreen4() {
    HadithTimeTheme {
        LevelFourScreen(
            onNavigateToSettings = {},
            onHomeClick = {}
        )
    }
}
