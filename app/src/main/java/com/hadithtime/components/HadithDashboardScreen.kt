package com.hadithtime.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadithtime.R
import com.hadithtime.model.HadithCard
import com.hadithtime.model.HadithLevel

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HadithDashboardScreen(
    navController: NavController,
    levels: List<HadithLevel>
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
               // .padding(paddingValues) // ⬅️ ensures content respects bottom bar
        ) {
            // 1️⃣ LazyColumn for scrollable content
            LazyColumn(
                state = scrollState,
                contentPadding = PaddingValues(top = 370.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues) // ⬅️ Here! Apply bottom bar padding ONLY to scrollable content.
                    .padding(WindowInsets.systemBars.asPaddingValues())
            ) {
                stickyHeader {
                    Text(
                        text = "Hadith Library",
                        color = colorResource(R.color.black_arabic),
                        fontFamily = FontFamily(Font(R.font.fredoka_semibold)),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }

                items(levels) { level ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .padding(start = 10.dp, top = 10.dp)
                                .background(
                                    color = colorResource(id = R.color.background_color),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                        ) {
                            Text(
                                text = level.levelTitle,
                                style = MaterialTheme.typography.titleMedium,
                                color = colorResource(R.color.black_arabic),
                                fontFamily = FontFamily(Font(R.font.fredoka_semibold)),
                                fontSize = 14.sp,
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            items(level.cards) { card ->
                                HadithCardItem(card) {
                                    val levelNumber = level.levelTitle.filter { it.isDigit() }.toIntOrNull() ?: 1
                                    val indexInLevel = level.cards.indexOf(card)
                                    navController.navigate("titleScreenLevel$levelNumber/$levelNumber/$indexInLevel")
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            // 2️⃣ Sticky header image + stats card
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
                        .height(127.dp)
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
                                .padding(vertical = 16.dp, horizontal = 11.dp),
                            horizontalArrangement = Arrangement.spacedBy(44.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DashboardStatColumn("Cleanliness", R.drawable.icon_card)
                            DashboardStatColumn("02 of 49", R.drawable.icon_card)
                            DashboardStatColumn("10 min", R.drawable.icon_card)
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
            .width(100.dp)
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
     //   }

        Text(
            text = card.title,
            color = Color.Black,
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(62.dp)
            .height(75.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(62.dp),
        )
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}
