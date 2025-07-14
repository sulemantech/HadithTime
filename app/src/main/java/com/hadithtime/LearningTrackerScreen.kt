package com.hadithtime

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.components.BottomNavigationBar
import com.hadithtime.model.Hadith

@SuppressLint("UnrememberedMutableState")
@Composable
fun LearningTrackerScreen(
    navController: NavController,
    viewModel: HadithViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val filteredDuas by viewModel.filteredDuas.collectAsState()
    // val selectedLevel by viewModel.selectedLevel.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.checkAndPreloadIfEmpty(duas)
    }

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
    val scrollState = rememberLazyListState()
    val headerMaxOffset = with(LocalDensity.current) { 100.dp.toPx() }
    val MyCountFont = FontFamily(Font(R.font.fredoka_semibold))
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
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(18.dp)
            ) {
                Text(
                    text = "Learning Tracker",
                    fontSize = 20.sp,
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
                val selectedFilter by viewModel.selectedFilter.collectAsState()

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 18.dp)
                ) {
                    FilterChip(
                        "All",
                        selected = selectedFilter == "All"
                    ) { viewModel.setSelectedFilter("All") }
                    FilterChip(
                        "Memorized",
                        selected = selectedFilter == "Memorized"
                    ) { viewModel.setSelectedFilter("Memorized") }
                    FilterChip(
                        "Favorite",
                        selected = selectedFilter == "Favorite"
                    ) { viewModel.setSelectedFilter("Favorite") }
                    FilterChip(
                        "In Progress",
                        selected = selectedFilter == "In Progress"
                    ) { viewModel.setSelectedFilter("In Progress") }
                }

                if (selectedFilter == "In Progress") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {

                    }
                } else if (selectedFilter == "Memorized") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.filter_color)),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_play),
                                    contentDescription = "Play Icon",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Play Memorized",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                } else if (selectedFilter == "Favorite") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {

                                Toast.makeText(
                                    context,
                                    "Play Favorite clicked!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.filter_color)),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_play),
                                    contentDescription = "Play Icon",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Play Favorite",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Select Level", fontWeight = FontWeight.SemiBold)
                        val selectedLevels by viewModel.selectedLevels.collectAsState()
                        SelectLevelDropdown(
                            selectedLevels = selectedLevels,
                            onLevelsSelected = { selected ->
                                viewModel.setLevels(selected)
                            }
                        )
                    }
                }

                Divider()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 15.dp, bottom = 10.dp)
                ) {
                    itemsIndexed(filteredDuas) { index, hadith ->
                        val levelNumber = hadith.level

                        val indexInLevel = filteredDuas
                            .filter { it.level == levelNumber }
                            .indexOfFirst { it == hadith }

                        Hadith(hadith, navController, levelNumber, indexInLevel)
                        Divider(
                            color = Color.Gray.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val textColor = if (selected) Color.White else Color.Black
    val MyCountFont = FontFamily(Font(R.font.fredoka_medium))

    Surface(
        color = if (selected) colorResource(R.color.filter_color) else colorResource(R.color.filter_color_bg),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 7.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            color = textColor,
            fontSize = 14.sp,
            fontFamily = MyCountFont
        )
    }
}

@Composable
fun SelectLevelDropdown(
    selectedLevels: List<Int>,
    onLevelsSelected: (List<Int>) -> Unit
) {
    var dialogOpen by remember { mutableStateOf(false) }
    val currentSelection = remember { mutableStateListOf<Int>().apply { addAll(selectedLevels) } }
    val MyCountFont = FontFamily(Font(R.font.fredoka_semibold))

    val displayedText =
        if (selectedLevels.isEmpty()) "Select" else selectedLevels.joinToString(", ") { " $it" }

    Box {
        Button(
            onClick = { dialogOpen = true },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.filter_color)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = displayedText, color = Color.White, fontFamily = MyCountFont)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.dropdown_arrow),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.White
            )
        }
    }

    if (dialogOpen) {
        AlertDialog(
            onDismissRequest = { dialogOpen = false },
            title = {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.clear),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Clear Filter",
                            fontSize = 16.sp,
                            fontFamily = MyCountFont,
                            color = Color.Black
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.5f)
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Manual grid layout, 3 chips per row
                    val levels = (1..7).toList()
                    val rows = levels.chunked(3)

                    rows.forEach { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            row.forEach { level ->
                                FilterChipDropDown(
                                    label = "Level $level",
                                    selected = level in currentSelection,
                                    onClick = {
                                        if (currentSelection.contains(level)) {
                                            currentSelection.remove(level)
                                        } else {
                                            currentSelection.add(level)
                                        }
                                    }
                                )
                            }
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.5f)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { dialogOpen = false }) {
                            Text(
                                text = "Cancel",
                                fontFamily = MyCountFont,
                                color = colorResource(R.color.filter_color)
                            )
                        }
                        TextButton(onClick = {
                            dialogOpen = false
                            onLevelsSelected(currentSelection.toList())
                        }) {
                            Text(
                                text = "OK",
                                fontFamily = MyCountFont,
                                color = colorResource(R.color.filter_color)
                            )
                        }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            confirmButton = {},
            dismissButton = {}
        )
    }
}

@Composable
fun FilterChipDropDown(label: String, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (selected) colorResource(R.color.filter_color) else Color.Transparent
    val textColor = if (selected) Color.White else Color.Black
    val myFont = FontFamily(Font(R.font.fredoka_semibold))
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .border(
                width = 1.dp,
                color = if (selected) Color.Black else Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
          //  .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontFamily = myFont
        )
    }

}

@Composable
fun Hadith(
    hadith: Hadith,
    navController: NavController,
    levelNumber: Int,
    indexInLevel: Int
) {
    val MyEnglishFont = FontFamily(Font(R.font.lato_regular))
    val lineDrawable = when (hadith.level) {
        0 -> R.drawable.line_level1
        1 -> R.drawable.line_level1
        2 -> R.drawable.line_level2
        3 -> R.drawable.line_level3
        4 -> R.drawable.line_level4
        5 -> R.drawable.line_level5
        6 -> R.drawable.line_level6
        7 -> R.drawable.line_level7
        else -> R.drawable.line_level1
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
           // .background(Color.White)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = Color.Gray.copy(alpha = 0.3f)
                ),
                onClick = {
                    navController.navigate("titleScreenLevel$levelNumber/$levelNumber/$indexInLevel")
                }
            )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = hadith.icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 9.dp)
            ) {
                hadith.englishReference?.let { reference ->
                    val lines = reference.split("\n", limit = 2)
                    val annotatedString = buildAnnotatedString {
                        if (lines.isNotEmpty()) {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.W200,
                                    fontSize = 16.sp,
                                    fontFamily = MyEnglishFont
                                )
                            ) { append(lines[0] + "\n") }
                        }
                        if (lines.size > 1) {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    fontFamily = MyEnglishFont
                                )
                            ) { append(lines[1]) }
                        }
                    }
                    Box(
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = annotatedString,
                            style = LocalTextStyle.current.copy(
                                color = Color.Black,
                                lineHeight = 28.sp
                            ),
                            maxLines = 2,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

            }

            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(34.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = lineDrawable),
                    contentDescription = "Level indicator line",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
