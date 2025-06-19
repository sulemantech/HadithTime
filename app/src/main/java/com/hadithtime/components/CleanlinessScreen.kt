package com.hadithtime.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.hadithtime.R
import com.example.hadithtime.ui.theme.HadithTimeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CleanlinessScreen(
    onNavigateToSettings: () -> Unit,
    onHomeClick: () -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    val navigationBarColor = colorResource(id = R.color.white)
    val statusBarColor = colorResource(id = R.color.dua1)
    SideEffect {
        systemUiController.setStatusBarColor(color = statusBarColor)
        systemUiController.setNavigationBarColor(color = navigationBarColor)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
    ) {
        val MyArabicFont = FontFamily(Font(R.font.al_quran))
        val MyEnglishFont = FontFamily(Font(R.font.lato_regular))


        Image(
            painter = painterResource(id = R.drawable.dua1),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(685.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            TopBar(
                title = "Cleanliness",
                onSettingsClick = onNavigateToSettings,
                onHomeClick = onHomeClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.White.copy(alpha = 0.4f))
            ) {

                Column(
                    modifier = Modifier
                        .padding(top = 60.dp, start = 20.dp, end = 20.dp, bottom = 50.dp)
                        .align(Alignment.Center)
                        .verticalScroll(rememberScrollState()) // Add scroll here
                ) {
                    Text(
                        text = "قَالَ رَسُولُ الله ﷺ \nلَا يَشْرَبَنَّ أَحَدٌ مِنْكُمْ قَائِمًا\n",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.black_arabic),
                        fontFamily = MyArabicFont,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "(صحيح مُسلم)",
                        fontSize = 12.sp,
                        color = colorResource(R.color.black_arabic),
                        fontFamily = MyEnglishFont,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "The Messenger of Allah ﷺ said:",
                        fontSize = 20.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        fontFamily = MyEnglishFont,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "“None of you should drink\nwhile standing.”",
                        fontSize = 20.sp,
                        color = colorResource(R.color.black_arabic),
                        textAlign = TextAlign.Center,
                        fontFamily = MyEnglishFont,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = "(Sahih al-Muslim)",
                        fontSize = 12.sp,
                        color = colorResource(R.color.black_arabic),
                        fontFamily = MyEnglishFont,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }


            Spacer(modifier = Modifier.weight(1f))

        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            PlayerControls()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCleanlinessScreen() {
    HadithTimeTheme {
        CleanlinessScreen(
            onNavigateToSettings = {},
            onHomeClick = {}
        )
    }
}
