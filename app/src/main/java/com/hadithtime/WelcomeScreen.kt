package com.hadithtime

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun WelcomeScreen(
    navController: NavController,
    onContinueClick: () -> Unit,
    onSwipeLeft: () -> Unit = {}
) {
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
                .padding(top = 150.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_book),
                contentDescription = "Hadith Icon",
                modifier = Modifier.size(135.dp),
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

        // Dot Indicator
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

        // Continue Button
        Button(
            onClick = onContinueClick,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.continue_bg)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 75.dp)
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
    onSkipClick: () -> Unit,
    onBackClick: () -> Unit,
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {}
) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_medium))
    val MyTextFont = FontFamily(Font(R.font.fredoka_regular))
    val MySkipFont = FontFamily(Font(R.font.fredoka_medium))
    var selectedLanguage by remember { mutableStateOf("English") }
    val selectedLanguages = remember { mutableStateListOf<String>() }


    val languages = listOf(
        Triple("English", R.drawable.ic_english, "English"),
        Triple("Arabic", R.drawable.ic_arabic, "العربية"),
        Triple("Urdu", R.drawable.ic_urdu, "اردو"),
        Triple("Hindi", R.drawable.ic_hindi, "हिंदी")
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.welcom_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 45.dp, start = 28.dp, end = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Back",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = MySkipFont,
                modifier = Modifier.clickable { onBackClick() }
            )

            Text(
                text = "Skip",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = MySkipFont,
                modifier = Modifier.clickable { onSkipClick() }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                lineHeight = 26.sp,
                fontFamily = MyTextFont,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column {
                languages.forEach { (name, icon,subtitle) ->
                    val isSelected = name in selectedLanguages

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) Color.White.copy(alpha = 0.1f) else Color.Transparent)
                            .clickable {
                                if (isSelected) selectedLanguages.remove(name)
                                else selectedLanguages.add(name)
                            }
                            .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = name,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        // Wrap Texts in Column
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = name,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                            Text(
                                text = subtitle,
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                        }

                        RadioButton(
                            selected = isSelected,
                            onClick = null,
                            enabled = false,
                            colors = RadioButtonDefaults.colors(
                                disabledSelectedColor =  colorResource(R.color.background_color),
                                disabledUnselectedColor =  colorResource(R.color.background_color),
                            )
                        )
                    }

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
                        .size(if (it == 1) 10.dp else 8.dp)
                        .background(
                            color = if (it == 1) Color.White else Color.White.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                )
            }
        }

        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.continue_bg)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 75.dp)
                .width(240.dp)
                .height(48.dp)
        ) {
            Text(text = "Next", color = Color.White)
        }
        //  Spacer(modifier = Modifier.height(24.dp))
    }

}


@Composable
fun FontSizeAdjustmentScreen(
    fontSize: Float,
    onFontSizeChange: (Float) -> Unit,
    onContinueClick: () -> Unit,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit,
    onBackClick: () -> Unit,
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    viewModel: HadithViewModel = viewModel()

) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_medium))
    val MyTextFont = FontFamily(Font(R.font.fredoka_regular))
    val MySkipFont = FontFamily(Font(R.font.fredoka_medium))
    val MyArabicFont = FontFamily(Font(R.font.al_quran))
    val fontSize by viewModel.fontSize

    Box(modifier = Modifier.fillMaxSize()) {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.welcom_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 45.dp, start = 28.dp, end = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Back",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = MySkipFont,
                modifier = Modifier.clickable { onBackClick() }
            )

            Text(
                text = "Skip",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = MySkipFont,
                modifier = Modifier.clickable { onSkipClick() }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                lineHeight = 26.sp,
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
                        .padding(top = 28.dp, start = 18.dp)
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
                Spacer(modifier = Modifier.height(8.dp))
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

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Slider(
                        value = fontSize,
                        onValueChange = { newSize ->
                            viewModel.updateFontSize(newSize)
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
                        color = colorResource(R.color.white)
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
                        .size(if (it == 2) 10.dp else 8.dp)
                        .background(
                            color = if (it == 2) Color.White else Color.White.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                )
            }
        }

        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.continue_bg)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 75.dp)
                .width(240.dp)
                .height(48.dp)
        ) {
            Text(text = "Next", color = Color.White)
        }
    }
}


