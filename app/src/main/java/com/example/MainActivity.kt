package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.features.dashboard.DashboardScreen
import com.example.ui.features.grading.GradingScreen
import com.example.ui.features.attendance.AttendanceScreen
import com.example.ui.features.common.PlaceholderScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    YarmoukApp()
                }
            }
        }
    }
}

@Composable
fun YarmoukApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                onNavigate = { route -> 
                     navController.navigate(route)
                }
            )
        }
        composable("grading") {
            GradingScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("attendance") {
            AttendanceScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("exams") {
            PlaceholderScreen(title = "محرر الامتحانات الذكي", onNavigateBack = { navController.popBackStack() })
        }
        composable("bubblesheet") {
            PlaceholderScreen(title = "محرر الأتمتة", onNavigateBack = { navController.popBackStack() })
        }
        composable("semester_plan") {
            PlaceholderScreen(title = "الخطة الفصلية", onNavigateBack = { navController.popBackStack() })
        }
        composable("daily_plan") {
            PlaceholderScreen(title = "الخطة اليومية", onNavigateBack = { navController.popBackStack() })
        }
        composable("results") {
            PlaceholderScreen(title = "النتائج والمحصلات", onNavigateBack = { navController.popBackStack() })
        }
        composable("cards") {
            PlaceholderScreen(title = "بطاقات طلابية", onNavigateBack = { navController.popBackStack() })
        }
        composable("certificates") {
            PlaceholderScreen(title = "منشئ الشهائد", onNavigateBack = { navController.popBackStack() })
        }
        composable("schools") {
            PlaceholderScreen(title = "إدارة المدارس", onNavigateBack = { navController.popBackStack() })
        }
        composable("tools") {
            PlaceholderScreen(title = "أدوات المساعد", onNavigateBack = { navController.popBackStack() })
        }
        composable("accountant") {
            PlaceholderScreen(title = "المحاسب المدرسي", onNavigateBack = { navController.popBackStack() })
        }
    }
}

