package com.hadithtime

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.components.BottomNavigationBar

@SuppressLint("UnrememberedMutableState")
@Composable
fun FavoriteScreen(
    navController: NavController,
) {
    val systemUiController = rememberSystemUiController()

    val scrollState = rememberLazyListState()
    val headerMaxOffset = with(LocalDensity.current) { 100.dp.toPx() }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    // Calculate header offset based on scroll
    val headerOffset by derivedStateOf {
        val scrollY = if (scrollState.firstVisibleItemIndex == 0) {
            scrollState.firstVisibleItemScrollOffset.toFloat()
        } else {
            headerMaxOffset
        }
        -scrollY.coerceIn(0f, headerMaxOffset)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // ⬅️ ensures content respects bottom bar
        ) {

        }
    }
}

