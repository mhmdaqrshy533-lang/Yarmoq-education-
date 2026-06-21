package com.example.ui.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class DashboardItem(
    val title: String,
    val icon: ImageVector,
    val iconTint: Color,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        DashboardItem("محرر الامتحانات\nالذكي", Icons.AutoMirrored.Filled.MenuBook, Color(0xFFFFB300), "exams"),
        DashboardItem("محرر الأتمتة", Icons.Filled.Gradient, Color(0xFF66BB6A), "bubblesheet"),
        DashboardItem("سجل الدرجات\nالرقمي", Icons.Filled.ListAlt, Color(0xFF26C6DA), "grading"),
        
        DashboardItem("الخطة الفصلية", Icons.Filled.CalendarMonth, Color(0xFFEF5350), "semester_plan"),
        DashboardItem("الخطة اليومية", Icons.AutoMirrored.Filled.Assignment, Color(0xFF42A5F5), "daily_plan"),
        DashboardItem("غياب الطلاب/\nالمعلمين", Icons.Filled.FactCheck, Color(0xFFD4E157), "attendance"),
        
        DashboardItem("النتائج والمحصلات", Icons.Filled.Verified, Color(0xFFFFCA28), "results"),
        DashboardItem("بطاقات طلابية", Icons.Filled.Badge, Color(0xFF90CAF9), "cards"),
        DashboardItem("منشئ الشهائد", Icons.Filled.EmojiEvents, Color(0xFFFFCA28), "certificates"),
        
        DashboardItem("إدارة المدارس", Icons.Filled.AccountBalance, Color(0xFFEF5350), "schools"),
        DashboardItem("أدوات المساعد", Icons.Filled.Calculate, Color(0xFF42A5F5), "memos"),
        DashboardItem("المحاسب المدرسي", Icons.Filled.Paid, Color(0xFF66BB6A), "accountant")
    )

    var selectedTab by remember { mutableIntStateOf(0) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            containerColor = YarmoukBg,
            topBar = {
                TopAppBar(
                    title = { 
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.AccountBalance, contentDescription = "Logo", tint = Color.White, modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    "يرموك", 
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = Color.White),
                                    modifier = Modifier.offset(y = 2.dp)
                                )
                                Text(
                                    "إدارة المدارس والتعليم الرقمي", 
                                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.8f))
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Notifications */ }) {
                            Icon(Icons.Filled.NotificationsNone, contentDescription = "Notifications", tint = Color.White)
                        }
                        IconButton(onClick = { /* Settings */ }) {
                            Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = YarmoukHeaderBlue,
                    )
                )
            },
            bottomBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    NavigationBar(
                        containerColor = YarmoukBottomNav,
                        contentColor = Color.White
                    ) {
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.Dashboard, contentDescription = "Dashboard") },
                            label = { Text("Dashboard") },
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = YarmoukHeaderBlue,
                                selectedTextColor = YarmoukHeaderBlue,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.White
                            )
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.PeopleAlt, contentDescription = "Students") },
                            label = { Text("Students") },
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = YarmoukHeaderBlue,
                                selectedTextColor = YarmoukHeaderBlue,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.White
                            )
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.BarChart, contentDescription = "Reports") },
                            label = { Text("Reports") },
                            selected = selectedTab == 2,
                            onClick = { selectedTab = 2 },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = YarmoukHeaderBlue,
                                selectedTextColor = YarmoukHeaderBlue,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.White
                            )
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.Build, contentDescription = "Tools") },
                            label = { Text("Tools") },
                            selected = selectedTab == 3,
                            onClick = { selectedTab = 3 },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = YarmoukHeaderBlue,
                                selectedTextColor = YarmoukHeaderBlue,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.White
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // User Welcome Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Text(
                            text = "Welcome, Mr. Suhail Al-Huzbari!",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Text(
                            text = "Yaqúm AI | (Offline Mode)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // High-Density Unified Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(items) { item ->
                        DashboardCard(item) {
                            onNavigate(item.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardCard(item: DashboardItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Ensure square cards
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = YarmoukCardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp), // Less padding to fit tightly like the image
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                item.icon,
                contentDescription = item.title,
                tint = item.iconTint,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimaryLight,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}

