package com.hadithtime

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hadithtime.model.DuaDatabase
import com.hadithtime.model.Dua
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DuaViewModel(application: Application) : AndroidViewModel(application) {
    private val duaDao = DuaDatabase.getDatabase(application).duaDao()
    val allDuas: Flow<List<Dua>> = duaDao.getAllDuas()

    // Function to fetch duas for specific level
    fun getDuasByLevel(level: Int): Flow<List<Dua>> {
        return duaDao.getDuasByLevel(level)
    }

    fun preloadDuas() {
        viewModelScope.launch {
            duaDao.deleteAll()
            duaDao.insertAll(duas) // Use the global duas list
        }
    }
}

val duas = listOf(
    // --- Level 1 ---
    Dua(
        level = 1,
        title = "Modesty",
        ArabicTitle = "الْحَياءُ",
        arabic = "قَالَ رَسُولُ الله ﷺ\nالْحَياءُ شُعْبَةٌ مِّنَ الإيمَان",
        reference = "(صحيح البخاري)",
        Englishtranslation = "The Messenger of Allah ﷺ said:\n “Modesty is a branch of faith.”",
        Englishreference = "(Sahih al-Bukhari)",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 1,
        title = "Faith",
        ArabicTitle = "الإيمان",
        arabic = "قَالَ رَسُولُ الله ﷺ\nقُلْ: آمَنْتُ بِاللَّهِ ثُمَّ اسْتَقِمْ",
        reference = "(صحيح مُسلم)",
        Englishtranslation = "The Messenger of Allah ﷺ said:\n\"Say, ‘I believe in Allah’, then be steadfast.”",
        Englishreference = "(Sahih al-Muslim)",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 1,
        title = "Learning the Qur'an",
        ArabicTitle = "تَعْلُمُ الْقُرْآنَ",
        arabic = "...",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 1,
        title = "Obey Parents",
        ArabicTitle = "طَاعَةُ الْوَالِدَيْنِ",
        arabic = "...",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 1,
        title = "Prayer",
        ArabicTitle = "الصَّلَاةُ",
        arabic = "...",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 1,
        title = "Manners of Speaking",
        ArabicTitle = "أَدَبُ الْكَلَامِ",
        arabic = "...",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 1,
        title = "Dua for Drinking",
        ArabicTitle = "دُعَاءُ الشُّرْبِ",
        arabic = "...",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    // --- Level 2 ---
    Dua(
        level = 2,
        title = "Dua 8",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 2,
        title = "Dua 9",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 2,
        title = "Dua 10",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 2,
        title = "Dua 11",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 2,
        title = "Dua 12",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 2,
        title = "Dua 13",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 2,
        title = "Dua 14",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    // --- Level 3 ---
    Dua(
        level = 3,
        title = "Dua 15",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 3,
        title = "Dua 16",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 3,
        title = "Dua 17",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 3,
        title = "Dua 18",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 3,
        title = "Dua 19",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 3,
        title = "Dua 20",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 3,
        title = "Dua 21",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    // --- Level 4 ---
    Dua(
        level = 4,
        title = "Dua 22",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 4,
        title = "Dua 23",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 4,
        title = "Dua 24",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 4,
        title = "Dua 25",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 4,
        title = "Dua 26",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 4,
        title = "Dua 27",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 4,
        title = "Dua 28",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    // --- Level 5 ---
    Dua(
        level = 5,
        title = "Dua 29",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 5,
        title = "Dua 30",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 5,
        title = "Dua 31",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 5,
        title = "Dua 32",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 5,
        title = "Dua 33",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 5,
        title = "Dua 34",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 5,
        title = "Dua 35",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    // --- Level 6 ---
    Dua(
        level = 6,
        title = "Dua 36",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 6,
        title = "Dua 37",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 6,
        title = "Dua 38",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 6,
        title = "Dua 39",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 6,
        title = "Dua 40",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 6,
        title = "Dua 41",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 6,
        title = "Dua 42",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    // --- Level 7 ---
    Dua(
        level = 7,
        title = "Dua 43",
        ArabicTitle = "الْحَياءُ",
        arabic = "...",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 7,
        title = "Dua 44",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 7,
        title = "Dua 45",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 7,
        title = "Dua 46",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    ),
    Dua(
        level = 7,
        title = "Dua 47",
        arabic = "...",
        ArabicTitle = "الْحَياءُ",
        reference = "...",
        Englishtranslation = "...",
        Englishreference = "",
        audio_url = "...",
        background_url = "..."
    )
)