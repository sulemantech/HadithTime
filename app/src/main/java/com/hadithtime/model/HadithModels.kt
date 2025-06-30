package com.hadithtime.model


import androidx.annotation.DrawableRes

data class HadithCard(
    val title: String,
    @DrawableRes val icon: Int,
    // Uncomment if you want color in future:
    // val color: Color
)

data class HadithLevel(
    val levelTitle: String,
    val cards: List<HadithCard>
)


