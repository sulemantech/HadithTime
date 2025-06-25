package com.hadithtime.levels

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
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
import com.hadithtime.model.LevelAssets
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TitleScreenLevel1(navController: NavController, level: Int, nextIndex: Int) {
    val levelOneAssets = listOf(
        LevelAssets(
            background = R.drawable.level_one_bg,
            badge = R.drawable.level_one_badge,
            girl = R.drawable.title_level1_girl1,
            bubble = R.drawable.title_level1_bubble1,
            statusBarColor = R.color.dua1,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelOneScreen"
        ),
        LevelAssets(
            background = R.drawable.level_one_bg,
            badge = R.drawable.level_one_badge,
            girl = R.drawable.title_level1_girl2,
            bubble = R.drawable.title_level1_bubble2,
            statusBarColor = R.color.dua1,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelOneScreen"
        ),
        LevelAssets(
            background = R.drawable.level_one_bg,
            badge = R.drawable.level_one_badge,
            girl = R.drawable.title_level1_girl3,
            bubble = R.drawable.title_level1_bubble3,
            statusBarColor = R.color.dua1,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelOneScreen"
        ),
        LevelAssets(
            background = R.drawable.level_one_bg,
            badge = R.drawable.level_one_badge,
            girl = R.drawable.title_level1_girl4,
            bubble = R.drawable.title_level1_bubble4,
            statusBarColor = R.color.dua1,
            navigationBarColor = R.color.dua1_title,
            nextRoute = "levelOneScreen"
        )
    )
    val assets = levelOneAssets.getOrElse(nextIndex) {
        levelOneAssets.last() // fallback if index is out of range
    }

    val systemUiController = rememberSystemUiController()
    val statusBarColor = colorResource(id = assets.statusBarColor)
    val navigationBarColor = colorResource(id = assets.navigationBarColor)

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

        Image(
            painter = painterResource(id = assets.girl),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp)
                .offset(x = girlOffsetX.value.dp)
                .height(400.dp)
                .width(224.dp)
        )

        Image(
            painter = painterResource(id = assets.bubble),
            contentDescription = null,
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
        )
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
