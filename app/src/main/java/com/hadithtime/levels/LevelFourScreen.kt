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
fun LevelFourScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onHomeClick: () -> Unit = {},
    startIndex: Int = 0

) {
    var currentIndex by remember { mutableIntStateOf(startIndex) }
    val systemUiController = rememberSystemUiController()
    val navigationBarColor = colorResource(id = R.color.white)
    val statusBarColor = colorResource(id = R.color.level_title_four_color)

    val levelFourDuas = remember { duas.filter { it.level == 4 } }
    val currentDua = levelFourDuas.getOrNull(currentIndex)

    BackHandler {
        navController.navigate("HadithDashboardScreen") {
            popUpTo("HadithDashboardScreen") { inclusive = true }
            launchSingleTop = true
        }
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
                    painter = painterResource(id = R.drawable.dua4),
                    contentDescription = "Background",
                    contentScale = ContentScale.FillBounds,
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
                            onHomeClick = {
                                navController.navigate("HadithDashboardScreen") {
                                    popUpTo("HadithDashboardScreen") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
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
                    if (currentIndex < levelFourDuas.lastIndex) {
                        navController.navigate("titleScreenLevel4/4/${currentIndex + 1}")
                    }
                },
                onPreviousClick = {
                    if (currentIndex > 0) {
                        navController.navigate("titleScreenLevel4/4/${currentIndex - 1}")
                    }
                },
                level = 4
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCleanlinessScreen4() {
    HadithTimeTheme {
        val navController = rememberNavController()
        LevelFourScreen(
            navController = navController,
            onNavigateToSettings = {},
            onHomeClick = {}
        )
    }
}
