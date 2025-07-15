package com.hadithtime

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hadithtime.components.FontSizeManager
import com.hadithtime.model.HadithDatabase
import com.hadithtime.model.Hadith
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HadithViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext

    private val duaDao = HadithDatabase.getDatabase(context).duaDao()
    var arabicFontSize by mutableStateOf(24.sp)

    val allDuas: StateFlow<List<Hadith>> = duaDao.getAllDuas()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedLevels = MutableStateFlow<List<Int>>(emptyList())
    val selectedLevels: StateFlow<List<Int>> = _selectedLevels

    private val _selectedFilter = MutableStateFlow("All")
    val selectedFilter: StateFlow<String> = _selectedFilter

    val filteredDuas: StateFlow<List<Hadith>> = combine(
        allDuas, selectedFilter, selectedLevels
    ) { all, filter, levels ->
        var filtered = all

        if (levels.isNotEmpty()) filtered = filtered.filter { it.level in levels }

        filtered = when (filter) {
            "All" -> filtered
            "Favorite" -> filtered.filter { it.isFavorite }
            "Memorized" -> filtered.filter { it.memorized }
            "In Progress" -> filtered.filter { !it.memorized }
            else -> filtered
        }

        filtered
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setSelectedFilter(filter: String) {
        _selectedFilter.value = filter
    }

    fun setLevels(levels: List<Int>) {
        _selectedLevels.value = levels
    }

    fun toggleFavorite(hadith: Hadith) {
        viewModelScope.launch {
            val updatedDua = hadith.copy(isFavorite = !hadith.isFavorite)
            duaDao.updateDua(updatedDua)
            Log.d("HadithViewModel", "Updated dua ${hadith.id} favorite=${updatedDua.isFavorite}")
        }
    }

    fun toggleMemorized(hadith: Hadith) {
        viewModelScope.launch {
            val updatedDua = hadith.copy(memorized = !hadith.memorized)
            duaDao.updateDua(updatedDua)
            Log.d("HadithViewModel", "Updated dua ${hadith.id} memorized=${updatedDua.memorized}")
        }
    }


    fun checkAndPreloadIfEmpty(duas: List<Hadith>) {
        viewModelScope.launch {
            val existing = duaDao.getAllDuas().first()
            if (existing.isEmpty()) {
                duaDao.insertAll(duas)
            }
        }
    }

    fun preloadDuas(duas: List<Hadith>) {
        viewModelScope.launch {
            duaDao.deleteAll()
            duaDao.insertAll(duas)
        }
    }

    var selectedFont by mutableStateOf("Al Majeed Quran")
        private set

    private val _fontSize = mutableStateOf(24f)
    val fontSize: State<Float> = _fontSize

    init {
        viewModelScope.launch {
            FontSizeManager.getSelectedFontFlow(context).collect { savedFont ->
                selectedFont = savedFont
            }
        }

        viewModelScope.launch {
            FontSizeManager.getFontSizeFlow(context).collect {
                _fontSize.value = it
            }
        }
    }

    fun updateFont(newFont: String) {
        selectedFont = newFont
        viewModelScope.launch {
            FontSizeManager.saveSelectedFont(context, newFont)
        }
    }

    fun updateFontSize(newSize: Float) {
        _fontSize.value = newSize
        viewModelScope.launch {
            FontSizeManager.saveFontSize(context, newSize)
        }
    }

    private val _readingTitleEnabled = MutableStateFlow(false)
    val readingTitleEnabled: StateFlow<Boolean> = _readingTitleEnabled

    fun setReadingTitleEnabled(enabled: Boolean) {
        _readingTitleEnabled.value = enabled
        viewModelScope.launch {
            FontSizeManager.saveReadingTitleEnabled(context, enabled)
        }
    }
    init {
        viewModelScope.launch {
            FontSizeManager.getReadingTitleFlow(context).collect {
                _readingTitleEnabled.value = it
            }
        }
    }

    private val _autoNextHadithEnabled = MutableStateFlow(false)
    val autoNextHadithEnabled: StateFlow<Boolean> = _autoNextHadithEnabled

    fun setautoNextHadithEnabled(enabled: Boolean) {
        _autoNextHadithEnabled.value = enabled
        viewModelScope.launch {
            FontSizeManager.saveAutoNextHadith(context, enabled)
        }
    }

    init {
        viewModelScope.launch {
            FontSizeManager.getAutoNextHadithFlow(context).collect {
                _autoNextHadithEnabled.value = it
            }
        }
    }

    private val _HadithReferenceEnabled = MutableStateFlow(false)
    val HadithReferenceEnabled: StateFlow<Boolean> = _HadithReferenceEnabled

    fun setaHadithReferenceEnabled(enabled: Boolean) {
        _HadithReferenceEnabled.value = enabled
        viewModelScope.launch {
            FontSizeManager.saveHadithReferenceEnabled(context, enabled)
        }
    }

    init {
        viewModelScope.launch {
            FontSizeManager.getHadithReferenceFlow(context).collect {
                _HadithReferenceEnabled.value = it
            }
        }
    }
}

