package com.hadithtime.components

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hadithtime.ui.theme.HadithTimeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.HadithViewModel
import com.hadithtime.R
import com.hadithtime.duas
import com.hadithtime.model.HadithCard
import com.hadithtime.model.HadithDataProvider.levels
import com.hadithtime.model.HadithLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HadithDashboardScreen(
    navController: NavController,
    levels: List<HadithLevel>
) {

    val context = LocalContext.current
    val view = LocalView.current
    LaunchedEffect(Unit) {
        val window = (context as? Activity)?.window ?: return@LaunchedEffect
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, view)
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        controller.hide(WindowInsetsCompat.Type.navigationBars())

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val navVisible = insets.isVisible(WindowInsetsCompat.Type.navigationBars())
            if (navVisible) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000L)
                    controller.hide(WindowInsetsCompat.Type.navigationBars())
                }
            }
            insets
        }
    }
    val viewModel: HadithViewModel = viewModel()
    val activity = context as? Activity
    BackHandler {
        activity?.finish()
    }
    LaunchedEffect(Unit) {
        viewModel.checkAndPreloadIfEmpty(duas)
    }


    val systemUiController = rememberSystemUiController()
    val scrollState = rememberLazyListState()
    val headerMaxOffset = with(LocalDensity.current) { 100.dp.toPx() }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

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
            // .padding(paddingValues)
        ) {
            LazyColumn(
                state = scrollState,
                contentPadding = PaddingValues(top = 390.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues) // ⬅️ Here! Apply bottom bar padding ONLY to scrollable content.
                   // .padding(WindowInsets.systemBars.asPaddingValues())
            ) {
//                stickyHeader {
//                    Text(
//                        text = "Hadith Library",
//                        color = colorResource(R.color.black_arabic),
//                        fontFamily = FontFamily(Font(R.font.fredoka_medium)),
//                        fontSize = 18.sp,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(Color.White)
//                            .padding(start = 16.dp, top = 15.dp)
//                    )
//                }

                items(levels) { level ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                            .offset(x = (-6).dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 11.dp, bottom = 5.dp)
                                .paint(
                                    painter = painterResource(id = R.drawable.level_bg),
                                    contentScale = ContentScale.Fit // fills box while keeping aspect ratio
                                )
                                .clip(RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = level.levelTitle,
                                style = MaterialTheme.typography.titleMedium,
                                color = colorResource(R.color.black_arabic),
                                fontFamily = FontFamily(Font(R.font.fredoka_semibold)),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .rotate(-90f)
                                    .padding(top = 5.dp, bottom = 10.dp)
                            )
                        }

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            items(level.cards) { card ->
                                Log.d("LazyRow", "Rendering card: ${card.title}, icon=${card.icon}")
                                HadithCardItem(card) {
                                    val levelNumber =
                                        level.levelTitle.filter { it.isDigit() }.toIntOrNull() ?: 1
                                    val indexInLevel = level.cards.indexOf(card)
                                    navController.navigate("titleScreenLevel$levelNumber/$levelNumber/$indexInLevel")
                                }
                            }
                        }

                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .offset { IntOffset(x = 0, y = headerOffset.toInt()) }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.header_image),
                    contentDescription = "Header Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Card(
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 45.dp)
                        .padding(horizontal = 16.dp)
                        .width(380.dp)
                        .height(115.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(44.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate("LearningTrackerScreen")
                                    }
                            ) {
                                DashboardStatColumn("Library", R.drawable.group_card)
                            }

                            DashboardStatColumn("02 of 49", R.drawable.group_card)
                            DashboardStatColumn("10 min", R.drawable.group_card)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HadithCardItem(card: HadithCard, onClick: () -> Unit) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_semibold))

    Column(
        modifier = Modifier
            .width(92.dp)
            .padding(top = 16.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = card.icon),
                contentDescription = card.title,
                modifier = Modifier.size(74.dp)
            )
        }

        Text(
            text = card.title,
            color = colorResource(R.color.black_arabic),
            fontFamily = MyCountFont,
            fontSize = 13.sp,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DashboardStatColumn(label: String, iconRes: Int) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_medium))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(62.dp)
            .padding(top = 2.dp, bottom = 2.dp)
            .height(80.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier
                .size(62.dp)
                .padding(bottom = 5.dp),
        )
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 12.sp,
            fontFamily = MyCountFont,
            color = colorResource(R.color.black_arabic),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHadithDashboardScreen() {
    HadithTimeTheme {
        val navController = rememberNavController()
        HadithDashboardScreen(
            navController = navController,
            levels
        )
    }
}
