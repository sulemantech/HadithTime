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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TitleScreenLevel2(navController: NavController) {
    val systemUiController = rememberSystemUiController()
    val navigationBarColor = colorResource(id = R.color.dua1_title)
    val statusBarColor = colorResource(id = R.color.dua1)
    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor)
        systemUiController.setNavigationBarColor(color = navigationBarColor)
    }
    // Animations
    val girlOffsetX = remember { Animatable(-300f) }
    val bubbleOffsetY = remember { Animatable(-100f) }
    val bubbleAlpha = remember { Animatable(0f) }
    val bubbleOffsetX = remember { Animatable(-200f) } // Start from left

    LaunchedEffect(Unit) {
        girlOffsetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )

        delay(300)

        launch {
            bubbleOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
            )
        }

        launch {
            bubbleAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 700)
            )
        }

        delay(1000)
        navController.navigate("LevelTwoScreen") {
            popUpTo("TitleTwoScreen") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Background
        Image(
            painter = painterResource(id = R.drawable.title_bg1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.level_two_badge),
            contentDescription = "Badge",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 26.dp, end = 16.dp)
                .height(40.dp)
                .width(120.dp)
        )
        // Girl Image (bottom-left)
        Image(
            painter = painterResource(id = R.drawable.girl_image),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(400.dp)
                .width(224.dp)
                .padding(start = 20.dp)
                .offset(x = girlOffsetX.value.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 25.dp + bubbleOffsetX.value.dp, y = 290.dp)
                .alpha(bubbleAlpha.value),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.bubble_image),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .width(300.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleScreen2() {
    HadithTimeTheme {
        TitleScreenLevel2(navController = rememberNavController())
    }
}
