package com.hadithtime.components

import androidx.compose.ui.graphics.Color
import com.hadithtime.R

data class Dua(
    val title: String,
    val backgroundRes: Int,
    val girlImageRes: Int,
    val bubbleImageRes: Int,
    val levelBadgeRes: Int = R.drawable.level_two_badge,
    val statusBarColor: Color,
    val navigationBarColor: Color,
    val nextScreenRoute: String
)
