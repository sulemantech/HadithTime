package com.hadithtime.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hadithtime.R
import com.hadithtime.model.Hadith

@Composable
fun HadithCard(dua: Hadith) {
    val MyArabicFont = FontFamily(Font(R.font.al_quran))
    val MyEnglishFont = FontFamily(Font(R.font.lato_regular))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        // Blurred Background Layer (matches parent size, so needs to be sized based on content)
        val contentModifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White.copy(alpha = 0.35f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.4f),
                shape = RoundedCornerShape(25.dp)
            )
            .fillMaxWidth()

        // Use Intrinsic sizing to match the content height
        Box(
            modifier = Modifier
                .then(contentModifier)
                .wrapContentHeight() // 👈 This replaces .height(400.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dua.arabic,
                    fontSize = 22.sp,
                    fontFamily = MyArabicFont,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 44.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                dua.reference?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        fontFamily = MyEnglishFont,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                dua.englishReference?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MyEnglishFont,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                dua.englishTranslation?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontFamily = MyEnglishFont,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Background blur matches content size
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(Color.White.copy(alpha = 0.1f))
                .blur(120.dp)
                .matchParentSize() // will match the foreground Box size
        )
    }
}
