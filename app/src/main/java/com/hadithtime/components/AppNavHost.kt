package com.hadithtime.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hadithtime.SettingScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.hadithtime.HomeScreen
import com.hadithtime.SplashScreen

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

        composable("cleanliness") {
            CleanlinessScreen(
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



