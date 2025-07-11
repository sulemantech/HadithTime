package com.hadithtime

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun SplashScreen(onFinished: (String) -> Unit) {
    val context = LocalContext.current
    val view = LocalView.current

    LaunchedEffect(Unit) {
        val window = (context as? Activity)?.window ?: return@LaunchedEffect
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, view)
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        controller.hide(WindowInsetsCompat.Type.navigationBars())
    }

    val systemUiController = rememberSystemUiController()
    val navigationBarColor = colorResource(id = R.color.white)
    val statusBarColor = colorResource(id = R.color.white)

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor.copy(alpha = 0f))
        systemUiController.setNavigationBarColor(navigationBarColor.copy(alpha = 0f))
    }

    val splashfont = FontFamily(Font(R.font.fredoka_semibold))
    val MyCountFont = FontFamily(Font(R.font.fredoka_regular))

    val onboardingCompleted by produceState<Boolean?>(initialValue = null) {
        value = OnboardingPrefs.isOnboardingCompleted(context)
    }

    LaunchedEffect(onboardingCompleted) {
        if (onboardingCompleted != null) {
            delay(2000)
            val route = if (onboardingCompleted == true) "HadithDashboardScreen" else "Onboarding"
            onFinished(route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Image(
            painter = painterResource(id = R.drawable.onboarding1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp), // manually push logo below status bar if needed
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.onboarding_logo),
                contentDescription = "Hadith Icon",
                modifier = Modifier.size(118.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hadith Time",
                fontSize = 48.sp,
                fontFamily = splashfont,
                color = Color.White
            )

            Text(
                text = "Listen, Repeat, and Shine\n with Sunnah!",
                fontSize = 24.sp,
                color = Color.White,
                fontFamily = MyCountFont,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

object OnboardingPrefs {
    private val Context.dataStore by preferencesDataStore("onboarding_prefs")
    private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

    suspend fun setOnboardingCompleted(context: Context, value: Boolean) {
        context.dataStore.edit { it[ONBOARDING_COMPLETED] = value }
    }

    suspend fun isOnboardingCompleted(context: Context): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[ONBOARDING_COMPLETED] ?: false
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onFinished = {})
}