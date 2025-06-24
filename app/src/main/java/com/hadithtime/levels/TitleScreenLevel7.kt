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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TitleScreenLevel7(navController: NavController) {
    val systemUiController = rememberSystemUiController()
    val navigationBarColor = colorResource(id = R.color.level_title_four_bottom)
    val statusBarColor = colorResource(id = R.color.level_title_four_color)
    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor)
        systemUiController.setNavigationBarColor(color = navigationBarColor)
    }
    // Animations
    // Animations
    val girlOffsetX = remember { Animatable(-300f) }

    val bubbleAlpha = remember { Animatable(0f) }
    val bubbleOffsetY = remember { Animatable(20f) } // Start slightly down
    val bubbleScale = remember { Animatable(0.5f) }  // Start small

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
                targetValue = -20f, // Float upward
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
        navController.navigate("LevelSevenScreen") {
           // popUpTo("TitleSevenScreen") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.level_seven_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Top Badge
        Image(
            painter = painterResource(id = R.drawable.level_seven_badge),
            contentDescription = "Badge",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 26.dp, end = 16.dp)
                .height(40.dp)
                .width(120.dp)
        )

        // Girl Image (bottom-left)
        Image(
            painter = painterResource(id = R.drawable.level_seven_girl),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp)
                .offset(x = girlOffsetX.value.dp)
                .height(400.dp)
                .width(224.dp)
        )

        // Bubble Animation (appears from girl’s mouth)
        Image(
            painter = painterResource(id = R.drawable.level_seven_bubble),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(
                    x = 40.dp, // adjust X as per girl's mouth
                    y = (-350).dp + bubbleOffsetY.value.dp // adjust Y upward
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
fun PreviewTitleScreen7() {
    HadithTimeTheme {
        TitleScreenLevel7(navController = rememberNavController())
    }
}
