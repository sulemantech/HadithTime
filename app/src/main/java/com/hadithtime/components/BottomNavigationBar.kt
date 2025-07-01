package com.hadithtime.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hadithtime.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    val MyCountFont = FontFamily(Font(R.font.fredoka_regular))
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val context = LocalContext.current

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
        // Home
        BottomNavigationItem(
            selected = currentDestination == "HadithDashboardScreen",
            onClick = { navController.navigate("HadithDashboardScreen") },
            icon = {
                Box(
                    modifier = if (currentDestination == "HadithDashboardScreen") Modifier
                        .size(36.dp)
                        .background(
                            color = colorResource(id = R.color.dashboard_color_bg),
                            shape = CircleShape
                        )
                    else Modifier
                ) {
                    Icon(
                        painterResource(
                            id = if (currentDestination == "HadithDashboardScreen") R.drawable.ic_home_inacitve else R.drawable.ic_home_active
                        ),
                        contentDescription = "HadithDashboardScreen",
                        modifier = Modifier.align(Alignment.Center),
                        tint = if (currentDestination == "HadithDashboardScreen")
                            colorResource(id = R.color.dashboard_color)
                        else
                            Color.Unspecified
                    )
                }
            },
            label = {
                Text(
                    "Home",
                    color = if (currentDestination == "HadithDashboardScreen")
                        colorResource(R.color.dashboard_color)
                    else
                        Color.Black,
                    fontFamily = MyCountFont
                )
            }
        )

        // Favorite
        BottomNavigationItem(
            selected = currentDestination == "FavoriteScreen",
            onClick = {
                navController.navigate("LearningTrackerScreen") {
                    launchSingleTop = true // Avoid duplicate screens in the stack
                    popUpTo("HadithDashboardScreen") { // Pop everything back to Home screen
                        inclusive = false // Home ko pop na karein, wahan wapas aye
                    }
                }
            },
            icon = {
                Box(
                    modifier = if (currentDestination == "FavoriteScreen") Modifier
                        .size(36.dp)
                        .background(
                            color = colorResource(id = R.color.dashboard_color_bg),
                            shape = CircleShape
                        )
                    else Modifier
                ) {
                    Icon(
                        painterResource(
                            id = if (currentDestination == "FavoriteScreen") R.drawable.ic_fav_star_active else R.drawable.ic_fav_star
                        ),
                        contentDescription = "FavoriteScreen",
                        modifier = Modifier.align(Alignment.Center),
                        tint = if (currentDestination == "FavoriteScreen")
                            colorResource(id = R.color.dashboard_color)
                        else
                            Color.Unspecified
                    )
                }
            },
            label = {
                Text(
                    "Favorite",
                    color = if (currentDestination == "FavoriteScreen")
                        colorResource(R.color.dashboard_color)
                    else
                        Color.Black,
                    fontFamily = MyCountFont
                )
            }
        )

        // Settings
        BottomNavigationItem(
            selected = currentDestination == "settings",
            onClick = {
                navController.navigate("settings") {
                    launchSingleTop = true
                    popUpTo("HadithDashboardScreen") {
                        inclusive = false
                    }
                }
            },
            icon = {
                Box(
                    modifier = if (currentDestination == "settings") Modifier
                        .size(36.dp)
                        .background(
                            color = colorResource(id = R.color.dashboard_color_bg),
                            shape = CircleShape
                        )
                    else Modifier
                ) {
                    Icon(
                        painterResource(
                            id = if (currentDestination == "settings") R.drawable.ic_setting_active else R.drawable.ic_setting_inactive
                        ),
                        contentDescription = "Settings",
                        modifier = Modifier.align(Alignment.Center),
                        tint = if (currentDestination == "settings")
                            colorResource(id = R.color.dashboard_color)
                        else
                            Color.Unspecified
                    )
                }
            },
            label = {
                Text(
                    "Settings",
                    color = if (currentDestination == "settings")
                        colorResource(R.color.dashboard_color)
                    else
                        Color.Black,
                    fontFamily = MyCountFont
                )
            }
        )

        var selectedTab by remember { mutableStateOf("home") } // Or whichever is your default tab

        BottomNavigationItem(
            selected = selectedTab == "share",
            onClick = {
                selectedTab = "share" // Update selected tab

                // Launch share intent
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out this amazing Hadith app: https://play.google.com/store/apps/details?id=${context.packageName}"
                    )
                }
                val chooser = Intent.createChooser(shareIntent, "Share App")
                context.startActivity(chooser)
            },
            icon = {
                Icon(
                    painterResource(
                        id = if (selectedTab == "share") R.drawable.ic_share_filled else R.drawable.ic_share_unactive
                    ),
                    contentDescription = "Share"
                )
            },
            label = {
                Text(
                    "Share",
                    color = if (selectedTab == "share")
                        colorResource(R.color.dashboard_color)
                    else
                        Color.Black,
                    fontFamily = MyCountFont
                )
            }
        )


        val showDialog = remember { mutableStateOf(false) }

        // Info
        BottomNavigationItem(
            selected = currentDestination == "info",
            onClick = {
                showDialog.value = true
            },
            icon = {
                Box(
                    modifier = if (currentDestination == "info") Modifier
                        .size(36.dp)
                        .background(
                            color = colorResource(id = R.color.dashboard_color_bg),
                            shape = CircleShape
                        )
                    else Modifier
                ) {
                    Icon(
                        painterResource(
                            id = if (currentDestination == "info") R.drawable.ic_setting_active else R.drawable.ic_setting_inactive
                        ),
                        contentDescription = "info",
                        modifier = Modifier.align(Alignment.Center),
                        tint = if (currentDestination == "info")
                            colorResource(id = R.color.dashboard_color)
                        else
                            Color.Unspecified
                    )
                }
            },
            label = {
                Text(
                    "info",
                    color = if (currentDestination == "info")
                        colorResource(R.color.dashboard_color)
                    else
                        Color.Black,
                    fontFamily = MyCountFont
                )
            }
        )

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Text(text = "Information")
                },
                text = {
                    Text("This is some information about the app.\nYou can customize this message.")
                },
                confirmButton = {
                    TextButton(onClick = { showDialog.value = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
