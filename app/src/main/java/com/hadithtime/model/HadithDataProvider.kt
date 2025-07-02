package com.hadithtime.model

import com.hadithtime.R
import com.hadithtime.model.HadithCard
import com.hadithtime.model.HadithLevel

//package com.hadithtime.model

object HadithDataProvider {

    val levels = listOf(
        HadithLevel(
            "Level 1",
            listOf(
                HadithCard("Modesty", R.drawable.ic_level1_dua1),
                HadithCard("Faith", R.drawable.ic_level1_dua2),
                HadithCard("Qur'an", R.drawable.ic_level1_dua3),
                HadithCard("Cleanliness", R.drawable.ic_level1_dua4)
            )
        ),
        HadithLevel(
            "Level 2",
            listOf(
                HadithCard("Obedience", R.drawable.ic_level2_dua1),
                HadithCard("Prayer", R.drawable.ic_level2_dua2),
                HadithCard("Eating", R.drawable.ic_level2_dua3),
                HadithCard("Drinking", R.drawable.ic_level2_dua4),
                HadithCard("Speaking", R.drawable.ic_level2_dua5),
            )
        ),
        HadithLevel(
            "Level 3",
            listOf(
                HadithCard("Salam", R.drawable.ic_level3_dua1),
                HadithCard("Dhikr", R.drawable.ic_level3_dua2),
                HadithCard("Supplication", R.drawable.ic_level3_dua3),
                HadithCard("Mercy", R.drawable.ic_level3_dua4),
                HadithCard("Consciousness", R.drawable.ic_level3_dua5),
                HadithCard("Good Word", R.drawable.ic_level3_dua6),
                HadithCard("Companionship", R.drawable.ic_level3_dua7)
            )
        ),
        HadithLevel(
            "Level 4",
            listOf(
                HadithCard("Brotherhood", R.drawable.ic_level4_dua1),
                HadithCard("Gifts", R.drawable.ic_level4_dua2),
                HadithCard("Eating", R.drawable.ic_level4_dua3),
                HadithCard("Purity", R.drawable.ic_level4_dua4),
                HadithCard("Miswak", R.drawable.ic_level4_dua5),
                HadithCard("Ghusl", R.drawable.ic_level4_dua6),
                HadithCard("Gentleness", R.drawable.ic_level4_dua7)
            )
        ),
        HadithLevel(
            "Level 5",
            listOf(
                HadithCard("Adornment", R.drawable.ic_level5_dua1),
                HadithCard("Neighbor", R.drawable.ic_level5_dua2),
                HadithCard("Smile", R.drawable.ic_level5_dua3),
                HadithCard("Beautification", R.drawable.ic_level5_dua4),
                HadithCard("Tasbih", R.drawable.ic_level5_dua5),
                HadithCard("Salawat ﷺ", R.drawable.ic_level5_dua6),
                HadithCard("Pillars", R.drawable.ic_level5_dua7),
                HadithCard("Anger", R.drawable.ic_level5_dua8)
            )
        ),
        HadithLevel(
            "Level 6",
            listOf(
                HadithCard("Etiquettes ", R.drawable.ic_level6_dua1),
                HadithCard("Tasbih", R.drawable.ic_level6_dua2),
                HadithCard("Paradise", R.drawable.ic_level6_dua3),
                HadithCard("Sincerity", R.drawable.ic_level6_dua4),
                HadithCard("Believer", R.drawable.ic_level6_dua5),
                HadithCard("Gratitude", R.drawable.ic_level6_dua6),
                HadithCard("Silent", R.drawable.ic_level6_dua7),
                HadithCard("Akhlaq", R.drawable.ic_level6_dua8),
                HadithCard("Jannah", R.drawable.ic_level6_dua9),
            )
        ),
        HadithLevel(
            "Level 7",
            listOf(
                HadithCard("Relief", R.drawable.ic_level7_dua1),
                HadithCard("AlFatiha", R.drawable.ic_level7_dua2),
                HadithCard("Yawning", R.drawable.ic_level7_dua3),
                HadithCard("Hellfire", R.drawable.ic_level7_dua4),
                HadithCard("Greeting", R.drawable.ic_level7_dua5),
                HadithCard("Drinking", R.drawable.ic_level7_dua6),
                HadithCard("Rightness", R.drawable.ic_level7_dua7),
                HadithCard("Honesty", R.drawable.ic_level7_dua8),
                HadithCard("Planting", R.drawable.ic_level7_dua9),
            )
        )
    )
}
