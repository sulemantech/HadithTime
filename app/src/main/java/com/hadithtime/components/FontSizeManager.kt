package com.hadithtime.components

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_settings")

object FontSizeManager {

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

    // Selected font name
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

    private val LANGUAGE_KEY = booleanPreferencesKey("is_english")

    fun getLanguagePreference(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[LANGUAGE_KEY] ?: true
        }
    }

    private val IS_ENGLISH_ENABLED = booleanPreferencesKey("is_english_enabled")
    private val IS_URDU_ENABLED = booleanPreferencesKey("is_urdu_enabled")

    fun getEnglishToggle(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { it[IS_ENGLISH_ENABLED] ?: true }
    }

    fun getUrduToggle(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { it[IS_URDU_ENABLED] ?: false }
    }

    suspend fun saveEnglishToggle(context: Context, isEnabled: Boolean) {
        context.dataStore.edit { it[IS_ENGLISH_ENABLED] = isEnabled }
    }

    suspend fun saveUrduToggle(context: Context, isEnabled: Boolean) {
        context.dataStore.edit { it[IS_URDU_ENABLED] = isEnabled }
    }

    // Reading Title Toggle
    private val READING_TITLE_KEY = booleanPreferencesKey("reading_title_enabled")

    fun getReadingTitleFlow(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { it[READING_TITLE_KEY] ?: false }
    }

    suspend fun saveReadingTitleEnabled(context: Context, isEnabled: Boolean) {
        context.dataStore.edit { it[READING_TITLE_KEY] = isEnabled }
    }

    // auto next Toggle

    private val AUTO_NEXT_HADITH_KEY = booleanPreferencesKey("auto_next_hadith")

    fun getAutoNextHadithFlow(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[AUTO_NEXT_HADITH_KEY] ?: false
        }
    }

    suspend fun saveAutoNextHadith(context: Context, isEnabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[AUTO_NEXT_HADITH_KEY] = isEnabled
        }
    }


    // Reading Title Toggle
    private val HADITH_REFERENCE_KEY = booleanPreferencesKey("hadith_reference_enabled")

    fun getHadithReferenceFlow(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { it[READING_TITLE_KEY] ?: false }
    }

    suspend fun saveHadithReferenceEnabled(context: Context, isEnabled: Boolean) {
        context.dataStore.edit { it[READING_TITLE_KEY] = isEnabled }
    }
}
