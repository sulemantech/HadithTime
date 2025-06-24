package com.hadithtime.levels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hadithtime.ui.theme.HadithTimeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.R
import com.hadithtime.components.HadithCard
import com.hadithtime.components.PlayerControls
import com.hadithtime.components.TopBar
import com.hadithtime.duas
import com.hadithtime.model.Hadith

@Composable
fun LevelFiveScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onHomeClick: () -> Unit = {}
) {
    var currentIndex by remember { mutableStateOf(0) }
    val systemUiController = rememberSystemUiController()
    val navigationBarColor = colorResource(id = R.color.white)
    val statusBarColor = colorResource(id = R.color.dua1)
    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor)
        systemUiController.setNavigationBarColor(color = navigationBarColor)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
    ) {
        val MyArabicFont = FontFamily(Font(R.font.al_quran))
        val MyEnglishFont = FontFamily(Font(R.font.lato_regular))

        Image(
            painter = painterResource(id = R.drawable.dua5),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            TopBar(
                title = "Pillars of Islam",
                onSettingsClick = onNavigateToSettings,
                onHomeClick = onHomeClick
            )

            Spacer(modifier = Modifier.height(24.dp))
            val levelFiveDuas = remember { duas.filter { it.level == 5 } }

            val currentDua = levelFiveDuas.getOrNull(currentIndex)
            currentDua?.let {
                HadithCard(dua = it)
            }

            Spacer(modifier = Modifier.weight(1f))

        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            val levelFiveDuas = remember { duas.filter { it.level == 5 } }

            PlayerControls(
                navController = navController,
                onNextClick = {
                    if (currentIndex < levelFiveDuas.lastIndex) {
                        currentIndex += 1
                    }
                },
                onPreviousClick = {
                    if (currentIndex > 0) {
                        currentIndex -= 1
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCleanlinessScreen5() {
    HadithTimeTheme {
        val navController = rememberNavController() // ✅ Create a mock NavController
        LevelFiveScreen(
            navController = navController,
            onNavigateToSettings = {},
            onHomeClick = {}
        )
    }
}
