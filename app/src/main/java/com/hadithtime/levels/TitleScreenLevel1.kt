package com.hadithtime.levels

import android.annotation.SuppressLint
import android.media.MediaPlayer
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hadithtime.ui.theme.HadithTimeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.HadithViewModel
import com.hadithtime.R
import com.hadithtime.model.LevelAssets
import com.hadithtime.model.levelOneTexts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

@SuppressLint("RememberReturnType")
@Composable
fun TitleScreenLevel1(
    navController: NavController, level: Int, nextIndex: Int,
    viewModel: HadithViewModel = viewModel()
) {
    val context = LocalContext.current
    val readingTitleEnabled by viewModel.readingTitleEnabled.collectAsState()

    val levelOneAssets = listOf(
        LevelAssets(
            R.drawable.level_one_bg,
            R.drawable.level_one_badge,
            R.drawable.new_girl_level1,
            R.drawable.new_bubble_level1,
            R.color.level_one_color,
            R.color.white,
            "levelOneScreen"
        ),
        LevelAssets(
            R.drawable.level_one_bg,
            R.drawable.level_one_badge,
            R.drawable.new_girl_level1,
            R.drawable.new_bubble_level1,
            R.color.level_one_color,
            R.color.white,
            "levelOneScreen"
        ),
        LevelAssets(
            R.drawable.level_one_bg,
            R.drawable.level_one_badge,
            R.drawable.new_girl_level1,
            R.drawable.new_bubble_level1,
            R.color.level_one_color,
            R.color.white,
            "levelOneScreen"
        ),
        LevelAssets(
            R.drawable.level_one_bg,
            R.drawable.level_one_badge,
            R.drawable.new_girl_level1,
            R.drawable.new_bubble_level1,
            R.color.level_one_color,
            R.color.white,
            "levelOneScreen"
        )
    )

    val text = levelOneTexts.getOrElse(nextIndex) { levelOneTexts.last() }
    val assets = levelOneAssets.getOrElse(nextIndex) { levelOneAssets.last() }

    val systemUiController = rememberSystemUiController()
    val statusBarColor = colorResource(id = assets.statusBarColor)
    val navigationBarColor = colorResource(id = assets.navigationBarColor)

    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor)
        systemUiController.setNavigationBarColor(color = navigationBarColor)
    }

    val hadithList by viewModel.filteredDuas.collectAsState()
   // val currentHadith = hadithList.find { it.level == level } ?: return
    val levelOneHadiths = hadithList.filter { it.level == 1 }
    val currentHadith = levelOneHadiths.getOrNull(nextIndex) ?: return

    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor)
        systemUiController.setNavigationBarColor(color = navigationBarColor)
    }

    val englishTitleRes = currentHadith.duaEnglishTitle
    val arabicTitleRes = currentHadith.duaArabicTitle

    val englishPlayer = remember(englishTitleRes) {
        MediaPlayer.create(context, englishTitleRes)
    }

    val arabicPlayer = remember(arabicTitleRes) {
        MediaPlayer.create(context, arabicTitleRes)
    }

    var alreadyPlayed by remember(currentHadith.id) { mutableStateOf(false) }
    LaunchedEffect(readingTitleEnabled, currentHadith.id) {
        if (!alreadyPlayed) {
            alreadyPlayed = true
            if (readingTitleEnabled && englishPlayer != null && arabicPlayer != null) {
                // Play English Title
                suspendCancellableCoroutine<Unit> { cont ->
                    englishPlayer.setOnCompletionListener {
                        cont.resume(Unit) {}
                    }
                    englishPlayer.start()
                }

                // Play Arabic Title
                suspendCancellableCoroutine<Unit> { cont ->
                    arabicPlayer.setOnCompletionListener {
                        cont.resume(Unit) {}
                    }
                    arabicPlayer.start()
                }

                // After both complete
                navController.navigate("levelOneScreen/$nextIndex")
            } else {
                // If readingTitle is off, just wait 2 seconds then navigate
                delay(2000)
                navController.navigate("levelOneScreen/$nextIndex")
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            englishPlayer?.release()
            arabicPlayer?.release()
        }
    }

    // Animations
    val MyEnglishFont = FontFamily(Font(R.font.sandy_kids))
    val MyArabicFont = FontFamily(Font(R.font.tinta_arabic))

    val girlOffsetX = remember { Animatable(-300f) }
    val bubbleAlpha = remember { Animatable(0f) }
    val bubbleOffsetY = remember { Animatable(20f) }
    val bubbleScale = remember { Animatable(0.5f) }

    LaunchedEffect(Unit) {
        girlOffsetX.animateTo(0f, tween(1000, easing = FastOutSlowInEasing))
        delay(300)
        launch { bubbleScale.animateTo(1f, tween(700, easing = FastOutSlowInEasing)) }
        launch { bubbleOffsetY.animateTo(-20f, tween(700, easing = FastOutSlowInEasing)) }
        launch { bubbleAlpha.animateTo(1f, tween(700, easing = FastOutSlowInEasing)) }
    }

    // UI
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
                .padding(top = 46.dp, end = 20.dp)
                .height(40.dp)
                .width(120.dp)
        )

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
                    .padding(bottom = 112.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text.englishText,
                    fontSize = 36.sp,
                    lineHeight = 36.sp,
                    fontFamily = MyEnglishFont,
                    color = colorResource(R.color.bubble_text)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 40.dp, y = (-350).dp + bubbleOffsetY.value.dp)
                .graphicsLayer(scaleX = bubbleScale.value, scaleY = bubbleScale.value)
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
                    .padding(top = 35.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                BoxWithConstraints {
                    val maxWidth = constraints.maxWidth.toFloat()
                    var textSize by remember { mutableStateOf(38.sp) }

                    Text(
                        text = text.arabicText,
                        fontSize = textSize,
                        fontFamily = MyArabicFont,
                        maxLines = 1,
                        softWrap = false,
                        onTextLayout = { result ->
                            if (result.didOverflowWidth) {
                                textSize *= 0.10f
                            }
                        },
                        color = colorResource(R.color.bubble_text),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleScreen1() {
    HadithTimeTheme {
        TitleScreenLevel1(
            navController = rememberNavController(),
            level = 1,
            nextIndex = 0
        )
    }
}
