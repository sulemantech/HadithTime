package com.hadithtime.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hadithtime.R
import com.hadithtime.model.Hadith

@Composable
fun TopBar(
    dua: Hadith,
    onSettingsClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    val MyHeadingFont = FontFamily(Font(R.font.fredoka_semibold))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp, start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Home Icon
        IconButton(
            onClick = { onHomeClick() },
            modifier = Modifier.size(56.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_icon),
                contentDescription = "Home",
                modifier = Modifier.size(42.dp)
            )
        }

        // Title centered
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            dua.Duaheading?.let {
                Text(
                    text = it,
                    fontSize = 22.sp,
                    fontFamily = MyHeadingFont,
                    textAlign = TextAlign.Center,
                   // fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

        }

        // Settings Icon
        IconButton(
            onClick = { onSettingsClick() },
            modifier = Modifier.size(56.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.setting_icon),
                contentDescription = "Settings",
                modifier = Modifier.size(42.dp)
            )
        }
    }
}
