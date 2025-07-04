package com.hadithtime

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hadithtime.components.BottomNavigationBar

@SuppressLint("UnrememberedMutableState")
@Composable
fun SettingScreen(navController: NavController) {
    var selectedAppLanguage by remember { mutableStateOf("English") }
    var selectedTranslation by remember { mutableStateOf("English") }
    var selectedGender by remember { mutableStateOf("Male") }
    var selectedVoice by remember { mutableStateOf("Male") }
    var selectedFont by remember { mutableStateOf("Al Majeed Quran") }
    var fontSize by remember { mutableStateOf(16f) }
    var hadithReferenceEnabled by remember { mutableStateOf(false) }
    var autoNextHadithEnabled by remember { mutableStateOf(false) }
    var readingTitleEnabled by remember { mutableStateOf(true) }
    var rewardsEnabled by remember { mutableStateOf(true) }
    var showTranslationDialog by remember { mutableStateOf(false) }
    val MyCountFont = FontFamily(Font(R.font.fredoka_semibold))
    val MyregularFont = FontFamily(Font(R.font.fredoka_regular))
    val MyArabicFont = FontFamily(Font(R.font.al_quran))

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            item {
                Text(
                    text = "Settings",
                    fontSize = 20.sp,
                    fontFamily = MyCountFont,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
            }

            item {
                Text(
                    "Language",
                    fontSize = 18.sp,
                    fontFamily = MyCountFont,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // App Language Options

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    // English option
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_app_lang),
                            contentDescription = "English",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "App Language",
                            fontSize = 16.sp,
                            fontFamily = MyregularFont,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_english),
                            contentDescription = "English",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "English", fontSize = 16.sp,
                            fontFamily = MyregularFont, modifier = Modifier.weight(1f)
                        )
                        RadioButton(
                            selected = selectedAppLanguage == "English",
                            onClick = { selectedAppLanguage = "English" }
                        )
                    }

                    // Urdu option
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_urdu),
                            contentDescription = "Urdu",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Urdu", fontSize = 16.sp,
                            fontFamily = MyregularFont,
                            modifier = Modifier.weight(1f)
                        )
                        RadioButton(
                            selected = selectedAppLanguage == "Urdu",
                            onClick = { selectedAppLanguage = "Urdu" }
                        )
                    }
                }

                // Dua Translation Row with dropdown
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clickable { showTranslationDialog = true }

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_translation),
                        contentDescription = "Dua Translation",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Dua Translation",
                        fontSize = 16.sp,
                        fontFamily = MyregularFont,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Box(
                        modifier = Modifier
                            .background(color = colorResource(R.color.background_color), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = selectedTranslation,
                                fontSize = 14.sp,
                                fontFamily = MyregularFont,
                                color = Color.Black
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown",
                                tint = Color.Black
                            )
                        }
                    }
                }

                // Translation Options Dialog
                if (showTranslationDialog) {
                    AlertDialog(
                        onDismissRequest = { showTranslationDialog = false },
                        title = { Text("Select Translation Language") },
                        buttons = {
                            Column(
                                modifier = Modifier
                                    .padding(all = 16.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    "English",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedTranslation = "English"
                                            showTranslationDialog = false
                                        }
                                        .padding(vertical = 8.dp)
                                )
                                Text(
                                    "Urdu",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedTranslation = "Urdu"
                                            showTranslationDialog = false
                                        }
                                        .padding(vertical = 8.dp)
                                )
                            }
                        }
                    )
                }
                Spacer(Modifier.height(16.dp))
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
            }

            item {
                Text("Font Settings", fontSize = 18.sp, fontFamily = MyCountFont)
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.font),
                        contentDescription = "Dua Translation",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Al Majeed Quran", fontSize = 16.sp,
                        fontFamily = MyregularFont, modifier = Modifier.weight(1f)
                    )
                    RadioButton(
                        selected = selectedFont == "Al Majeed Quran",
                        onClick = { selectedFont = "Al Majeed Quran" }
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.font),
                        contentDescription = "Dua Translation",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Arabic font 2", fontSize = 16.sp,
                        fontFamily = MyregularFont, modifier = Modifier.weight(1f)
                    )
                    RadioButton(
                        selected = selectedFont == "Arabic font 2",
                        onClick = { selectedFont = "Arabic font 2" }
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Top Row with icon, "Font Size" text, and value on the right
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.font_size),
                            contentDescription = "Font Size Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Font Size",
                            fontSize = 16.sp,
                            fontFamily = MyregularFont,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 20.dp)
                        )
                    }

                    // The Arabic text centered
                    Text(
                        text = "ٱلْـحَـمْـدُ للهِ", // Replace with your actual text
                        fontSize = fontSize.sp,
                        fontFamily = MyArabicFont,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    )

                    // Slider with value indicator at the end
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Slider(
                            value = fontSize,
                            onValueChange = { fontSize = it },
                            valueRange = 12f..30f,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = fontSize.toInt().toString())
                    }
                }
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
                Spacer(Modifier.height(16.dp))
            }

            item {
                Text("Display", fontSize = 18.sp, fontFamily = MyCountFont)
                Spacer(Modifier.height(8.dp))

                SettingSwitch(
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = "Hadith Reference Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    title = "Hadith Reference",
                    checked = hadithReferenceEnabled,
                    onCheckedChange = { hadithReferenceEnabled = it }
                )

                SettingSwitch(
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = "Hadith Reference Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    title = "Auto Next Hadith",
                    checked = autoNextHadithEnabled,
                    onCheckedChange = { autoNextHadithEnabled = it }
                )
                Spacer(Modifier.height(16.dp))
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
            }

            item {
                Text("Audio", fontSize = 18.sp, fontFamily = MyCountFont)
                Spacer(Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clickable { showTranslationDialog = true }

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_speaker),
                        contentDescription = "Choose Voice",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Choose Voice",
                        fontSize = 16.sp,
                        fontFamily = MyregularFont,
                        modifier = Modifier.weight(1f),
                    )
                    Box(
                        modifier = Modifier
                            .background(color = colorResource(R.color.background_color), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = selectedGender,
                                fontSize = 14.sp,
                                fontFamily = MyregularFont,
                                color = Color.Black
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown",
                                tint = Color.Black
                            )
                        }
                    }
                }

                // Translation Options Dialog
                if (showTranslationDialog) {
                    AlertDialog(
                        onDismissRequest = { showTranslationDialog = false },
                        title = { Text("Select") },
                        buttons = {
                            Column(
                                modifier = Modifier
                                    .padding(all = 16.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    "Male",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedGender = "Male"
                                            showTranslationDialog = false
                                        }
                                        .padding(vertical = 8.dp)
                                )
                                Text(
                                    "Female",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedGender = "Female"
                                            showTranslationDialog = false
                                        }
                                        .padding(vertical = 8.dp)
                                )
                            }
                        }
                    )
                }

                SettingSwitch(
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_reading),
                            contentDescription = "Hadith Reference Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    title = "Reading Out Hadith Title",
                    checked = readingTitleEnabled,
                    onCheckedChange = { readingTitleEnabled = it }
                )
                Spacer(Modifier.height(16.dp))
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
            }

            item {
                Text("Notifications", fontSize = 18.sp, fontFamily = MyCountFont)
                Spacer(Modifier.height(8.dp))

                SettingSwitch(
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_reward),
                            contentDescription = "Hadith Reference Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    title = "Rewards",
                    checked = rewardsEnabled,
                    onCheckedChange = { rewardsEnabled = it }
                )
                Spacer(Modifier.height(16.dp))
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
            }

            item {
                val context = LocalContext.current
                Text("About", fontSize = 18.sp, fontFamily = MyCountFont)
                Spacer(Modifier.height(8.dp))

                SettingRow(title = "About App",  icon = {
                    Image(
                        painter = painterResource(id = R.drawable.about_app),
                        contentDescription = "Hadith Reference Icon",
                        modifier = Modifier.size(24.dp)
                    )
                },
                    onClick = {  }
                )

                SettingRow(
                    title = "Rate App",
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_rate_app),
                            contentDescription = "Rate App Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    onClick = { rateApp(context) }
                )

                SettingRow(
                    title = "Share App",
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_share_app),
                            contentDescription = "Share App Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    onClick = { shareApp(context) } // Pass the action when tapped
                )

            }
        }
    }
}

