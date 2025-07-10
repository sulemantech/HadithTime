package com.hadithtime.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Dua.kt
@Entity(tableName = "duas")
data class Hadith(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val Duaheading: String,
    val arabic: String,
    val reference: String?,
    val arabicTitle: String,
    val englishReference: String?,
    val englishTranslation: String,
    val audioUrl: Int,
    val backgroundUrl: String?,
    val icon: Int,
    val level: Int,
    val memorized: Boolean = false,
    val isFavorite: Boolean = false
)

