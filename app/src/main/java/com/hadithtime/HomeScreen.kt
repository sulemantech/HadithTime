//package com.hadithtime
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.SideEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.google.accompanist.systemuicontroller.rememberSystemUiController
//
//@Composable
//fun HomeScreen(navController: NavController) {
//
//    val systemUiController = rememberSystemUiController()
//    val navigationBarColor = colorResource(id = R.color.white)
//    val statusBarColor = colorResource(id = R.color.level_one_color)
//    SideEffect {
//        systemUiController.setStatusBarColor(color = statusBarColor)
//        systemUiController.setNavigationBarColor(color = navigationBarColor)
//    }
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(
//                text = "Home",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            val levels = listOf(
//                "Level 1" to "titleScreenLevel1/1/0",
//                "Level 2" to "titleScreenLevel2/2/0",
//                "Level 3" to "titleScreenLevel3/3/0",
//                "Level 4" to "titleScreenLevel4/4/0",
//                "Level 5" to "titleScreenLevel5/5/0",
//                "Level 6" to "titleScreenLevel6/6/0",
//                "Level 7" to "titleScreenLevel7/7/0"
//            )
//
//            levels.forEach { (label, route) ->
//                Button(
//                    onClick = { navController.navigate(route) },
//                    modifier = Modifier
//                        .fillMaxWidth(0.7f)
//                        .padding(vertical = 4.dp)
//                ) {
//                    Text(text = label)
//                }
//            }
//        }
//    }
//}