val duas = listOf(
    // Level 1: Duas 1–4
    Hadith(
        Duaheading = "Modesty",
        arabic = "قَالَ رَسُولُ الله ﷺ\nالْحَياءُ شُعْبَةٌ مِّنَ الإيمَان",
        reference = "(صحيح البخاري)",
        arabicTitle = "الْحَياءُ",
        englishReference = "The Messenger of Allah ﷺ said:\n “Modesty is a branch of faith.”",
        duaEnglishTitle = R.raw.l01_h01_en_title,
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l01_h01_ar_text,
        backgroundUrl = null,
        duaArabicTitle= R.raw.l01_h01_title_ar,
        icon = R.drawable.ic_level1_dua1,
        level = 1
    ),
    Hadith(
        Duaheading = "Faith",
        arabic = "قَالَ رَسُولُ الله ﷺ\nقُلْ آمَنْتُ بِاللَّهِ ثُمَّ اسْتَقِمْ",
        reference = "(صحيح مُسلم)",
        arabicTitle = "الإيمان",
        englishReference = "The Messenger of Allah ﷺ said:\n\"Say, ‘I believe in Allah’, then be steadfast.”",
        englishTranslation = "(Sahih al-Muslim)",
        audioUrl = R.raw.l01_h02_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l01_h02_en_title,
        icon = R.drawable.ic_level1_dua2,
        duaArabicTitle= R.raw.l01_h02_ar_title,
        level = 1
    ),
    Hadith(
        Duaheading = "Learning the Qur'an",
        arabic = "قَالَ رَسُولُ الله ﷺ\nخَيْرُكُمْ مَنْ تَعَلَّمَ الْقُرْآنَ وَعَلَّمَه",
        reference = "(صحيح البخاري)",
        arabicTitle = "فَضْلُ تَعَلُّمِ الْقُرْآنِ",
        englishReference = "The Messenger of Allah ﷺ said:\n The best amongst you is the one who learns the Qur'an and teaches it.\"",
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l01_h03_ar_text,
        backgroundUrl = null,
        duaArabicTitle= R.raw.l01_h03_title_ar,
        duaEnglishTitle = R.raw.l01_h03_en_title,
        icon = R.drawable.ic_level1_dua3,
        level = 1
    ),
    Hadith(
        Duaheading = "Cleanliness",
        arabic = "قَالَ رَسُولُ الله ﷺ\n الطُّهُورُ شَطْرُ الإِيمَانِ",
        reference = "(صحيح مُسلم)",
        arabicTitle = "الطّهارة",
        englishReference = "The Messenger of Allah ﷺ said:\n \"Cleanliness is half of faith \"",
        englishTranslation = "(Sahih al-Muslim)",
        audioUrl = R.raw.l01_h04_ar_text,
        duaArabicTitle= R.raw.l01_h04_title_ar,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l01_h04_en_title,
        icon = R.drawable.ic_level1_dua4,
        level = 1
    ),

    // Level 2: Duas 5–9
    Hadith(
        Duaheading = "Obey the Prophet ﷺ",
        arabic = "قَالَ رَسُولُ الله ﷺ\nمَنْ أَطَاعَنِي دَخَلَ الْجَنَّةَ",
        reference = "(صحيح بخارى)",
        arabicTitle = "   طَاعةُ النَّبيِّﷺ",
        englishReference = "The Messenger of Allah ﷺ said:\n \"Whoever obeys me will enter Paradise,",
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l02_h01_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l02_h01_title_en,
        icon = R.drawable.ic_level2_dua1,
        duaArabicTitle= R.raw.l02_h01_title_ar,
        level = 2
    ),
    Hadith(
        Duaheading = "Prayer",
        arabic = "قَالَ رَسُولُ الله ﷺ\n صَلُّوا الصَّلاَةَ لِوَقْتِهَا",
        reference = "(صحيح مُسلم)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n “Observed prayer at its prescribed time.”",
        englishTranslation = "(Sahih al-Muslim)",
        audioUrl = R.raw.l02_h02_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l02_h02_title_en,
        icon = R.drawable.ic_level2_dua2,
        duaArabicTitle= R.raw.l02_h02_ar_title,

        level = 2
    ),
    Hadith(
        Duaheading = "Etiquette of  Eating",
        arabic = "قَالَ رَسُولُ الله ﷺ\nسَمِّ اللَّهَ , وَكُلْ بِيَمِينِكَ",
        reference = "(صحيح البخاري)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n “Mention the Name of Allah and eat with your right hand.”",
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l02_h03_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l02_h03_title_en,
        icon = R.drawable.ic_level2_dua3,
        duaArabicTitle= R.raw.l02_h03_ar_title,
        level = 2
    ),
    Hadith(
        Duaheading = "Etiquette of Drinking",
        arabic = "قَالَ رَسُولُ الله ﷺ\n لَا يَشْرَبَنَّ أَحَدٌ مِنْكُمْ قَائِمًا",
        reference = "(صحيح مُسلم)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n “None of you should drink while standing.”",
        englishTranslation = "(Sahih al-Muslim)",
        audioUrl = R.raw.l02_h04_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l02_h04_title_en,
        icon = R.drawable.ic_level2_dua4,
        duaArabicTitle= R.raw.l02_h04_ar_title,
        level = 2
    ),
    Hadith(
        Duaheading = "Manners of Speaking",
        arabic = "قَالَ رَسُولُ الله ﷺ\n لَا تَسُبَّنَّ أَحَدًا",
        reference = "(سنن ابي داؤد)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n “Do not abuse anyone.”",
        englishTranslation = "(Sunan Abi Dawud)",
        audioUrl = R.raw.l02_h05_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l02_h05_title_en,
        icon = R.drawable.ic_level2_dua5,
        duaArabicTitle= R.raw.l02_h05_ar_title,
        level = 2
    ),

    // Level 3: Duas 10–16
    Hadith(
        Duaheading = "Spreading Salam",
        arabic = "قَالَ رَسُولُ الله ﷺ\n  أَفْشُوا السَّلاَمَ بَيْنَكُمْ",
        reference = "(صحيح مُسلم)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n “Promote greeting amongst you.”",
        englishTranslation = "(Sahih al-Muslim)",
        audioUrl = R.raw.l03_h01_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h01_title_en,
        icon = R.drawable.ic_level3_dua1,
        duaArabicTitle= R.raw.l03_h01_ar_title,

        level = 3
    ),
    Hadith(
        Duaheading = "The  Best  of  Dhikr",
        arabic = "قَالَ رَسُولُ الله ﷺ\n أَفْضَلُ الذِّكْرِ لَا إِلَهَ إِلاَّ اللَّهُ",
        reference = "(سنن الترمذي)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n There is none worthy of worship except Allah.”",
        englishTranslation = "(Sunan al-Tirmidhi)",
        audioUrl = R.raw.l03_h02_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h02_title_en,
        icon = R.drawable.ic_level3_dua2,
        duaArabicTitle= R.raw.l03_h02_ar_title,
        level = 3
    ),
    Hadith(
        Duaheading = "Supplication",
        arabic = "قَالَ رَسُولُ الله ﷺ\n إِذَا سَأَلْتَ فَاسْأَلِ اللَّهَ",
        reference = "(سنن الترمذي)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n “Whenever you ask (for anything), ask from Allah.”",
        englishTranslation = "(Sunan al-Tirmidhi)",
        audioUrl = R.raw.l03_h03_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h03_title_en,
        icon = R.drawable.ic_level3_dua3,
        duaArabicTitle= R.raw.l03_h03_ar_title,

        level = 3
    ),
    Hadith(
        Duaheading = "Mercy",
        arabic = "قَالَ رَسُولُ الله ﷺ\n مَنْ لَا يَرْحَمْ لَا يُرْحَمْ",
        reference = "(صحيح مُسلم)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n “Anyone who does not show mercy, will not be shown mercy.”",
        englishTranslation = "(Sahih al-Muslim)",
        audioUrl = R.raw.l03_h04_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h04_title_en,
        icon = R.drawable.ic_level3_dua4,
        duaArabicTitle= R.raw.l03_h04_ar_title,
        level = 3
    ),
    Hadith(
        Duaheading = "God-consciousness",
        arabic = "قَالَ رَسُولُ الله ﷺ\n اتَّقِ اللَّهَ حَيْثُمَا كُنْتَ",
        reference = "(سنن الترمذي)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n “Be conscious of Allah where ever you are.”",
        englishTranslation = "(Sunan al-Tirmidhi)",
        audioUrl = R.raw.l03_h05_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h05_title_en,
        icon = R.drawable.ic_level3_dua5,
        duaArabicTitle= R.raw.l03_h05_ar_title,
        level = 3
    ),
    Hadith(
        Duaheading = "The Good Word",
        arabic = "قَالَ رَسُولُ الله ﷺ\n الْكَلِمَةُ الطَّيِّبَةُ صَدَقَةٌ",
        reference = "(صحيح البخاري)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n‘’Good word is a charity.”",
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l03_h06_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h06_title_en,
        icon = R.drawable.ic_level3_dua6,
        duaArabicTitle= R.raw.l03_h06_ar_title,
        level = 3
    ),
    Hadith(
        Duaheading = "Righteous Companionship",
        arabic = "قَالَ رَسُولُ الله ﷺ\n الْمَرْءُ مَعَ مَنْ أَحَبَّ",
        reference = "(صحيح البخاري)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n “ A man will be with those whom he loves.\"",
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l03_h07_ar_text,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level3_dua7,
        duaArabicTitle= R.raw.l03_h07_ar_title,
        level = 3
    ),

    // Level 4: Duas 17–23
    Hadith(
        Duaheading = "Muslim Brotherhood",
        arabic = "وعن ابن عمر رضي الله عنهما أن رسول الله صلى الله عليه وسلم قال\u200F:\u200F\nالْمُسْلِمُ أخُو الْمُسْلِم",
        reference = "(متفق عليه)",
        arabicTitle = "...",
        englishReference = "Ibn 'Umar (May Allah be pleased with both of them) reported: The Messenger of Allah (ﷺ) said,  \n \"A Muslim is a brother of another Muslim.”",
        englishTranslation = "(Agreed Upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level4_dua1,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 4
    ),
    Hadith(
        Duaheading = "Gifts and Love",
        arabic = "وَعَنْ أَبِي هُرَيْرَةَ رضى الله عنه عَنِ النَّبِيِّ صلى الله عليه وسلم قَالَ\u200F:\u200F\nتَهَادُوْا تَحَابُّو ",
        reference = "(صحيح البخاري)",
        arabicTitle = "...",
        englishReference = "Abu Hurairah رضى الله عنه   reported: The Prophet (ﷺ) said:\n \" Exchange gifts, and you will love one another.\"",
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level4_dua2,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 4
    ),
    Hadith(
        Duaheading = "Left-Hand Eating (of Shaytan)",
        arabic = "عَنْ جَابِرٍ رضى الله عنه  ، عَنْ رَسُولِ الله ﷺ قَالَ\u200F:\u200F\nلاَ تَأْكُلُوا بِالشِّمَالِ فَإِنَّ الشَّيْطَانَ يَأْكُلُ بِالشِّمَالِ",
        reference = "(سنن ابن ماجه)",
        arabicTitle = "...",
        englishReference = "It was narrated from Jabir (May Allah be pleased with him) that the Messenger of Allah (ﷺ) said:\n “Do not eat with your left hand, for Satan eats with his left hand.”",
        englishTranslation = "(Sunan Ibn Majah)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level4_dua3,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 4
    ),
    Hadith(
        Duaheading = "Purity",
        arabic = "عَنْ أَبِي مَالِكٍ الأَشْعَرِيِّ رضى الله عنه، قَالَ قَالَ رَسُولُ اللَّهِﷺ:\n الطُّهُورُ شَطْرُ الإِيمَانِ وَالْحَمْدُ لِلَّهِ تَمْلأُ الْمِيزَانَ",
        reference = "(صحيح مُسلم)",
        arabicTitle = "...",
        englishReference = "Abu Malik at-Ash'ari (May Allah be pleased with him) reported: The Messenger of Allah (ﷺ) said:\n “Cleanliness is half of faith and al-Hamdu Lillah (all praise and gratitude is for Allah alone) fills the scale.”",
        englishTranslation = "(Sunan Ibn Majah)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level4_dua4,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 4
    ),
    Hadith(
        Duaheading = "Using the Miswak",
        arabic = "عَنْ عَائِشَةَ رضى الله عنها  قَالَتْ: قَالَ رَسُولُ اللَّهِﷺ\n السِّوَاكُ مَطْهَرَةٌ لِلْفَمِ مَرْضَاةٌ",
        reference = "(رواه النسائي)",
        arabicTitle = "...",
        englishReference = "'Aishah (May Allah be pleased with her) reported: The Prophet (ﷺ) said,\n \"The Miswak (tooth-stick) purifies the mouth and pleases the Rubb.\"",
        englishTranslation = "(An-Nasa'i)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level4_dua5,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 4
    ),
    Hadith(
        Duaheading = "The Sunnah of Ghusl on Friday",
        arabic = "قَالَ رَسُولُ الله ﷺ\n مَنْ جَاءَ مِنْكُمُ الْجُمُعَةَ فَلْيَغْتَسِلْ",
        reference = "(صحيح البخاري)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n\"Anyone of you coming for the Jumuah prayer should take a bath.\"",
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level4_dua6,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 4
    ),
    Hadith(
        Duaheading = "Gentleness",
        arabic = "!قَالَ رَسُولُ اللَّهِﷺ \u200F:\u200F يَا عَائِشَةُ\nتإِنَّ اللَّهَ رَفِيقٌ يُحِبُّ الرِّفْقَ فِي الأَمْرِ كُلِّهِ",
        reference = "(متفق عليه)",
        arabicTitle = "...",
        englishReference = "The Prophet (ﷺ) said, \"O `Aisha! (May Allah be pleased with her)\n \"Allah is Gentle and He loves gentleness in all matters.\"",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level4_dua7,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 4
    ),

    // Level 5: Duas 24–31

    Hadith(
        Duaheading = "Adornment is an Islamic Value",
        arabic = "،عَنْ عَبْدِ اللَّهِ بْنِ مَسْعُودٍ رضى الله عنه \n عَنِ النَّبِيِّ صلى الله عليه وسلم قَالَ\n إِنَّ اللَّهَ جَمِيلٌ يُحِبُّ الْجَمَالَ",
        reference = "(صحيح مُسلم)",
        arabicTitle = "...",
        englishReference = "Abdullah bin Mas'ud (May Allah be pleased with him) narrated that the Messenger of Allah (ﷺ),said :\n“Allah is Beautiful and He loves beauty”.   ",
        englishTranslation = "(Sahih al-Muslim)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level5_dua1,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 5
    ),
    Hadith(
        Duaheading = "The Right of the Neighbor",
        arabic = "عَنْ أَبِي هُرَيْرَةَ رضى الله عنه ، قَالَ قَالَ رَسُولُ الله ﷺ:\nمَنْ كَانَ يُؤْمِنُ بِاللَّهِ وَالْيَوْمِ الآخِرِ فَلاَ يُؤْذِ جَارَهُ",
        reference = "(صحيح البخاري)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\nAnybody who believes in Allah and the Last Day should not harm his neighbor",
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level5_dua2,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 5
    ),
    Hadith(
        Duaheading = "A Smile is Charity",
        arabic = "عن أبي ذررضى الله عنه قال:\n قال رسول الله ﷺ:\nتَبَسُّمُكَ فِي وَجْهِ أَخِيْكَ لَكَ صَدَقَةٌ",
        reference = "(رواه الترمذي)",
        arabicTitle = "...",
        englishReference = "Abu Dharr (May Allah be pleased with him) reported: Allah’s messenger ﷺ said:\n Your smiling in the face of your brother is charity.",
        englishTranslation = "(At-Tirmidhi)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level5_dua3,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 5
    ),
    Hadith(
        Duaheading = "Adornment is an Islamic Value",
        arabic = "عَنْ النَّبِيِّ ﷺ قَالَ\nلَا يُؤْمِنُ أَحَدُكُمْ حَتَّى يُحِبَّ لِأَخِيهِ مَا يُحِبُّ لِنَفْسِهِ",
        reference = "(متفق عليه\u200F)",
        arabicTitle = "...",
        englishReference = "Anas (May Allah be pleased with him) reported:\nThe Prophet (ﷺ) said, \"No one of you shall become a true believer until he desires for his brother what he desires for himself",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level5_dua4,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 5
    ),
    Hadith(
        Duaheading = "Beloved Words to Allah",
        arabic = " قَالَ رَسُولُ الله ﷺ\n:\u200Fإنّ أَحَبَّ الْكَلَامِ إلَى اللهِ\u200F\n\u200F سُبْحَانَ اللهِ وَبِحَمْدِهِ",
        reference = "(صحيح مسلم\u200F)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah ﷺ said:\n“Verily, the words most beloved to Allah are: 'Subhan-Allahi wa bihamdihi' (Allah is free from imperfection and His is the praise)'.\"\u200F\u200F",
        englishTranslation = "(Sahih al-Muslim)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level5_dua5,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 5
    ),

    Hadith(
        Duaheading = "Salawat on the Prophet ﷺ",
        arabic = "عن أَبِي هُرَيْرَةَ رضى الله عنه ، قَالَ قَالَ رَسُولُ اللَّهِ ﷺ\nمَنْ صَلَّى عَلَىَّ صَلاَةً صَلَّى اللَّهُ عَلَيْهِ بِهَا عَشْرًا",
        reference = "(رواه الترمذي)",
        arabicTitle = "...",
        englishReference = "Abu Hurairah (May Allah be pleased with him) reported: The Messenger of Allah (ﷺ) said:\nWhoever sends blessing upon me once, Allah will send blessings upon him ten times",
        englishTranslation = "(At-Tirmidhi)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level5_dua6,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 5
    ),
    Hadith(
        Duaheading = "Pillars Of Islam",
        arabic = "عَنِ ابْنِ عُمَرَ ـ رضى الله عنهما ـ قَالَ قَالَ رَسُولُ اللَّهِ ﷺ \nبُنِيَ الإِسْلاَمُ عَلَى خَمْسٍ شَهَادَةِ أَنْ لاَ إِلَهَ إِلاَّ اللَّهُ وَأَنَّ مُحَمَّدًا رَسُولُ اللَّهِ، وَإِقَامِ الصَّلاَةِ، وَإِيتَاءِ الزَّكَاةِ، وَالْحَجِّ، وَصَوْمِ رَمَضَانَ",
        reference = "Narrated Ibn 'Umar: رضى الله عنهما",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah (ﷺ) said: \"Islam is built upon five [pillars]:\n" +
                "1.    To testify that none has the right to be worshipped but Allah and Muhammad (ﷺ)is the Messenger of Allah.\n" +
                "2.    To establish the prayer.\n" +
                "3.    To give Zakat (obligatory charity).\n" +
                "4.    To perform Hajj (Pilgrimage to Mecca)\n" +
                "To observe fast during the month of Ramadan",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level5_dua7,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 5
    ),
    Hadith(
        Duaheading = "Anger",
        arabic = "عَنْ أَبِي هُرَيْرَةَ رَضِيَ اللهُ عَنْهُ أَنَّ رَجُلًا قَالَ لِلنَّبِيِّ ﷺ أَوْصِنِي. قَالَ ﷺ:\nلَا تَغْضَبْ، فَرَدَّدَ مِرَارًا، قَالَ: لَا تَغْضَبْ",
        reference = "(صحيح البخاري\u200F)",
        arabicTitle = "...",
        englishReference = "Abu Hurairah (May Allah be pleased with him) reported:\nA man asked the Prophet (ﷺ) for advice and he (ﷺ) said, \"Do not get angry\".\n" +
                "The man asked repeatedly, and he (ﷺ) kept replying, 'Do not get angry'.\"",
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level5_dua8,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 5
    ),

    // Level 6: Duas 32–39

    Hadith(
        Duaheading = "Etiquettes of Eating",
        arabic = "عُمَرَ بْنِ أَبِي سَلَمَةَ \u200F رضى الله عنه  قَالَ : قَالَ النَّبِيُّ \u200Fﷺ \u200F:\nيَا غُلَامُ ! سَمِّ اللَّهَ , وَكُلْ بِيَمِينِكَ , وَكُلْ مِمَّا يَلِيكَ",
        reference = "(مُتَّفَقٌ عَلَيْهِ)",
        arabicTitle = "...",
        englishReference = "Umar bin Abu Salamah  رضي الله عنهreported: The Messenger of Allah (ﷺ), said to me, :\nMention Allah's Name (i.e., say Bismillah), eat with your right hand, and eat from what is near you.",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level6_dua1,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 6
    ),
    Hadith(
        Duaheading = "Two Beloved Words",
        arabic = "وعن أبي هريرة رضي الله عنه قَالَ: قَالَ رَسُولُ اللَّهِ ﷺ :\n\u200F \" كَلِمَتَانِ خَفِيفَتَانِ عَلَى اللِّسَانِ ثَقِيلَتَانِ فِي الْمِيزَانِ حَبِيبَتَانِ إِلَى الرَّحْمَنِ: سُبْحَانَ اللهِ وَبِحَمْدِهِ سُبْحَانَ اللهِ الْعَظِيم",
        reference = "Abu Hurairah (May Allah be pleased with him) reported:",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah (ﷺ) said,“Two phrases are light on the tongue, heavy on the scale are beloved to the  Most Merciful (AL-Rehman) : 'Subhan-Allahi wa bihamdihi, Subhan-Allahil-Azim [Glory be to Allah and His is the praise, (and) Allah, the Greatest is free from imperfection]'\n" +
                "1.    To testify that none has the right to be worshipped but Allah and Muhammad (ﷺ)is the Messenger of Allah.\n" +
                "2.    To establish the prayer.\n" +
                "3.    To give Zakat (obligatory charity).\n" +
                "4.    To perform Hajj (Pilgrimage to Mecca)\n" +
                "To observe fast during the month of Ramadan",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level6_dua2,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 6
    ),
    Hadith(
        Duaheading = "Deeds Leading to Paradise",
        arabic = "عَنْ عَبْدِ اللَّهِ بْنِ عَمْرٍو رضي الله عنه قَالَ: قَالَ رَسُولُ اللَّهِ  ﷺ:\nأعمال توصِلُكَ إلى الجنة",
        reference = "(رَوَاهُ التِّرْمِذِيّ وَابْن مَاجَه\u200F)",
        arabicTitle = "...",
        englishReference = "The Messenger of Allah (ﷺ) said: \"(All of you) worship the Most Merciful (Ar-Rahman), feed others, and spread the (greeting of) Salam; you will enter Paradise in peace.\nNarrated 'Abdullah bin 'Amr (May Allah be pleased with him):",
        englishTranslation = "(At-Tirmidhi and Ibn-Majahi)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level6_dua3,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 6
    ),
    Hadith(
        Duaheading = "Sincerity",
        arabic = "وعن أبي هريرة عبد الرحمن بن صخر رضي الله عنه   قَالَ : قَالَ رسول الله \u200Fﷺ \u200F:\n\"\u200F إنَّ اللهَ لَا يَنْظُرُ إلَى أجْسَامِكُمْ ، وَلَا إلى صُوَرِكُمُ، وَلَكِن يَنْظُرُ إلَى قُلُوبِكُمْ وَأَعْمَالِكُم",
        reference = "(صحيح مُسلم)",
        arabicTitle = "...",
        englishReference = "Abu Hurairah (May Allah be pleased with him) narrated:\n The Messenger of Allah (ﷺ) said, \"Allah does not look at your figures, nor at your faces but He looks at your hearts and deeds\".",
        englishTranslation = "(Sahih al-Muslim)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level6_dua4,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 6
    ),
    Hadith(
        Duaheading = "The Strong Believer",
        arabic = "وعن أبي هريرة رضي الله عنه قَالَ: قَالَ رَسُولُ اللَّهِ ﷺ :\n اَلْمُؤْمِنُ اَلْقَوِيُّ خَيْرٌ وَأَحَبُّ إِلَى اللَّهِ مِنْ الْمُؤْمِنِ الضَّعِيفِ, وَفِي كُلٍّ خَيْرٌ",
        reference = "(صحيح مُسلم)",
        arabicTitle = "...",
        englishReference = "Abu Hurairah رضى الله عنه narrated that the Messenger of Allah (ﷺ) said:\nThe strong believer is better and more beloved to Allah than the weak believer, though there is goodness in both\n" +
                "1.    To testify that none has the right to be worshipped but Allah and Muhammad (ﷺ)is the Messenger of Allah.\n" +
                "2.    To establish the prayer.\n" +
                "3.    To give Zakat (obligatory charity).\n" +
                "4.    To perform Hajj (Pilgrimage to Mecca)\n" +
                "To observe fast during the month of Ramadan",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level6_dua5,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 6
    ),

    Hadith(
        Duaheading = "Gratitude: People & Allah",
        arabic = "عَنْ أَبِي هُرَيْرَةَ رضى الله عنه ، عَنِ النَّبِيِّ  ﷺ:\nلاَ يَشْكُرُ اللَّهَ مَنْ لاَ يَشْكُرُ النَّاسَ",
        reference = "(سنن أبي داود\u200F)",
        arabicTitle = "...",
        englishReference = "Narrated Abu Hurairah: رضى الله عنه\nThe Prophet (ﷺ) said: \"He who does not thank the people is not thankful to Allah.",
        englishTranslation = "(Sunan Abi Dawood)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level6_dua6,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 6
    ),
    Hadith(
        Duaheading = "Speak Good or Be Silent",
        arabic = "وعن أبي هريرة  رضي الله عنه   عَنْهُ أَنَّ رَسُولَ اللَّهِ  ﷺ قَالَ \u200F:\nمَنْ كَانَ يُؤْمِنُ بِاللَّهِ وَالْيَوْمِ الْآخِرِ فَلْيَقُلْ خَيْرًا أَوْ لِيَصْمُتْ",
        reference = "(رَوَاهُ الْبُخَارِيُ وَمُسْلِمٌ)",
        arabicTitle = "...",
        englishReference = "Abu Hurairah رضى الله عنه narrated that the Messenger of Allah (ﷺ) said:\nHe who believes in Allah and the Last Day, should speak good or remain silent",
        englishTranslation = "(Al-Bukhari and Al-Muslim)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level6_dua7,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 6
    ),
    Hadith(
        Duaheading = "Characteristics of a Muslim",
        arabic = "عن عبد الله بن عمرو بن العاص رضي الله عنهما عن النبي ﷺ قال:\nالْمُسْلِمُ مَنْ سَلِمَ الْمُسْلِمُونَ مِنْ لِسَانِهِ وَيَدِهِ، وَالْمُهَاجِرُ مَنْ هَجَرَ مَا نَهَى اللَّهُ عَنْهُ",
        reference = "( مُتَّفَقٌ عَلَيْهِ)",
        arabicTitle = "...",
        englishReference = "'Abdullah bin 'Amr bin Al-'as (May Allah be pleased with them) reported: The Prophet (ﷺ) said\nA Muslim is the one from whose tongue and hand the other Muslims are safe; and a Muhajir (Emigrant) is the one who refrains from what Allah has forbidden",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level6_dua8,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 6
    ),
    Hadith(
        Duaheading = "Removing Harm is Charity",
        arabic = "وَعَنْ أَبِي هُرَيْرَةَ رَضِيَ اللَّهُ عَنْهُ قَالَ: قَالَ رَسُولُ اللَّهِ صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ \u200F:\nيُمِيطُ الأَذَى عَنِ الطَّرِيقِ صَدَقَةٌ",
        reference = "(رواه البخاري)",
        arabicTitle = "...",
        englishReference = "Abu Hurairah (May Allah be pleased with him) reported: The Messenger of Allah (ﷺ) said, \nRemoving harmful thing from the way is an act of charity",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level6_dua9,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 6
    ),
    // Level 7: Duas 40–47

    Hadith(
        Duaheading = "The Treasure of Paradise",
        arabic = "عَنْ أَبِي هُرَيْرَةَ رضى الله عنه قَالَ قَالَ لِي رَسُولُ اللَّهِ  ﷺ:\nأَكْثِرْ مِنْ قَوْلِ لاَ حَوْلَ وَلاَ قُوَّةَ إِلاَّ بِاللَّهِ فَإِنَّهَا كَنْزٌ مِنْ كُنُوزِ الْجَنَّةِ",
        reference = "(رواه الترمذي\u200F)",
        arabicTitle = "...",
        englishReference = "Abu Hurairah (May Allah be pleased with him) said: “The Messenger of Allah (ﷺ) said to me:\n Be frequent in saying: “There is no might or power except by Allah, (Lā ḥawla wa lā quwwata illā billāh).” For verily, it is a treasure from the treasures of Paradise.\"",
        englishTranslation = "(Al-Tirmidhi)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level7_dua1,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 7
    ),
    Hadith(
        Duaheading = "Prayer & Al-Fatiha",
        arabic = "وَعَنْ عُبَادَةَ بْنِ الصَّامِتِ \u200F رضى الله عنه \u200F- قَالَ : قَالَ رَسُولُ اَللَّهِ  ﷺ \u200F:\nلَا صَلَاةَ لِمَنْ لَمْ يَقْرَأْ بِأُمِّ الْقُرْآنِ",
        reference = "(مُتَّفَقٌ عَلَيْه)",
        arabicTitle = "...",
        englishReference = "Narrated 'Ubada bin As-Samit رضى الله عنه: The Messenger of Allah (ﷺ) said:\nThere is no Salat (prayer) for one who does not recite Ummul-Qur'an (Surat al-Fatiha",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level7_dua2,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 7
    ),
    Hadith(
        Duaheading = "Yawning and Its Etiquettes",
        arabic = "عَنْ أَبِيْ هُرَيْرَةَ رضى الله عنه أَنَّ النَّبِيَّ ﷺ قال:\nاَلتَّثَاؤُبُ مِنَ الشَّيْطَانِ فَإِذَا تَثَاءَبَ أَحَدُكُمْ  فَالْيَكْظِمْ مَا اسْتَطَاعَ",
        reference = "(صحيح مُسلم)",
        arabicTitle = "...",
        englishReference = "Narrated Abu Hurairah (May Allah be pleased with him):\n" +
                "The Prophet (ﷺ) said:\nYawning is from the devil, so when one of you yawns, he should suppress it as much as he can",
        englishTranslation = "(Sahih al-Muslim)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level7_dua3,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 7
    ),
    Hadith(
        Duaheading = "How to Avoid Hellfire?",
        arabic = "وعن عدى بن حاتم رضى الله عنه أن  رَسُوْلُ اللهِ  ﷺ   قَالَ:\nاِتَّقُوا النَّارَ وَلَوْ بِشِقِّ تَمْرَةٍ فَمَنْ لَمْ يَجِدْ فَبِكَلِمَةٍ طَيِّبَةٍ",
        reference = "(مُتَّفَقٌ عَلَيْه\u200F)",
        arabicTitle = "...",
        englishReference = "Adi bin Hatim (May Allah be pleased with him) reported:\nThe Messenger of Allah (ﷺ) said:    \nProtect yourself from Hellfire, even if it is by giving half a date in charity. If you cannot find that, then with a kind word",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level7_dua4,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 7
    ),
    Hadith(
        Duaheading = "Etiquettes of Greeting",
        arabic = "عَنْ أَبِي هُرَيْرَةَ رضى الله عنه ، عَنِ النَّبِيّ  ﷺ \u200F قَالَ :\nيُسَلِّمُ الصَّغِيرُ عَلَى الْكَبِيرِ، وَالْمَارُّ عَلَى الْقَاعِدِ، وَالْقَلِيلُ عَلَى الْكَثِيرِ",
        reference = "(صحيح البخاري)",
        arabicTitle = "...",
        englishReference = "Narrated Abu Huraira (May Allah be pleased with him): The Prophet (ﷺ) said:\nThe young should greet the old, the passerby should greet the sitting one, and the smaller group of persons should greet the large group of persons.",
        englishTranslation = "(Sahih al-Bukhari)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level7_dua5,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 7
    ),
    Hadith(
        Duaheading = "Etiquettes of Drinking",
        arabic = "وَعَنْ أَبِي قَتَادَةَ \u200F رضى الله عنه أَنَّ النَّبِيَّ ﷺ قال:\nإِذَا شَرِبَ أَحَدُكُمْ , فَلَا يَتَنَفَّسْ فِي الْإِنَاءِ",
        reference = "(مُتَّفَقٌ عَلَيْهِ)",
        arabicTitle = "...",
        englishReference = "The Prophet (ﷺ) said:\nWhen one of you drinks, he should not breathe into the vessel",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level7_dua6,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 7
    ),
    Hadith(
        Duaheading = "Right-Side Sunnah ﷺ",
        arabic = "وعن عدى بن حاتم رضى الله عنه أن  رَسُوْلُ اللهِ  ﷺ   قَالَ:\nكَانَ النَّبِيُّ صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ يُحِبُّ التَّيَمُّنَ مَا اسْتَطَاعَ فِي شَأْنِهِ كُلِّهِ: فِي طُهُورِهِ وَتَرَجُّلِهِ و تَنَعُّلِهِ",
        reference = "(مُتَّفَقٌ عَلَيْه\u200F)",
        arabicTitle = "...",
        englishReference = "Narrated 'A’isha (May Allah be pleased with her) :\nThe Prophet (ﷺ) liked to begin with the right as much as possible in all his affairs, in his purification, combing his hair, and putting on his sandals",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level7_dua7,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 7
    ),
    Hadith(
        Duaheading = "Planting  a Tree is Charity",
        arabic = "وَعَنْ أَنَسٍ رضى الله عنه قَالَ: قَالَ رَسُولُ اللَّهِ  ﷺ \u200F :\nمَا مِنْ مُسْلِمٍ يَغْرِسُ غَرْسًا أَوْ يَزْرَعُ زَرْعًا فَيَأْكُلُ مِنْهُ إِنْسَانٌ أَوْ طَيْرٌ أَوْ بَهِيمَةٌ إِلَّا كَانَت لَهُ صَدَقَة",
        reference = "(( مُتَّفَقٌ عَلَيْهِ)",
        arabicTitle = "...",
        englishReference = "Narrated Anas (May Allah be pleased with him) said: The Messenger of Allah (ﷺ) said:\nIf any Muslim plants a tree or sows seed, and a human, bird, or animal eats from it, it will be counted as charity for him",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level7_dua8,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 7
    ),
    Hadith(
        Duaheading = "Truthfulness & Lying",
        arabic = " قَالَ رَسُولُ اللَّهِ  ﷺ  \u200F :\nإِنَّ الصِّدْقَ يَهْدِي إِلَى الْبِرِّ، وَإِنَّ الْبِرَّ يَهْدِي إِلَى الْجَنَّةِ، وَإِنَّ الرَّجُلَ لَيَصْدُقُ حَتَّى يَكُونَ صِدِّيقًا، وَإِنَّ الْكَذِبَ يَهْدِي إِلَى الْفُجُورِ، وَإِنَّ الْفُجُورَ يَهْدِي إِلَى النَّارِ، وَإِنَّ الرَّجُلَ لَيَكْذِبُ، حَتَّى يُكْتَبَ عِنْدَ اللَّهِ كَذَّابًا",
        reference = "(رواه البخاري)",
        arabicTitle = "...",
        englishReference = "The Prophet (ﷺ) said,\n \"Truthfulness leads to righteousness, and righteousness leads to Paradise. And a man keeps on telling the truth until he becomes a truthful person. Lying leads to Al-Fajur (i.e. wickedness, evil-doing), and Al-Fajur (wickedness) leads to the (Hell) Fire, and a man may keep on telling lies till he is written before Allah, a liar.\"",
        englishTranslation = "(Agreed upon)",
        audioUrl = R.raw.l03_h07_title_en,
        backgroundUrl = null,
        duaEnglishTitle = R.raw.l03_h07_title_en,
        icon = R.drawable.ic_level7_dua9,
        duaArabicTitle= R.raw.l01_h01_en_title,

        level = 7
    ),
)