//package com.hadithtime.components
//
//import com.hadithtime.R
//
//object DuaRepository {
//    val duaList = listOf(
//        Dua(
//            id = 1,
//            title = "Cleanliness Dua",
//            titleBgRes = R.drawable.title_bg1,
//            girlImageRes = R.drawable.girl_image,
//            bubbleImageRes = R.drawable.bubble_image,
//            mainBgRes = R.drawable.dua1,
//            contentText = "اللهم طهر قلبي من النفاق",
//            audioResId = R.raw.cleanliness_audio
//        ),
//        Dua(
//            id = 2,
//            title = "Before Eating",
//            titleBgRes = R.drawable.title_bg2,
//            girlImageRes = R.drawable.girl_eating,
//            bubbleImageRes = R.drawable.bubble_eating,
//            mainBgRes = R.drawable.eating_bg,
//            contentText = "بِسْمِ اللَّهِ وَعَلَى بَرَكَةِ اللَّهِ",
//            audioResId = R.raw.before_eating_audio
//        )
//    )
//}
//fun getEnglishTranslation(duaId: Int): String {
//    return when (duaId) {
//        1 -> "O Allah, purify my heart from hypocrisy."
//        2 -> "In the name of Allah and with the blessings of Allah."
//        else -> ""
//    }
//}
//
//fun getHadithReference(duaId: Int): String {
//    return when (duaId) {
//        1 -> "(Sahih al-Bukhari)"
//        2 -> "(Sahih Muslim)"
//        else -> ""
//    }
//}
