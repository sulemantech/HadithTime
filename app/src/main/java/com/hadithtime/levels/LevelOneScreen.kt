package com.hadithtime.levels

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hadithtime.ui.theme.HadithTimeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.R
import com.hadithtime.components.HadithCard
import com.hadithtime.components.PlayerControls
import com.hadithtime.components.TopBar
import com.hadithtime.duas

@Composable
fun LevelOneScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onHomeClick: () -> Unit = {},
    startIndex: Int = 0
) {

    var currentIndex by remember { mutableIntStateOf(startIndex) }
    val systemUiController = rememberSystemUiController()
    val navigationBarColor = colorResource(id = R.color.white)
    val statusBarColor = colorResource(id = R.color.level_one_color)

    val levelOneDuas = remember { duas.filter { it.level == 1 } }
    val currentDua = levelOneDuas.getOrNull(currentIndex)

    BackHandler {
        onHomeClick()
    }
    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor)
        systemUiController.setNavigationBarColor(color = navigationBarColor)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(modifier = Modifier.weight(1f)) {
                Image(
                    painter = painterResource(id = R.drawable.dua1),
                    contentDescription = "Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    currentDua?.let {
                        TopBar(
                            dua = it,
                            onSettingsClick = onNavigateToSettings,
                            onHomeClick = onHomeClick
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    currentDua?.let {
                        HadithCard(dua = it)
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            PlayerControls(
                navController = navController,
                onNextClick = {
                    if (currentIndex < levelOneDuas.lastIndex) {
                        navController.navigate("titleScreenLevel1/1/${currentIndex + 1}")
                    }
                },
                onPreviousClick = {
                    if (currentIndex > 0) {
                        navController.navigate("titleScreenLevel1/1/${currentIndex - 1}")
                    }
                },
                level = 1
            )
        }
    }

}

//@Composable
//fun HadithCard(dua: Hadith) {
//    val MyArabicFont = FontFamily(Font(R.font.al_quran))
//    val MyEnglishFont = FontFamily(Font(R.font.lato_regular))
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(10.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Box(
//            modifier = Modifier
//                .clip(RoundedCornerShape(25.dp))
//                .background(Color.White.copy(alpha = 0.5f))
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(25.dp)
//                    .verticalScroll(rememberScrollState()),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = dua.arabic,
//                    fontSize = 22.sp,
//                    fontFamily = MyArabicFont,
//                    color = Color.Black,
//                    textAlign = TextAlign.Center,
//                    lineHeight = 44.sp
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                dua.reference?.let {
//                    Text(
//                        text = it,
//                        fontSize = 20.sp,
//                        fontFamily = MyEnglishFont,
//                        textAlign = TextAlign.Center,
//                        color = Color.Black
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                dua.englishReference?.let {
//                    Text(
//                        text = it,
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold,
//                        fontFamily = MyEnglishFont,
//                        textAlign = TextAlign.Center,
//                        color = Color.Black
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                dua.englishTranslation?.let {
//                    Text(
//                        text = it,
//                        fontSize = 14.sp,
//                        fontFamily = MyEnglishFont,
//                        color = Color.Black,
//                        textAlign = TextAlign.Center
//                    )
//                }
//            }
//        }
//    }
//}


//@Composable
//fun PlayerControls(navController: NavController, onNextClick: () -> Unit, onPreviousClick: () -> Unit){
//    var sliderValue by remember { mutableStateOf(7f) }
//    var memorizeEnabled by remember { mutableStateOf(false) }
//    val MyCountFont = FontFamily(Font(R.font.fredoka_expanded_regular))
//
//    Surface(
//        color = Color.White,
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 12.dp,end = 12.dp, bottom = 12.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth().padding(start = 7.dp, end = 7.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "1 of 49",
//                    fontSize = 14.sp,
//                    fontFamily = MyCountFont,
//                    color = Color.Black
//                )
//
////                Row(verticalAlignment = Alignment.CenterVertically) {
////                    Text(
////                        text = "Memorize",
////                        fontSize = 14.sp,
////                        color = Color.Black
////                    )
////                    Spacer(modifier = Modifier.width(8.dp))
////                    Switch(
////                        checked = memorizeEnabled,
////                        onCheckedChange = { memorizeEnabled = it }
////                    )
////                }
//            }
//
//            val sliderColor = colorResource(id = R.color.slider_color)
//
//            Slider(
//                value = sliderValue,
//                onValueChange = { sliderValue = it },
//                valueRange = 0f..40f,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(36.dp),
//                colors = SliderDefaults.colors(
//                    thumbColor = sliderColor,
//                    activeTrackColor = sliderColor,
//                    inactiveTrackColor = sliderColor.copy(alpha = 0.3f)
//                )
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth().padding(start = 7.dp, end = 7.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(text = "0:07", fontFamily = MyCountFont,fontSize = 14.sp)
//                Text(text = "0:40", fontFamily = MyCountFont, fontSize = 14.sp)
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = { }) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_repeat),
//                        contentDescription = "Repeat",
//                        modifier = Modifier.size(24.dp, 24.dp)
//                    )
//                }
//                IconButton(onClick = onPreviousClick) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_previous),
//                        contentDescription = "Previous",
//                        modifier = Modifier.size(20.dp, 20.dp)
//                    )
//                }
//                IconButton(onClick = { },
//                    modifier = Modifier.size(56.dp)
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_play),
//                        contentDescription = "Play",
//                        modifier = Modifier.size(50.dp)
//                    )
//                }
//                IconButton(onClick = onNextClick) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_next),
//                        contentDescription = "Next",
//                        modifier = Modifier.size(20.dp, 20.dp)
//                    )
//                }
//                IconButton(onClick = {} ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_tabler_heart),
//                        contentDescription = "Favorite",
//                        modifier = Modifier.size(24.dp, 24.dp)
//                    )
//                }
//            }
//        }
//    }
//}

//@Composable
//fun TopBar1(
//    title: String,
//    onSettingsClick: () -> Unit,
//    onHomeClick: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 16.dp, start = 4.dp, end = 4.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // Home Icon
//        IconButton(
//            onClick = { onHomeClick() },
//            modifier = Modifier.size(56.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.home_icon),
//                contentDescription = "Home",
//                modifier = Modifier.size(42.dp)
//            )
//        }
//
//        // Title centered
//        Box(
//            modifier = Modifier.weight(1f),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = title,
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                color = Color.White,
//                textAlign = TextAlign.Center
//            )
//        }
//
//        // Settings Icon
//        IconButton(
//            onClick = { onSettingsClick() },
//            modifier = Modifier.size(56.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.setting_icon),
//                contentDescription = "Settings",
//                modifier = Modifier.size(42.dp)
//            )
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun PreviewCleanlinessScreen() {
    HadithTimeTheme {
        val navController = rememberNavController() // ✅ Create a mock NavController
        LevelOneScreen(
            navController = navController,
            onNavigateToSettings = {},
            onHomeClick = {}
        )
    }
}