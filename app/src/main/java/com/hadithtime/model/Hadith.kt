package com.hadithtime.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Dua.kt
@Entity(tableName = "duas")
data class Hadith(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val arabic: String,
    val reference: String?,
    val arabicTitle: String,
    val englishReference: String?,
    val englishTranslation: String,
    val audioUrl: String?,
    val backgroundUrl: String?,
    val level: Int
)

