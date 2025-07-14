package com.hadithtime

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hadithtime.components.BottomNavigationBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hadithtime.components.FontSizeManager
import com.hadithtime.components.GenderDialog
import com.hadithtime.components.GenderOptionRow
import com.hadithtime.components.LanguageDialog
import com.hadithtime.components.LanguageOptionRow
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: HadithViewModel = viewModel()
) {

    var selectedEnglisLanguage by remember { mutableStateOf("English") }
    var selectedUrduLanguage by remember { mutableStateOf(false) }
    var selectedTranslation by remember { mutableStateOf("English") }
    var selectedGender by remember { mutableStateOf("Male") }
    var selectedVoice by remember { mutableStateOf("Male") }
    var selectedFont by remember { mutableStateOf("Al Majeed Quran") }
//    var fontSize by remember { mutableStateOf(16f) }
    var hadithReferenceEnabled by remember { mutableStateOf(false) }
  //  var autoNextHadithEnabled by remember { mutableStateOf(false) }

    var rewardsEnabled by remember { mutableStateOf(false) }
    var showTranslationDialog by remember { mutableStateOf(false) }
    val MyCountFont = FontFamily(Font(R.font.fredoka_semibold))
    val MyregularFont = FontFamily(Font(R.font.fredoka_regular))
    val MyArabicFont = FontFamily(Font(R.font.al_quran))
    val fontSize by viewModel.fontSize
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
                    color = colorResource(R.color.black),
                    fontFamily = MyCountFont,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    textAlign = TextAlign.Center
                )
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp)
                )
            }

            item {
                Text(
                    "Language",
                    fontSize = 18.sp,
                    color = colorResource(R.color.black),
                    fontFamily = MyCountFont,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(start = 5.dp, bottom = 5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_app_lang),
                            contentDescription = "App Language Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "App Language",
                            fontSize = 16.sp,
                            color = colorResource(R.color.black),
                            fontFamily = MyregularFont,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    val context = LocalContext.current
                    val scope = rememberCoroutineScope()
                    val isEnglish by FontSizeManager.getEnglishToggle(context)
                        .collectAsState(initial = true)
                    val isUrdu by FontSizeManager.getUrduToggle(context)
                        .collectAsState(initial = false)

                    SettingSwitch(
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_en),
                                contentDescription = "English",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        title = "English",
                        checked = isEnglish,
                        textColor = Color.White,
                        onCheckedChange = {
                            scope.launch {
                                FontSizeManager.saveEnglishToggle(context, it)
                            }
                        }
                    )

                    SettingSwitch(
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_pk),
                                contentDescription = "Urdu",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        title = "Urdu",
                        textColor = Color.White,
                        checked = isUrdu,
                        onCheckedChange = {
                            scope.launch {
                                FontSizeManager.saveUrduToggle(context, it)
                            }
                        }
                    )

                }
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
            }
            val selectedFont = viewModel.selectedFont

            item {
                Text(
                    "Font Settings",
                    fontSize = 18.sp,
                    fontFamily = MyCountFont,
                    color = colorResource(R.color.black),
                )
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.font),
                        contentDescription = "Dua Translation",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 8.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Al Majeed Quran", fontSize = 16.sp, color = colorResource(R.color.black),
                        fontFamily = MyregularFont, modifier = Modifier.weight(1f)
                    )
                    RadioButton(
                        selected = selectedFont == "Al Majeed Quran",
                        onClick = { viewModel.updateFont("Al Majeed Quran") },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.filter_color),
                            unselectedColor = Color.Gray
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.font),
                        contentDescription = "Dua Translation",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 8.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Arabic font 2",
                        fontSize = 16.sp,
                        fontFamily = MyregularFont,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    RadioButton(
                        selected = selectedFont == "Arabic font 2",
                        onClick = { viewModel.updateFont("Arabic font 2") },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.filter_color),
                            unselectedColor = Color.Gray
                        )
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
                            modifier = Modifier
                                .size(24.dp)
                                .padding(start = 8.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Font Size",
                            fontSize = 16.sp,
                            color = colorResource(R.color.black),
                            fontFamily = MyregularFont,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 20.dp)
                        )
                    }

                    // The Arabic text centered
                    Text(
                        text = "ٱلْـحَـمْـدُ للهِ",
                        fontSize = fontSize.sp,
                        color = colorResource(R.color.black),
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
                                thumbColor = colorResource(R.color.filter_color),
                                activeTrackColor = colorResource(R.color.filter_color_bg),
                                inactiveTrackColor = colorResource(R.color.filter_color_bg)
                            )
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
                Text(
                    "Hadith Setting",
                    fontSize = 18.sp,
                    fontFamily = MyCountFont,
                    color = colorResource(R.color.black),
                )
                Spacer(Modifier.height(8.dp))

                var showDialog by remember { mutableStateOf(false) }
                var selectedTranslation by rememberSaveable { mutableStateOf("English") }
                var dropdownOffset by remember { mutableStateOf(Offset.Zero) }
                val density = LocalDensity.current

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 8.dp, bottom = 5.dp)
                        .clickable { showDialog = true }

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_translation),
                        contentDescription = "Hadith Translation",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Hadith Translation",
                        fontSize = 16.sp,
                        color = colorResource(R.color.black),
                        fontFamily = MyregularFont,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                dropdownOffset = coordinates.positionInWindow()
                            }
                            .background(
                                color = colorResource(R.color.background_color),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clickable { showDialog = true }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = selectedTranslation,
                                fontSize = 14.sp,
                                fontFamily = MyregularFont,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.button_dropdown),
                                contentDescription = "Repeat",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                DropdownMenu(
                    expanded = showDialog,
                    onDismissRequest = { showDialog = false },
                    offset = with(density) {
                        DpOffset(
                            x = dropdownOffset.x.toDp() - 90.dp,
                            y = dropdownOffset.y.toDp() + 56.dp
                        )
                    },
                    modifier = Modifier
                        .width(220.dp)
                        .background(Color.White, RoundedCornerShape(6.dp))
                        .border(
                            width = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(6.dp)
                        )
                ) {
                    LanguageOptionRow(
                        icon = R.drawable.ic_english,
                        label = "English",
                        isSelected = selectedTranslation == "English",
                        onClick = {
                            selectedTranslation = "English"
                            showDialog = false
                        }
                    )
                    Divider()
                    LanguageOptionRow(
                        icon = R.drawable.ic_urdu,
                        label = "Urdu",
                        isSelected = selectedTranslation == "Urdu",
                        onClick = {
                            selectedTranslation = "Urdu"
                            showDialog = false
                        }
                    )
                }

                val autoNextHadithEnabled by viewModel.autoNextHadithEnabled.collectAsState()
                SettingSwitch(
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = "Hadith Reference Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    title = "Auto Next Hadith",
                    textColor = Color.White,
                    checked = autoNextHadithEnabled,
                    onCheckedChange = { viewModel.setautoNextHadithEnabled(it) }
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
                Text(
                    "Audio",
                    fontSize = 18.sp,
                    fontFamily = MyCountFont,
                    color = colorResource(R.color.black),
                )
                Spacer(Modifier.height(8.dp))

                var showForGenderDialog by remember { mutableStateOf(false) }
                var selectedGender by rememberSaveable { mutableStateOf("Male") }
                var dropdownOffset by remember { mutableStateOf(Offset.Zero) }
                val density = LocalDensity.current

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 8.dp, bottom = 5.dp)
                        .clickable { showForGenderDialog = true }

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
                        color = colorResource(R.color.black),
                        fontFamily = MyregularFont,
                        modifier = Modifier.weight(1f),
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorResource(R.color.background_color),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = selectedGender,
                                fontSize = 14.sp,
                                fontFamily = MyregularFont,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.button_dropdown),
                                contentDescription = "Repeat",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
                DropdownMenu(
                    expanded = showForGenderDialog,
                    onDismissRequest = { showForGenderDialog = false },
                    offset = with(density) {
                        DpOffset(
                            x = dropdownOffset.x.toDp() - 10.dp,
                            y = dropdownOffset.y.toDp() + 76.dp
                        )
                    },
                    modifier = Modifier
                        .width(220.dp)
                        .background(Color.White, RoundedCornerShape(6.dp))
                        .border(
                            width = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(6.dp)
                        )
                ) {
                    GenderOptionRow(
                        icon = R.drawable.ic_male,
                        label = "Male",
                        isSelected = selectedGender == "Male",
                        onClick = {
                            selectedGender = "Male"
                            showForGenderDialog = false
                        }
                    )
                    Divider()
                    GenderOptionRow(
                        icon = R.drawable.ic_female,
                        label = "Female",
                        isSelected = selectedGender == "Female",
                        onClick = {
                            selectedGender = "Female"
                            showForGenderDialog = false
                        }
                    )
                }
                val readingTitleEnabled by viewModel.readingTitleEnabled.collectAsState()

                SettingSwitch(
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_reading),
                            contentDescription = "Hadith Reference Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    title = "Reading Out Hadith Title",
                    textColor = Color.White,
                    checked = readingTitleEnabled,
                    onCheckedChange = { viewModel.setReadingTitleEnabled(it) }
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
                Text(
                    "Notifications",
                    fontSize = 18.sp,
                    fontFamily = MyCountFont,
                    color = colorResource(R.color.black),
                )
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
                    textColor = Color.White,
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
                Text(
                    "About",
                    fontSize = 18.sp,
                    fontFamily = MyCountFont,
                    color = colorResource(R.color.black),
                )
                Spacer(Modifier.height(8.dp))

                SettingRow(title = "About App", textColor = Color.White,
                    icon = {
                    Image(
                        painter = painterResource(id = R.drawable.about_app),
                        contentDescription = "Hadith Reference Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 8.dp)
                    )
                },
                    onClick = { }
                )

                SettingRow(
                    title = "Rate App",  textColor = Color.White,
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_rate_app),
                        contentDescription = "Rate App Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 8.dp)
                    )
                },
                onClick = { rateApp(context) }
                )

                SettingRow(
                    title = "Share App",
                    textColor = Color.White,
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_share_app),
                            contentDescription = "Share App Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(start = 8.dp)
                        )
                    },
                    onClick = { shareApp(context) }
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
                Uri.parse("https://play.google.com/store/apps/details?id=com.dualand.app")
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.dualand.app")
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
    textColor: Color = Color.Black,
    onCheckedChange: (Boolean) -> Unit
) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_semibold))
    val MyregularFont = FontFamily(Font(R.font.fredoka_regular))
    val MyArabicFont = FontFamily(Font(R.font.al_quran))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
    ) {
        icon()
        Spacer(Modifier.width(8.dp))
        Text(
            title,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = MyregularFont,
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = colorResource(R.color.filter_color),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = colorResource(R.color.filter_color_unchecked)
            )
        )
    }
}

@Composable
fun SettingRow(
    title: String, icon: @Composable () -> Unit,
    onClick: () -> Unit, textColor: Color = Color.Black,
) {
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
        Text(title, modifier = Modifier.weight(1f), fontSize = 18.sp, fontFamily = MyregularFont,  color = Color.Black,)
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
