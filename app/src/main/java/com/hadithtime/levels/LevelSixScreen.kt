package com.hadithtime.levels

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hadithtime.ui.theme.HadithTimeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.R
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hadithtime.HadithViewModel
import com.hadithtime.components.HadithCard
import com.hadithtime.components.PlayerControls
import com.hadithtime.components.TopBar
import com.hadithtime.duas

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun LevelSixScreen(
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
    val levelSixDuas = filteredDuas.filter { it.level == 6 }
    val currentDua = levelSixDuas.getOrNull(currentIndex)
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
                    painter = painterResource(id = R.drawable.dua6),
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

                    val fontSize = viewModel.fontSize.value // ← from your state

                    currentDua?.let {
                        HadithCard(
                            dua = it,
                            fontSizeSp = fontSize // ✅ pass this
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            currentDua?.let { dua ->
                PlayerControls(
                    navController = navController,
                    onNextClick = {
                        if (currentIndex < levelSixDuas.lastIndex) {
                            navController.navigate("titleScreenLevel6/6/${currentIndex + 1}")
                        }
                    },
                    onPreviousClick = {
                        if (currentIndex > 0) {
                            navController.navigate("titleScreenLevel6/6/${currentIndex - 1}")
                        }
                    },
                    level = 6,
                    dua = dua,                // ✅ Pass the current dua here
                    viewModel = viewModel     // ✅ Pass the viewmodel here
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true)
@Composable
fun PreviewCleanlinessScreen6() {
    HadithTimeTheme {
        val navController = rememberNavController() // ✅ Create a mock NavController
        LevelSixScreen(
            navController = navController,
            onNavigateToSettings = {},
            onHomeClick = {}
        )
    }
}
