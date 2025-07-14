package com.hadithtime.components

import android.graphics.Rect
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hadithtime.R

@Composable
fun LanguageDialog(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    selectedLanguage: String,
    onSelect: (String) -> Unit,
    anchorBounds: Rect? = null
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        offset = DpOffset(x = 0.dp, y = 4.dp),
        modifier = Modifier
            .width(200.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
    ) {
        LanguageOptionRow(
            icon = R.drawable.ic_english,
            label = "English",
            isSelected = selectedLanguage == "English",
            onClick = {
                onSelect("English")
                onDismissRequest()
            }
        )
        Divider(color = Color.LightGray)
        LanguageOptionRow(
            icon = R.drawable.ic_urdu,
            label = "Urdu",
            isSelected = selectedLanguage == "Urdu",
            onClick = {
                onSelect("Urdu")
                onDismissRequest()
            }
        )
    }
}

@Composable
fun LanguageOptionRow(
    icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    color = Color.Black
                )
                RadioButton(
                    selected = isSelected,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(R.color.filter_color),
                        unselectedColor = Color.Gray
                    )
                )
            }
        },
        onClick = onClick
    )
}
