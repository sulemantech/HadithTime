package com.hadithtime.components

import android.os.Build
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.S)
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
        composable("titleScreenLevel1/{level}/{nextIndex}") { backStackEntry ->
            val level = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 1
            val index = backStackEntry.arguments?.getString("nextIndex")?.toIntOrNull() ?: 0
            TitleScreenLevel1(navController, level = level, nextIndex = index)
        }

        composable("titleScreenLevel2/{level}/{nextIndex}") { backStackEntry ->
            val level = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 1
            val index = backStackEntry.arguments?.getString("nextIndex")?.toIntOrNull() ?: 0
            TitleScreenLevel2(navController, level = level, nextIndex = index)
        }

        composable("titleScreenLevel3/{level}/{nextIndex}") { backStackEntry ->
            val level = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 1
            val index = backStackEntry.arguments?.getString("nextIndex")?.toIntOrNull() ?: 0
            TitleScreenLevel3(navController, level = level, nextIndex = index)
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

        composable("levelOneScreen/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            LevelOneScreen(
                navController = navController,
                startIndex = index,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }

        composable("levelTwoScreen/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            LevelTwoScreen(
                navController = navController,
                startIndex = index,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("levelThreeScreen/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            LevelThreeScreen(
                navController = navController,
                startIndex = index,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("LevelFourScreen") {
            LevelFourScreen(
                navController = navController,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("LevelFiveScreen") {
            LevelFiveScreen(
                navController = navController,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("LevelSixScreen") {
            LevelSixScreen(
                navController = navController,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("LevelSevenScreen") {
            LevelSevenScreen(
                navController = navController,
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



