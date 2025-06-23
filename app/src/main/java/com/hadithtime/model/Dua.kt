package com.hadithtime.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Dua.kt
@Entity(tableName = "duas")
data class Dua(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val arabic: String,
    val reference: String?,
    val ArabicTitle: String,
    val Englishreference: String?,
    val Englishtranslation: String,
    val audio_url: String?,
    val background_url: String?,
    val level: Int  // 👈 Add this field
)
