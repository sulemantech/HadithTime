package com.hadithtime.components

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.hadithtime.ChooseLanguageScreen
import com.hadithtime.FavoriteScreen
import com.hadithtime.FontSizeAdjustmentScreen
import com.hadithtime.LearningTrackerScreen
import com.hadithtime.OnboardingPrefs
import com.hadithtime.PreferencesScreen
import com.hadithtime.SettingScreen
import com.hadithtime.levels.LevelOneScreen
import com.hadithtime.levels.LevelTwoScreen
import com.hadithtime.SplashScreen
import com.hadithtime.WelcomeScreen
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
import com.hadithtime.model.HadithDataProvider.levels
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen { route ->
                navController.navigate(route) {
                    popUpTo("splash") { inclusive = true }
                    launchSingleTop = true
                }
            }
        }

        composable("welcome") {
            WelcomeScreen(navController = navController) {
                navController.navigate("language")
            }
        }

        composable("language") {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            ChooseLanguageScreen(
                selectedLanguage = "English",
                onLanguageSelected = {},
                onNextClick = { navController.navigate("font") },
                onSkipClick = {
                    scope.launch {
                        OnboardingPrefs.setOnboardingCompleted(context, true)
                        navController.navigate("HadithDashboardScreen") {
                            popUpTo("splash") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }

        composable("font") {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            FontSizeAdjustmentScreen(
                fontSize = 18f,
                onFontSizeChange = {},
                onNextClick = { navController.navigate("preferences") },
                onSkipClick = {
                    scope.launch {
                        OnboardingPrefs.setOnboardingCompleted(context, true)
                        navController.navigate("HadithDashboardScreen") {
                            popUpTo("splash") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                onContinueClick = {}
            )
        }

        composable("preferences") {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            PreferencesScreen(
                isDailyReminderEnabled = true,
                isDarkModeEnabled = false,
                onDailyReminderChange = {},
                onDarkModeChange = {},
                onGetStartedClick = {},
                onNextClick = {
                    scope.launch {
                        OnboardingPrefs.setOnboardingCompleted(context, true)
                        navController.navigate("HadithDashboardScreen") {
                            popUpTo("splash") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                onContinueClick = {
                    scope.launch {
                        OnboardingPrefs.setOnboardingCompleted(context, true)
                        navController.navigate("HadithDashboardScreen") {
                            popUpTo("splash") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
        composable("HadithDashboardScreen") {
            HadithDashboardScreen(navController, levels)
        }

        composable("LearningTrackerScreen") {
            LearningTrackerScreen(navController)
        }

        composable("FavoriteScreen") {
            FavoriteScreen(navController)
        }

        composable("settings") {
            SettingScreen(navController)
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

        composable("titleScreenLevel4/{level}/{nextIndex}") { backStackEntry ->
            val level = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 1
            val index = backStackEntry.arguments?.getString("nextIndex")?.toIntOrNull() ?: 0
            TitleScreenLevel4(navController, level = level, nextIndex = index)
        }

        composable("titleScreenLevel5/{level}/{nextIndex}") { backStackEntry ->
            val level = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 1
            val index = backStackEntry.arguments?.getString("nextIndex")?.toIntOrNull() ?: 0
            TitleScreenLevel5(navController, level = level, nextIndex = index)
        }

        composable("titleScreenLevel6/{level}/{nextIndex}") { backStackEntry ->
            val level = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 1
            val index = backStackEntry.arguments?.getString("nextIndex")?.toIntOrNull() ?: 0
            TitleScreenLevel6(navController, level = level, nextIndex = index)
        }

        composable("titleScreenLevel7/{level}/{nextIndex}") { backStackEntry ->
            val level = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 1
            val index = backStackEntry.arguments?.getString("nextIndex")?.toIntOrNull() ?: 0
            TitleScreenLevel7(navController, level = level, nextIndex = index)
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
        composable("levelFourScreen/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            LevelFourScreen(
                navController = navController,
                startIndex = index,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("levelFiveScreen/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            LevelFiveScreen(
                navController = navController,
                startIndex = index,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("levelSixScreen/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            LevelSixScreen(
                navController = navController,
                startIndex = index,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }
        composable("levelSevenScreen/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            LevelSevenScreen(
                navController = navController,
                startIndex = index,
                onNavigateToSettings = { navController.navigate("settings") },
                onHomeClick = { navController.navigate("home") }
            )
        }


    }
}




