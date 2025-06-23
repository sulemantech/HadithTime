package com.hadithtime.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hadithtime.SettingScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.hadithtime.levels.LevelOneScreen
import com.hadithtime.HomeScreen
import com.hadithtime.levels.LevelTwoScreen
import com.hadithtime.SplashScreen
import com.hadithtime.levels.LevelFiveScreen
import com.hadithtime.levels.LevelFourScreen
import com.hadithtime.levels.LevelSevenScreen
import com.hadithtime.levels.LevelSixScreen
import com.hadithtime.levels.LevelThreeScreen
import com.hadithtime.levels.TitleScreenLevel1
import com.hadithtime.levels.TitleScreenLevel2
import com.hadithtime.levels.TitleScreenLevel3
import com.hadithtime.levels.TitleScreenLevel4
import com.hadithtime.levels.TitleScreenLevel5
import com.hadithtime.levels.TitleScreenLevel6
import com.hadithtime.levels.TitleScreenLevel7

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {

        composable(
            "splash",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }
        ) {
            SplashScreen(
                onFinished = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("TitleOneScreen") {
            TitleScreenLevel1(navController)
        }
        composable("TitleTwoScreen") {
            TitleScreenLevel2(navController)
        }
        composable("TitleThreeScreen") {
            TitleScreenLevel3(navController)
        }
        composable("TitleFourScreen") {
            TitleScreenLevel4(navController)
        }
        composable("TitleFiveScreen") {
            TitleScreenLevel5(navController)
        }
        composable("TitleSixScreen") {
            TitleScreenLevel6(navController)
        }
        composable("TitleSevenScreen") {
            TitleScreenLevel7(navController)
        }

        composable("LevelOneScreen") {
            LevelOneScreen(
                navController = navController,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("LevelTwoScreen") {
            LevelTwoScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("LevelThreeScreen") {
            LevelThreeScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("LevelFourScreen") {
            LevelFourScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("LevelFiveScreen") {
            LevelFiveScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("LevelSixScreen") {
            LevelSixScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("LevelSevenScreen") {
            LevelSevenScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }

        composable("settings") {
            SettingScreen(
               // onHomeClick = { navController.navigate("home") }
            )
        }
    }
}



