package com.hadithtime.components

import android.app.Activity
import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.hadithtime.ChooseLanguageScreen
import com.hadithtime.FontSizeAdjustmentScreen
import com.hadithtime.OnboardingPrefs
import com.hadithtime.PreferencesScreen
import com.hadithtime.WelcomeScreen
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = context as? Activity

    SideEffect {
        activity?.let {
            WindowCompat.setDecorFitsSystemWindows(it.window, false)
            val controller = it.window.insetsController
            controller?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> WelcomeScreen(
                navController = navController,
                onContinueClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                },
                onSwipeLeft = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )

            1 -> ChooseLanguageScreen(
                selectedLanguage = "English",
                onLanguageSelected = {},
                onNextClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(2)
                    }
                },
                onBackClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                onSkipClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(3)
                    }
                },
                onSwipeLeft = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(2)
                    }
                },
                onSwipeRight = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
            )

            2 -> FontSizeAdjustmentScreen(
                fontSize = 24f,
                onFontSizeChange = {},
                onNextClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(3)
                    }
                },
                onBackClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                },
                onSkipClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(3)
                    }
                },
                onContinueClick = {},
                onSwipeLeft = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(3)
                    }
                },
                onSwipeRight = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )

            3 -> PreferencesScreen(
                isDailyReminderEnabled = false,
                isDarkModeEnabled = false,
                onDailyReminderChange = {},
                onDarkModeChange = {},
                onNextClick = {},


                onContinueClick = {
                    coroutineScope.launch {
                        OnboardingPrefs.setOnboardingCompleted(context, value = true)
                        navController.navigate("HadithDashboardScreen") {
                            popUpTo("Onboarding") { inclusive = true }
                        }
                    }
                },
                onGetStartedClick = {
                    coroutineScope.launch {
                        OnboardingPrefs.setOnboardingCompleted(context, value = true)
                        navController.navigate("HadithDashboardScreen") {
                            popUpTo("Onboarding") { inclusive = true }
                        }
                    }
                },
                onSwipeRight = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(2)
                    }
                }
            )
        }
    }
}
