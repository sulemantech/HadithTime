package com.hadithtime.levels

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hadithtime.ui.theme.HadithTimeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.HadithViewModel
import com.hadithtime.R
import com.hadithtime.components.FontSizeManager
import com.hadithtime.components.HadithCard
import com.hadithtime.components.PlayerControls
import com.hadithtime.components.TopBar
import com.hadithtime.duas

@Composable
fun LevelSevenScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onHomeClick: () -> Unit = {},
    startIndex: Int = 0
) {
    var currentIndex by remember { mutableIntStateOf(startIndex) }
    val systemUiController = rememberSystemUiController()
    val navigationBarColor = colorResource(id = R.color.white)
    val statusBarColor = colorResource(id = R.color.level_title_five_color)
    val viewModel: HadithViewModel = viewModel()
    var arabicFontSize by remember { mutableStateOf(24.sp) }

    val filteredDuas by viewModel.filteredDuas.collectAsState()
    val levelSevenDuas = filteredDuas.filter { it.level == 7 }
    val currentDua = levelSevenDuas.getOrNull(currentIndex)
    val context = LocalContext.current
    val fontSize = viewModel.fontSize.value
    val isEnglish by FontSizeManager.getLanguagePreference(context).collectAsState(initial = true)

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(currentIndex) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount > 0) {
                        if (currentIndex > 0) {
                            navController.navigate("titleScreenLevel7/7/${currentIndex - 1}")
                        } else {
                            val previousLevel = 6
                            val previousLevelDuas = filteredDuas.filter { it.level == previousLevel }
                            if (previousLevelDuas.isNotEmpty()) {
                                navController.navigate("titleScreenLevel$previousLevel/$previousLevel/${previousLevelDuas.lastIndex}")
                            } else {
                                Toast.makeText(context, "No previous Duas!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        if (currentIndex < levelSevenDuas.lastIndex) {
                            navController.navigate("titleScreenLevel7/7/${currentIndex + 1}")
                        } else {
                            Toast.makeText(context, "You've reached the final Dua!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                Image(
                    painter = painterResource(id = R.drawable.dua7),
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
                        HadithCard(
                            dua = it,
                            fontSizeSp = fontSize,
                            isEnglish = isEnglish
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            currentDua?.let { dua ->
                PlayerControls(
                    navController = navController,
                    onNextClick = {
                        if (currentIndex < levelSevenDuas.lastIndex) {
                            navController.navigate("titleScreenLevel7/7/${currentIndex + 1}")
                        } else {
                            Toast.makeText(context, "You've reached the final Dua!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onPreviousClick = {
                        if (currentIndex > 0) {
                            navController.navigate("titleScreenLevel7/7/${currentIndex - 1}")
                        } else {
                            val previousLevel = 6
                            val previousLevelDuas = filteredDuas.filter { it.level == previousLevel }
                            if (previousLevelDuas.isNotEmpty()) {
                                navController.navigate("titleScreenLevel$previousLevel/$previousLevel/${previousLevelDuas.lastIndex}")
                            } else {
                                Toast.makeText(context, "No previous Duas!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    level = 7,
                    dua = dua,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCleanlinessScreen7() {
    HadithTimeTheme {
        val navController = rememberNavController() // ✅ Create a mock NavController
        LevelSevenScreen(
            navController = navController,
            onNavigateToSettings = {},
            onHomeClick = {}
        )
    }
}
