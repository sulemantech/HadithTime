package com.hadithtime

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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

@Composable
fun WelcomeScreen(navController: NavController, onContinueClick: () -> Unit) {
    val splashfont = FontFamily(Font(R.font.fredoka_semibold))
    val MyCountFont = FontFamily(Font(R.font.fredoka_regular))

    Box(modifier = Modifier.fillMaxSize()) {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.welcom_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        // Top Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 200.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_book),
                contentDescription = "Hadith Icon",
                modifier = Modifier.size(100.dp),
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Hadith Time",
                fontSize = 32.sp,
                fontFamily = splashfont,
                color = Color.White
            )

            Text(
                text = "Listen, Repeat, and Shine with Sunnah!",
                fontSize = 18.sp,
                color = Color.White,
                fontFamily = MyCountFont,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 150.dp)
        ) {
            repeat(4) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (it == 0) 10.dp else 8.dp)
                        .background(
                            color = if (it == 0) Color.White else Color.White.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                )
            }
        }

        Button(
            onClick = onContinueClick,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.continue_bg)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .width(240.dp)
                .height(48.dp)
        ) {
            Text(text = "Continue", color = Color.White)
        }
    }
}

@Composable
fun ChooseLanguageScreen(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_medium))
    val MyTextFont = FontFamily(Font(R.font.fredoka_regular))
    val MySkipFont = FontFamily(Font(R.font.fredoka_medium))

    val languages = listOf(
        "English" to R.drawable.ic_english,
        "Arabic" to R.drawable.ic_arabic,
        "Urdu" to R.drawable.ic_urdu,
        "Hindi" to R.drawable.ic_hindi
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.welcom_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Skip button
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    text = "Skip",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = MySkipFont,
                    modifier = Modifier
                        .clickable { onSkipClick() }
                        .padding(top = 38.dp, end = 28.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Icon
                Image(
                    painter = painterResource(id = R.drawable.onboarding_logo2),
                    contentDescription = "Hadith Icon",
                    modifier = Modifier.size(100.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Choose Your Language",
                    fontSize = 26.sp,
                    fontFamily = MyCountFont,
                    color = Color.White
                )

                Text(
                    text = "Pick your language to start your Hadith journey!",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = MyTextFont,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Language List
                languages.forEach { (name, icon) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selectedLanguage == name) Color.White.copy(alpha = 0.1f) else Color.Transparent)
                            .clickable { onLanguageSelected(name) }
                            .padding(start = 10.dp, top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = name,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = name,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        RadioButton(
                            selected = selectedLanguage == name,
                            onClick = { onLanguageSelected(name) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.White,
                                unselectedColor = Color.White
                            )
                        )
                    }
                }
            }

            // Bottom Section
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Page Indicator
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                ) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (it == 1) 10.dp else 8.dp)
                                .background(
                                    color = if (it == 1) Color.White else Color.White.copy(alpha = 0.5f),
                                    shape = CircleShape
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Next Button
                Button(
                    onClick = { onNextClick() },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.continue_bg)),
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .width(240.dp)
                        .height(48.dp)
                ) {
                    Text(text = "Next", color = colorResource(R.color.white))
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectionScreenPreview() {
    ChooseLanguageScreen(
        selectedLanguage = "English",
        onLanguageSelected = {},
        onNextClick = {},
        onSkipClick = {}
    )
}

@Composable
fun FontSizeAdjustmentScreen(
    fontSize: Float,
    onFontSizeChange: (Float) -> Unit,
    onContinueClick: () -> Unit,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit

) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_medium))
    val MyTextFont = FontFamily(Font(R.font.fredoka_regular))
    val MySkipFont = FontFamily(Font(R.font.fredoka_medium))
    val MyArabicFont = FontFamily(Font(R.font.al_quran))
    Box(modifier = Modifier.fillMaxSize()) {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.welcom_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Skip button
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    text = "Skip",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = MySkipFont,
                    modifier = Modifier
                        .clickable { onSkipClick() }
                        .padding(top = 38.dp, end = 28.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Icon
                Image(
                    painter = painterResource(id = R.drawable.onboarding3),
                    contentDescription = "Hadith Icon",
                    modifier = Modifier.size(100.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Choose Font Size",
                    fontSize = 26.sp,
                    fontFamily = MyCountFont,
                    color = Color.White
                )

                Text(
                    text = "Select a font size that’s perfect for your child’s reading comfort.",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = MyTextFont,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Top Row with icon, "Font Size" text, and value on the right
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp, start = 18.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.font_size),
                            contentDescription = "Font Size Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(start = 8.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Font Size",
                            fontSize = 16.sp,
                            color = colorResource(R.color.white),
                            fontFamily = MyTextFont,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 20.dp)
                        )
                    }

                    // The Arabic text centered
                    Text(
                        text = "ٱلْـحَـمْـدُ للهِ",
                        fontSize = fontSize.sp,
                        color = colorResource(R.color.white),
                        fontFamily = MyArabicFont,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    )
                    var arabicFontSize by remember { mutableStateOf(24.sp) }

                    // Slider with value indicator at the end
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp)
                    ) {
                        Slider(
                            value = fontSize,
                            onValueChange = { newSize ->
                                //  viewModel.updateFontSize(newSize)
                            },
                            valueRange = 12f..100f,
                            modifier = Modifier.weight(1f),
                            colors = SliderDefaults.colors(
                                thumbColor = colorResource(R.color.filter_color_bg),
                                activeTrackColor = colorResource(R.color.filter_color_bg),
                                inactiveTrackColor = colorResource(R.color.filter_color_bg)
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = fontSize.toInt().toString(),
                            color = colorResource(R.color.white),
                        )
                    }
                }

            }

            // Bottom Section
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Page Indicator
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                ) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (it == 2) 10.dp else 8.dp)
                                .background(
                                    color = if (it == 2) Color.White else Color.White.copy(alpha = 0.5f),
                                    shape = CircleShape
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Next Button
                Button(
                    onClick = { onNextClick() },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.continue_bg)),
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .width(240.dp)
                        .height(48.dp)
                ) {
                    Text(text = "Next", color = colorResource(R.color.white))
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FontSizeAdjustmentScreenPreview() {
    FontSizeAdjustmentScreen(
        fontSize = 18f,
        onFontSizeChange = {},
        onContinueClick = {},
        onNextClick = {},
        onSkipClick = {}
    )
}

@Composable
fun PreferencesScreen(
    isDailyReminderEnabled: Boolean,
    isDarkModeEnabled: Boolean,
    onDailyReminderChange: (Boolean) -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    onGetStartedClick: () -> Unit,
    onNextClick: () -> Unit,
    onContinueClick: () -> Unit
) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_medium))
    val MyTextFont = FontFamily(Font(R.font.fredoka_regular))
    val MySkipFont = FontFamily(Font(R.font.fredoka_medium))
    val MyArabicFont = FontFamily(Font(R.font.al_quran))
    val splashfont = FontFamily(Font(R.font.fredoka_semibold))

    Box(modifier = Modifier.fillMaxSize()) {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.welcom_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        // Top Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 200.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.onboarding4),
                contentDescription = "Hadith Icon",
                modifier = Modifier.size(100.dp),
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Set Your Preferences",
                fontSize = 32.sp,
                fontFamily = splashfont,
                color = Color.White
            )

            Text(
                text = "Enable smart features to make Hadith learning smoother and more fun.",
                fontSize = 18.sp,
                color = Color.White,
                fontFamily = MyCountFont,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Dark Mode Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),
                        tint = Color.White
                    )

                    Text(
                        text = "Auto Next Hadith",
                        modifier = Modifier.weight(1f),
                        color = colorResource(R.color.white),
                        fontFamily = MyTextFont,
                        fontSize = 16.sp
                    )

                    Switch(
                        checked = isDarkModeEnabled,
                        onCheckedChange = onDarkModeChange
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_eye),
                        contentDescription = "Dark Mode Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),
                        tint = Color.White
                    )

                    Text(
                        text = "Hadith Reference",
                        modifier = Modifier.weight(1f),
                        color = colorResource(R.color.white),
                        fontFamily = MyTextFont,
                        fontSize = 16.sp
                    )

                    Switch(
                        checked = isDarkModeEnabled,
                        onCheckedChange = onDarkModeChange
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_reward),
                        contentDescription = "Dark Mode Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),
                        tint = Color.White
                    )

                    Text(
                        text = "Rewards Notification",
                        modifier = Modifier.weight(1f),
                        color = colorResource(R.color.white),
                        fontFamily = MyTextFont,
                        fontSize = 16.sp
                    )

                    Switch(
                        checked = isDarkModeEnabled,
                        onCheckedChange = onDarkModeChange
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_reading),
                        contentDescription = "Dark Mode Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),
                        tint = Color.White
                    )

                    Text(
                        text = "Reading Out Hadith Title",
                        modifier = Modifier.weight(1f),
                        color = colorResource(R.color.white),
                        fontFamily = MyTextFont,
                        fontSize = 16.sp
                    )

                    Switch(
                        checked = isDarkModeEnabled,
                        onCheckedChange = onDarkModeChange
                    )
                }

            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 150.dp)
        ) {
            repeat(4) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (it == 3) 10.dp else 8.dp)
                        .background(
                            color = if (it == 3) Color.White else Color.White.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                )
            }
        }

        Button(
            onClick = onContinueClick,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.continue_bg)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 55.dp)
                .width(240.dp)
                .height(48.dp)
        ) {
            Text(text = "Get started", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreferencesScreenPreview() {
    PreferencesScreen(
        isDailyReminderEnabled = true,
        isDarkModeEnabled = false,
        onDailyReminderChange = {},
        onDarkModeChange = {},
        onGetStartedClick = {},
        onNextClick = {},
        onContinueClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    WelcomeScreen(navController = navController, onContinueClick = {})
}
