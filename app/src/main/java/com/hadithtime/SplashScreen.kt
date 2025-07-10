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

@Composable
fun SplashScreen(onFinished: (String) -> Unit) {
    val systemUiController = rememberSystemUiController()

    val NavigationBarColor = colorResource(id = R.color.white)
    val statusBarColor = colorResource(id = R.color.white)
    val splashfont = FontFamily(Font(R.font.fredoka_semibold))
    val MyCountFont = FontFamily(Font(R.font.fredoka_regular))
    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor)
        systemUiController.setNavigationBarColor(color = NavigationBarColor)
    }
    val context = LocalContext.current

    val onboardingCompleted by produceState<Boolean?>(initialValue = null) {
        value = OnboardingPrefs.isOnboardingCompleted(context)
    }

    LaunchedEffect(onboardingCompleted) {
        if (onboardingCompleted != null) {
            delay(2000)
            val route = if (onboardingCompleted == true) "HadithDashboardScreen" else "welcome"
            onFinished(route)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.onboarding1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onboarding_logo),
                    contentDescription = "Hadith Icon",
                    modifier = Modifier.size(118.dp),
                )

                Spacer(modifier = Modifier.height(4.dp))

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

                //  Spacer(modifier = Modifier.height(40.dp))
            }
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

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onFinished = {})
}