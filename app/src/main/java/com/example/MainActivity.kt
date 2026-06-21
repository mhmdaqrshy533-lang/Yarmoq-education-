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
    val viewModel: com.example.ui.YarmoukViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                onNavigate = { route -> 
                     navController.navigate(route)
                }
            )
        }
        composable("grading") {
            GradingScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable("attendance") {
            AttendanceScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable("exams") {
            com.example.ui.features.core.ExamsScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable("bubblesheet") {
            com.example.ui.features.core.BubbleSheetScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable("semester_plan") {
            com.example.ui.features.core.PlansScreen(viewModel, isDaily = false, onNavigateBack = { navController.popBackStack() })
        }
        composable("daily_plan") {
            com.example.ui.features.core.PlansScreen(viewModel, isDaily = true, onNavigateBack = { navController.popBackStack() })
        }
        composable("results") {
            com.example.ui.features.core.ResultsScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable("cards") {
            com.example.ui.features.core.StudentCardsScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable("certificates") {
            com.example.ui.features.certificates.CertificateBuilderScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable("schools") {
            com.example.ui.features.core.SchoolsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("memos") {
            com.example.ui.features.core.MemosScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable("accountant") {
            com.example.ui.features.core.AccountantScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable("school_fingerprint") {
            com.example.ui.features.core.SchoolFingerprintScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("student_portfolio") {
            com.example.ui.features.core.StudentPortfolioScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable("schedule") {
            com.example.ui.features.core.ScheduleScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}