fun rateApp(context: Context) {
    val packageName = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.dualand.appid=$packageName")
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }
}

fun shareApp(context: Context) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Check out this app!")
        putExtra(
            Intent.EXTRA_TEXT,
            "Hey! Download this app: https://play.google.com/store/apps/details?id=${context.packageName}"
        )
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}


@Composable
fun SettingSwitch(
    icon: @Composable () -> Unit,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_semibold))
    val MyregularFont = FontFamily(Font(R.font.fredoka_regular))
    val MyArabicFont = FontFamily(Font(R.font.al_quran))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        icon()  // <-- Call the composable lambda directly
        Spacer(Modifier.width(8.dp))
        Text(
            title,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            fontFamily = MyregularFont,
        )
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingRow(title: String, icon: @Composable () -> Unit,
               onClick: () -> Unit) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_semibold))
    val MyregularFont = FontFamily(Font(R.font.fredoka_regular))
    val MyArabicFont = FontFamily(Font(R.font.al_quran))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
    ) {
        icon()
        Spacer(Modifier.width(8.dp))
        Text(title, modifier = Modifier.weight(1f),fontSize = 18.sp, fontFamily = MyregularFont)
        Image(
            painter = painterResource(id = R.drawable.forward_arrow),
            contentDescription = "Hadith Reference Icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun DropdownSetting(
    title: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(vertical = 12.dp)
    ) {
        Icon(Icons.Default.ArrowDropDown, contentDescription = title)
        Spacer(Modifier.width(8.dp))
        Text(title, modifier = Modifier.weight(1f))
        Text(selectedOption)
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        options.forEach { option ->
            DropdownMenuItem(onClick = {
                onOptionSelected(option)
                expanded = false
            }, text = { Text(option) })
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingScreen(navController = rememberNavController())
    }
}
