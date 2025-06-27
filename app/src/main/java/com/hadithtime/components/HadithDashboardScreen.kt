package com.hadithtime.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hadithtime.R

@Composable
fun HadithDashboardScreen(
    navController: NavController,
    levels: List<HadithLevel>
) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_semibold))

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        // HEADER IMAGE WITHOUT PADDING
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
        ) {
            // Full-size header image
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(62.dp)
                                .height(75.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_card),
                                contentDescription = "Cleanliness",
                                modifier = Modifier.size(62.dp),
                            )
                            Text("Cleanliness", style = MaterialTheme.typography.labelSmall)
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(62.dp)
                                .height(75.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_card),
                                contentDescription = "Progress",
                                modifier = Modifier.size(62.dp),

                            )
                            Text("02 of 49", style = MaterialTheme.typography.labelSmall)
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(62.dp)
                                .height(75.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_card),
                                contentDescription = "Time",
                                modifier = Modifier.size(62.dp),
                            )
                            Text("10 min", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

        Text(
            text = "Hadith Library",
            color = colorResource(R.color.black_arabic),
            fontFamily = MyCountFont,
            fontSize = 18.sp,
         //   style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(top = 64.dp, start = 16.dp, end = 16.dp) // enough top padding so it sits below the floating card
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // sets consistent left (start) padding for everything inside
                .verticalScroll(rememberScrollState())
        ) {
            levels.forEach { level ->
                Box(
                    modifier = Modifier
                        .width(70.dp)
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
                        fontFamily = MyCountFont,
                        fontSize = 14.sp,
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(0.dp) // no extra horizontal padding, aligns with parent Column padding
                ) {
                    items(level.cards) { card ->
                        HadithCardItem(card) {
                            navController.navigate("titleScreenLevel1/${card.title}")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
       // HadithDashboardScreen(navController = rememberNavController(), levels = levels)

    }

}

@Composable
fun TopIllustration() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFE8F4FD), RoundedCornerShape(24.dp))
    ) {
        // Replace with actual illustration
        Text(
            text = "Illustration Here",
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black
        )
    }
}
@Composable
fun HadithCardItem(card: HadithCard, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Card(
//            shape = RoundedCornerShape(16.dp),
//          //  colors = CardDefaults.cardColors(containerColor = card.color),
//            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//            modifier = Modifier
//                .size(62.dp) // square card, adjust as needed
//        ) {
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
            color = Color.Black, // black text for better readability outside card
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

data class HadithCard(
    val title: String,
    val icon: Int, // Drawable resource ID
  //  val color: Color
)

data class HadithLevel(
    val levelTitle: String,
    val cards: List<HadithCard>
)

val levels = listOf(
    HadithLevel(
        "Level 1",
        listOf(
            HadithCard("Belief", R.drawable.icon_level1_dua1,),
            HadithCard("Tawheed", R.drawable.icon_level1_dua2, ),
            HadithCard("Learning Quran", R.drawable.icon_level1_dua3, ),
            HadithCard("Learning Quran", R.drawable.home_icon,)
        )
    ),
    HadithLevel(
        "Level 2",
        listOf(
            HadithCard("Cleanliness", R.drawable.icon_level2_dua1, ),
            HadithCard("Imitation of Sunnah", R.drawable.icon_level2_dua2,),
            HadithCard("Prayers", R.drawable.icon_level2_dua3, ),
            HadithCard("Cleanliness", R.drawable.icon_level2_dua1, ),
            HadithCard("Imitation of Sunnah", R.drawable.icon_level2_dua2,),
            HadithCard("Prayers", R.drawable.icon_level2_dua3, )
        )
    ),
    HadithLevel(
        "Level 3",
        listOf(
            HadithCard("Cleanliness", R.drawable.icon_level3_dua1, ),
            HadithCard("Obedience", R.drawable.icon_level3_dua2,),
            HadithCard("Prayers", R.drawable.icon_level3_dua3, ) ,
            HadithCard("Cleanliness", R.drawable.icon_level3_dua1, ),
            HadithCard("Imitation of Sunnah", R.drawable.icon_level3_dua2,),
            HadithCard("Prayers", R.drawable.icon_level3_dua3, )
        )
    ),
    HadithLevel(
        "Level 4",
        listOf(
            HadithCard("Cleanliness", R.drawable.icon_level3_dua1, ),
            HadithCard("Obedience", R.drawable.icon_level3_dua2,),
            HadithCard("Prayers", R.drawable.icon_level3_dua3, ) ,
            HadithCard("Cleanliness", R.drawable.icon_level3_dua1, ),
            HadithCard("Imitation of Sunnah", R.drawable.icon_level3_dua2,),
            HadithCard("Prayers", R.drawable.icon_level3_dua3, )
        )
    ),
    HadithLevel(
        "Level 5",
        listOf(
            HadithCard("Cleanliness", R.drawable.icon_level3_dua1, ),
            HadithCard("Obedience", R.drawable.icon_level3_dua2,),
            HadithCard("Prayers", R.drawable.icon_level3_dua3, ) ,
            HadithCard("Cleanliness", R.drawable.icon_level3_dua1, ),
            HadithCard("Imitation of Sunnah", R.drawable.icon_level3_dua2,),
            HadithCard("Prayers", R.drawable.icon_level3_dua3, )
        )
    ),
    HadithLevel(
        "Level 6",
        listOf(
            HadithCard("Cleanliness", R.drawable.icon_level3_dua1, ),
            HadithCard("Obedience", R.drawable.icon_level3_dua2,),
            HadithCard("Prayers", R.drawable.icon_level3_dua3, ) ,
            HadithCard("Cleanliness", R.drawable.icon_level3_dua1, ),
            HadithCard("Imitation of Sunnah", R.drawable.icon_level3_dua2,),
            HadithCard("Prayers", R.drawable.icon_level3_dua3, )
        )
    ),
    HadithLevel(
        "Level 7",
        listOf(
            HadithCard("Cleanliness", R.drawable.icon_level3_dua1, ),
            HadithCard("Obedience", R.drawable.icon_level3_dua2,),
            HadithCard("Prayers", R.drawable.icon_level3_dua3, ) ,
            HadithCard("Cleanliness", R.drawable.icon_level3_dua1, ),
            HadithCard("Imitation of Sunnah", R.drawable.icon_level3_dua2,),
            HadithCard("Prayers", R.drawable.icon_level3_dua3, )
        )
    ),
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardWithBottomNav() {
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) {  innerPadding ->
        HadithDashboardScreen(navController = rememberNavController(), levels = levels)
    }
}

@Composable
fun BottomNavigationBar() {
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = true,
            onClick = {}
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorite") },
            selected = false,
            onClick = {}
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            selected = false,
            onClick = {}
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
            selected = false,
            onClick = {}
        )
    }
}
