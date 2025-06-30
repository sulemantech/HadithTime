import com.hadithtime.R
import com.hadithtime.model.HadithCard
import com.hadithtime.model.HadithLevel

//package com.hadithtime.model

object HadithDataProvider {

    val levels = listOf(
        HadithLevel(
            "Level 1",
            listOf(
                HadithCard("Belief", R.drawable.icon_level1_dua1),
                HadithCard("Tawheed", R.drawable.icon_level1_dua2),
                HadithCard("Learning Quran", R.drawable.icon_level1_dua3),
                HadithCard("Cleanliness", R.drawable.home_icon)
            )
        ),
        HadithLevel(
            "Level 2",
            listOf(
                HadithCard("Cleanliness", R.drawable.icon_level2_dua1),
                HadithCard("Imitation of Sunnah", R.drawable.icon_level2_dua2),
                HadithCard("Prayers", R.drawable.icon_level2_dua3),
                HadithCard("Cleanliness", R.drawable.home_icon),
                HadithCard("Imitation of Sunnah", R.drawable.header_image),
            )
        ),
        HadithLevel(
            "Level 3",
            listOf(
                HadithCard("Obedience", R.drawable.icon_level3_dua1),
                HadithCard("Prayer Discipline", R.drawable.icon_level3_dua2),
                HadithCard("Gratitude", R.drawable.icon_level3_dua3),
                HadithCard("Forgiveness", R.drawable.icon_level3_dua4),
                HadithCard("Patience", R.drawable.icon_level3_dua5),
                HadithCard("Charity", R.drawable.icon_level3_dua6),
                HadithCard("Brotherhood", R.drawable.icon_level3_dua7)
            )
        ),
        HadithLevel(
            "Level 4",
            listOf(
                HadithCard("Cleanliness", R.drawable.icon_level3_dua1),
                HadithCard("Obedience", R.drawable.icon_level3_dua2),
                HadithCard("Prayers", R.drawable.icon_level3_dua3),
                HadithCard("Cleanliness", R.drawable.icon_level3_dua1),
                HadithCard("Imitation of Sunnah", R.drawable.icon_level3_dua2),
                HadithCard("Prayers", R.drawable.icon_level3_dua3),
                HadithCard("Prayers", R.drawable.icon_level3_dua3)
            )
        ),
        HadithLevel(
            "Level 5",
            listOf(
                HadithCard("Cleanliness", R.drawable.icon_level3_dua1),
                HadithCard("Obedience", R.drawable.icon_level3_dua2),
                HadithCard("Prayers", R.drawable.icon_level3_dua3),
                HadithCard("Cleanliness", R.drawable.icon_level3_dua1),
                HadithCard("Imitation of Sunnah", R.drawable.icon_level3_dua2),
                HadithCard("Prayers", R.drawable.icon_level3_dua3),
                HadithCard("Prayers", R.drawable.icon_level3_dua3),
                HadithCard("Prayers", R.drawable.icon_level3_dua3)
            )
        ),
        HadithLevel(
            "Level 6",
            listOf(
                HadithCard("Cleanliness", R.drawable.icon_level3_dua1),
                HadithCard("Obedience", R.drawable.icon_level3_dua2),
                HadithCard("Prayers", R.drawable.icon_level3_dua3),
                HadithCard("Cleanliness", R.drawable.icon_level3_dua1),
                HadithCard("Imitation of Sunnah", R.drawable.icon_level3_dua2),
                HadithCard("Prayers", R.drawable.icon_level3_dua3),
                HadithCard("Prayers", R.drawable.icon_level3_dua3),
                HadithCard("Prayers", R.drawable.home_icon)
            )
        ),
        HadithLevel(
            "Level 7",
            listOf(
                HadithCard("Cleanliness", R.drawable.icon_level3_dua1),
                HadithCard("Obedience", R.drawable.icon_level3_dua2),
                HadithCard("Prayers", R.drawable.icon_level3_dua3),
                HadithCard("Cleanliness", R.drawable.icon_level3_dua1),
                HadithCard("Imitation of Sunnah", R.drawable.icon_level3_dua2),
                HadithCard("Prayers", R.drawable.icon_level3_dua3),
                HadithCard("Prayers", R.drawable.home_icon),
                HadithCard("Prayers", R.drawable.home_icon)
            )
        )
    )
}
