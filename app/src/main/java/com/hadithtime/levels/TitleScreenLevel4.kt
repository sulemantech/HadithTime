package com.hadithtime.levels

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hadithtime.ui.theme.HadithTimeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.R
import com.hadithtime.model.LevelAssets
import com.hadithtime.model.levelFourTexts
import com.hadithtime.model.levelTwoTexts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TitleScreenLevel4(navController: NavController, level: Int, nextIndex: Int) {
    val levelOneAssets = listOf(
        LevelAssets(
            background = R.drawable.level_four_bg,
            badge = R.drawable.level_four_badge,
            girl = R.drawable.new_girl_level4,
            bubble = R.drawable.new_bubble_level1,
            statusBarColor = R.color.level_title_four_color,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelFourScreen"
        ),
        LevelAssets(
            background = R.drawable.level_four_bg,
            badge = R.drawable.level_four_badge,
            girl = R.drawable.new_girl_level4,
            bubble = R.drawable.new_bubble_level1,
            statusBarColor = R.color.level_title_four_color,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelFourScreen"
        ),
        LevelAssets(
            background = R.drawable.level_four_bg,
            badge = R.drawable.level_four_badge,
            girl = R.drawable.new_girl_level4,
            bubble = R.drawable.new_bubble_level1,
            statusBarColor = R.color.level_title_four_color,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelFourScreen"
        ),
        LevelAssets(
            background = R.drawable.level_four_bg,
            badge = R.drawable.level_four_badge,
            girl = R.drawable.new_girl_level4,
            bubble = R.drawable.new_bubble_level1,
            statusBarColor = R.color.level_title_four_color,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelFourScreen"
        ),
        LevelAssets(
            background = R.drawable.level_four_bg,
            badge = R.drawable.level_four_badge,
            girl = R.drawable.new_girl_level4,
            bubble = R.drawable.new_bubble_level1,
            statusBarColor = R.color.level_title_four_color,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelFourScreen"
        ),
        LevelAssets(
            background = R.drawable.level_four_bg,
            badge = R.drawable.level_four_badge,
            girl = R.drawable.new_girl_level4,
            bubble = R.drawable.new_bubble_level1,
            statusBarColor = R.color.level_title_four_color,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelFourScreen"
        ),
        LevelAssets(
            background = R.drawable.level_four_bg,
            badge = R.drawable.level_four_badge,
            girl = R.drawable.new_girl_level4,
            bubble = R.drawable.new_bubble_level1,
            statusBarColor = R.color.level_title_four_color,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelFourScreen"
        ),
    )
    val assets = levelOneAssets.getOrElse(nextIndex) {
        levelOneAssets.last()
    }

    val text = levelFourTexts.getOrElse(nextIndex) {
        levelFourTexts.last()
    }
    val systemUiController = rememberSystemUiController()
    val statusBarColor = colorResource(id = assets.statusBarColor)
    val navigationBarColor = colorResource(id = assets.navigationBarColor)

    val MyEnglishFont = FontFamily(Font(R.font.sandy_kids))
    val MyArabicFont = FontFamily(Font(R.font.tinta_arabic))

    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor)
        systemUiController.setNavigationBarColor(color = navigationBarColor)
    }

    val girlOffsetX = remember { Animatable(-300f) }
    val bubbleAlpha = remember { Animatable(0f) }
    val bubbleOffsetY = remember { Animatable(20f) }
    val bubbleScale = remember { Animatable(0.5f) }

    LaunchedEffect(Unit) {
        girlOffsetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )

        delay(300)

        launch {
            bubbleScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
            )
        }

        launch {
            bubbleOffsetY.animateTo(
                targetValue = -20f,
                animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
            )
        }

        launch {
            bubbleAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
            )
        }

        delay(1000)
        navController.navigate("${assets.nextRoute}/$nextIndex")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = assets.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(id = assets.badge),
            contentDescription = "Badge",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 26.dp, end = 16.dp)
                .height(40.dp)
                .width(120.dp)
        )

        // Girl image with offset animation
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp)
                .offset(x = girlOffsetX.value.dp)
                .height(400.dp)
                .width(224.dp)
        ) {
            Image(
                painter = painterResource(id = assets.girl),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )


            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 116.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BoxWithConstraints {
                    val maxWidth = constraints.maxWidth.toFloat()
                    var textSize by remember { mutableStateOf(32.sp) }

                    // Estimate if text will overflow and shrink accordingly
                    Text(
                        text = text.englishText,
                        fontSize = textSize,
                        fontFamily = MyEnglishFont,
                        lineHeight = 26.sp,
                        softWrap = false,
                        onTextLayout = { result ->
                            if (result.didOverflowWidth) {
                                textSize *= 0.9 // Reduce size progressively
                            }
                        },
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = colorResource(R.color.bubble_text)
                    )
                }
            }

        }

        // Bubble with animation
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(
                    x = 40.dp,
                    y = (-350).dp + bubbleOffsetY.value.dp
                )
                .graphicsLayer(
                    scaleX = bubbleScale.value,
                    scaleY = bubbleScale.value
                )
                .alpha(bubbleAlpha.value)
                .size(width = 300.dp, height = 180.dp)
        ) {
            Image(
                painter = painterResource(id = assets.bubble),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 38.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                BoxWithConstraints {
                    val maxWidth = constraints.maxWidth.toFloat()
                    var textSize by remember { mutableStateOf(31.sp) }

                    // Estimate if text will overflow and shrink accordingly
                    Text(
                        text = text.arabicText,
                        fontSize = textSize,
                        fontFamily = MyArabicFont,
                        softWrap = false,
                        lineHeight = 30.sp,
                        onTextLayout = { result ->
                            if (result.didOverflowWidth) {
                                textSize *= 0.10 // Reduce size progressively
                            }
                        },
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = colorResource(R.color.bubble_text)
                    )
                }
            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewTitleScreen4() {
    HadithTimeTheme {
        TitleScreenLevel4(
            navController = rememberNavController(),
            level = 4,
            nextIndex = 0
        )
    }
}
