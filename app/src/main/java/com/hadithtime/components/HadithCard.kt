package com.hadithtime.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hadithtime.HadithViewModel
import com.hadithtime.R
import com.hadithtime.model.Hadith

@Composable
fun HadithCard(dua: Hadith, fontSizeSp: Float, viewModel: HadithViewModel = viewModel()) {
    val arabicFontSize = fontSizeSp.sp
    val AlMajeedFont = FontFamily(Font(R.font.al_quran))
    val MyEnglishFont = FontFamily(Font(R.font.lato_regular))
   // var arabicFontSize by remember { mutableStateOf(24.sp) }
    val ArabicFont2 = FontFamily(Font(R.font.vazirmatn_regular))
    var selectedFont by rememberSaveable { mutableStateOf("Al Majeed Quran") }
    val fontFamily = when (viewModel.selectedFont) {
        "Al Majeed Quran" -> AlMajeedFont
        "Arabic font 2" -> ArabicFont2
        else -> AlMajeedFont
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(25.dp))
                .background(Color.White.copy(alpha = 0.15f))
                .blur(200.dp)
        )

        val contentModifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White.copy(alpha = 0.25f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.4f),
                shape = RoundedCornerShape(25.dp)
            )
            .fillMaxWidth()

        Box(
            modifier = Modifier
                .then(contentModifier)
                .wrapContentHeight()
                .heightIn(max = 420.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                dua.arabic?.let { reference ->
                    val lines = reference.split("\n", limit = 2)

                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = ParagraphStyle(
                                    lineHeight = (arabicFontSize.value * 1.8).sp,
                                    textAlign = TextAlign.Center
                                )
                            ) {
                                if (lines.isNotEmpty()) {
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.W200,
                                            fontSize = arabicFontSize,
                                            fontFamily = fontFamily,
                                        )
                                    ) {
                                        append(lines[0] + "\n")
                                    }
                                }
                                if (lines.size > 1) {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = arabicFontSize,
                                            fontFamily = fontFamily,
                                        )
                                    ) {
                                        append(lines[1])
                                    }
                                }
                            }
                        },
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                dua.reference?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontFamily = MyEnglishFont,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                dua.englishReference?.let { reference ->
                    val lines = reference.split("\n", limit = 2)

                    Text(
                        buildAnnotatedString {
                            if (lines.isNotEmpty()) {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.W200,
                                        fontSize = 20.sp,
                                        fontFamily = MyEnglishFont
                                    )
                                ) {
                                    append(lines[0] + "\n")
                                }
                            }
                            if (lines.size > 1) {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        fontFamily = MyEnglishFont
                                    )
                                ) {
                                    append(lines[1])
                                }
                            }
                        },
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        lineHeight = 28.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))

                dua.englishTranslation?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        fontFamily = MyEnglishFont,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
