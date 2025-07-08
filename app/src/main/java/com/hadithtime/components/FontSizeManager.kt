package com.hadithtime.components

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// FontSizeManager.kt
object FontSizeManager {
    private val Context.dataStore by preferencesDataStore("user_settings")
    private val FONT_SIZE_KEY = floatPreferencesKey("arabic_font_size")

    suspend fun saveFontSize(context: Context, size: Float) {
        context.dataStore.edit { prefs ->
            prefs[FONT_SIZE_KEY] = size
        }
    }

    fun getFontSizeFlow(context: Context): Flow<Float> {
        return context.dataStore.data
            .map { prefs -> prefs[FONT_SIZE_KEY] ?: 24f }
    }

    private val SELECTED_FONT_KEY = stringPreferencesKey("selected_arabic_font")

    suspend fun saveSelectedFont(context: Context, fontName: String) {
        context.dataStore.edit { settings ->
            settings[SELECTED_FONT_KEY] = fontName
        }
    }

    fun getSelectedFontFlow(context: Context): Flow<String> {
        return context.dataStore.data
            .map { prefs -> prefs[SELECTED_FONT_KEY] ?: "Al Majeed Quran" }
    }
}