@Composable
fun PreferencesScreen(
    isDailyReminderEnabled: Boolean,
    isDarkModeEnabled: Boolean,
    onDailyReminderChange: (Boolean) -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    onGetStartedClick: () -> Unit,
    onNextClick: () -> Unit,
    onContinueClick: () -> Unit,
    onSwipeRight: () -> Unit,
    viewModel: HadithViewModel = viewModel()

) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_medium))
    val MyTextFont = FontFamily(Font(R.font.fredoka_regular))
    val MySkipFont = FontFamily(Font(R.font.fredoka_medium))
    val MyArabicFont = FontFamily(Font(R.font.al_quran))
    val splashfont = FontFamily(Font(R.font.fredoka_semibold))
    val autoNextHadithEnabled by viewModel.autoNextHadithEnabled.collectAsState()

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
                .padding(top = 80.dp)
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
                lineHeight = 26.sp,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp)
                ) {
                    val autoNextHadithEnabled by viewModel.autoNextHadithEnabled.collectAsState()

                    SettingSwitch2(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_next),
                                contentDescription = "Dark Mode Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(end = 4.dp),
                                tint = Color.White
                            )
                        },
                        title = "Auto Next Hadith",
                        textColor = Color.Black,
                        checked = autoNextHadithEnabled,
                        onCheckedChange = { viewModel.setautoNextHadithEnabled(it) }
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp)
                ) {
                    var readingTitleEnabled by remember { mutableStateOf(false) }

                    SettingSwitch2(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_eye),
                                contentDescription = "Dark Mode Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(end = 4.dp),
                                tint = Color.White
                            )
                        },
                        title = "Hadith Reference",
                        textColor = Color.Black,
                        checked = readingTitleEnabled,
                        onCheckedChange = { readingTitleEnabled = it }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp)
                ) {
                    var readingTitleEnabled by remember { mutableStateOf(false) }

                    SettingSwitch2(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_reward),
                                contentDescription = "Dark Mode Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(end = 4.dp),
                                tint = Color.White
                            )
                        },
                        title = "Reward Notification",
                        textColor = Color.Black,
                        checked = readingTitleEnabled,
                        onCheckedChange = { readingTitleEnabled = it }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp)
                ) {
                    val readingTitleEnabled by viewModel.readingTitleEnabled.collectAsState()

                    SettingSwitch2(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_reading),
                                contentDescription = "Dark Mode Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(end = 4.dp),
                                tint = Color.White
                            )
                        },
                        title = "Reading Out Hadith Title",
                        textColor = Color.Black,
                        checked = readingTitleEnabled,
                        onCheckedChange = { viewModel.setReadingTitleEnabled(it) }
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
                .padding(bottom = 75.dp)
                .width(240.dp)
                .height(48.dp)
        ) {
            Text(text = "Get started", color = Color.White)
        }
    }
}

@Composable
fun SettingSwitch2(
    icon: @Composable () -> Unit,
    title: String,
    checked: Boolean,
    textColor: Color = Color.White,
    onCheckedChange: (Boolean) -> Unit,
) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_semibold))
    val MyregularFont = FontFamily(Font(R.font.fredoka_regular))
    val MyArabicFont = FontFamily(Font(R.font.al_quran))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        icon()
        Spacer(Modifier.width(5.dp))
        Text(
            title,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = MyregularFont,
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = colorResource(R.color.background_color),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = colorResource(R.color.filter_color_unchecked)
            )
        )
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
        onContinueClick = {},
        onSwipeRight = {}
    )
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    WelcomeScreen(navController = navController, onContinueClick = {})
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectionScreenPreview() {
    ChooseLanguageScreen(
        selectedLanguage = "English",
        onLanguageSelected = {},
        onNextClick = {},
        onSkipClick = {},
        onBackClick = {},

        )
}

@Preview(showBackground = true)
@Composable
fun FontSizeAdjustmentScreenPreview() {
    FontSizeAdjustmentScreen(
        fontSize = 18f,
        onFontSizeChange = {},
        onContinueClick = {},
        onNextClick = {},
        onSkipClick = {},
        onBackClick = {}
    )
}