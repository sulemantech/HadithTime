package com.hadithtime
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hadithtime.model.Hadith

@Composable
fun LearningTrackerScreen(
    navController: NavController,
    viewModel: HadithViewModel = androidx.lifecycle.viewmodel.compose.viewModel() // ✔️ correct way
) {
    val filteredDuas by viewModel.filteredDuas.collectAsState()
    val selectedLevel by viewModel.selectedLevel.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.preloadDuas()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        Text(
            text = "Learning Tracker",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            textAlign = TextAlign.Center
        )
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Filter Tabs Row (not connected yet; just UI for now)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(top = 18.dp)
        ) {
            FilterChip("All", selected = true)
            FilterChip("Memorized", selected = false)
            FilterChip("Favorite", selected = false)
            FilterChip("In Progress", selected = false)
        }

        // Select Level Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Select Level", fontWeight = FontWeight.SemiBold)
            var selectedLevels by remember { mutableStateOf<List<Int>>(emptyList()) }

            SelectLevelDropdown(
                selectedLevels = selectedLevels,
                onLevelsSelected = { levels ->
                    selectedLevels = levels
                    // Update VM or filter list accordingly
                }
            )
        }

        Divider()

        // List of Hadith Cards
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 15.dp)
        ) {
            items(filteredDuas) { hadith ->
                HadithCard(hadith)
            }
        }
    }
}

@Composable
fun FilterChip(label: String, selected: Boolean) {
    //val backgroundColor = if (selected) colorResource(R.color.filter_color) else colorResource(R.color.filter_color_bg)
    val textColor = if (selected) Color.White else Color.Black
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
          //  .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable { /* handle click */ }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = label, color = textColor)
    }
}

@Composable
fun SelectLevelDropdown(
    selectedLevels: List<Int>,
    onLevelsSelected: (List<Int>) -> Unit
) {
    var dialogOpen by remember { mutableStateOf(false) }
    val displayedText = if (selectedLevels.isEmpty()) "Select" else selectedLevels.joinToString(", ") { "$it" }
    val currentSelection = remember { mutableStateListOf<Int>().apply { addAll(selectedLevels) } }

    Box {
        Button(
            onClick = { dialogOpen = true },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.filter_color)),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = displayedText, color = Color.White)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
        }
    }

    if (dialogOpen) {
        AlertDialog(
            onDismissRequest = { dialogOpen = false },
            confirmButton = {
                TextButton(onClick = {
                    dialogOpen = false
                    onLevelsSelected(currentSelection.toList()) // pass levels back
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { dialogOpen = false }) {
                    Text("Cancel")
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share_filled),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select Level")
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                       // .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (row in 0 until 3) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (col in 1..3) {
                                val level = row * 3 + col
                                if (level <= 7) {
                                    FilterChip(
                                        label = "Level $level",
                                        selected = level in currentSelection,
                                        onClick = {
                                            if (currentSelection.contains(level)) {
                                                currentSelection.remove(level) // unselect
                                            } else {
                                                currentSelection.add(level)    // select
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
        )
    }
}

@Composable
fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (selected) Color(0xFF1E88E5) else Color.LightGray
    val textColor = if (selected) Color.White else Color.Black
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = label, color = textColor, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun HadithCard(hadith: Hadith) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp),
                   // .background(hadith.iconBackground, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = hadith.icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                hadith.englishReference?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
//            Box(
//                modifier = Modifier
//                    .width(4.dp)
//                    .height(40.dp)
//                    .background(hadith.statusColor, shape = RoundedCornerShape(2.dp))
//            )
        }
    }
}
