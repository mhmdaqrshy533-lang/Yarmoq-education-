package com.example

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.YarmoukAppTheme
import com.example.ui.theme.glassMorphism
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YarmoukAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") { GlassDashboardScreen(navController) }
                        composable("students") { MonthlyGradesScreen(navController) }
                        composable("students_list") { StudentsListScreen(navController) }
                        composable("progress_card") { ProgressCardScreen(navController) }
                        // Other screens would be added here
                    }
                }
            }
        }
    }
}

@Composable
fun GlassDashboardScreen(navController: androidx.navigation.NavController) {
    // Dynamic animated background blobs
    val infiniteTransition = rememberInfiniteTransition(label = "bg_anim")
    val xOffset1 by infiniteTransition.animateFloat(
        initialValue = -200f, targetValue = 400f,
        animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing), RepeatMode.Reverse),
        label = "x1"
    )
    val yOffset1 by infiniteTransition.animateFloat(
        initialValue = -100f, targetValue = 600f,
        animationSpec = infiniteRepeatable(tween(8000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "y1"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Starry/Dark background base
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F172A), Color(0xFF020617)) // Slate 900 to 950
                )
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF7E22CE).copy(alpha = 0.5f), Color.Transparent),
                    center = Offset(xOffset1, yOffset1), radius = 300f
                ),
                radius = 300f, center = Offset(xOffset1, yOffset1)
            )
        }

        Box(modifier = Modifier.fillMaxSize().then(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) Modifier.blur(40.dp) else Modifier))

        // Content Layer
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Header (Neumorphism / Glassmorphism)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .glassMorphism(intensity = 0.6f)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("مدرسة اليرموك النموذجية", fontSize = 14.sp, color = Color.White.copy(alpha=0.7f))
                    Text("المحرر المدرسي الشامل", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("إصدار 40.1 Pro - يرموك", fontSize = 12.sp, color = Color(0xFFC084FC))
                }
                Box(
                    modifier = Modifier.size(52.dp).glassMorphism(cornerRadius = 100.dp, intensity = 1.5f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.School, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .glassMorphism(intensity = 0.4f)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = {}) { Icon(Icons.Default.PlayArrow, null, tint = Color(0xFFF43F5E)) }
                IconButton(onClick = {}) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color(0xFF3B82F6)) }
                IconButton(onClick = {}) { Icon(Icons.Default.Phone, null, tint = Color(0xFF10B981)) }
                IconButton(onClick = {}) { Icon(Icons.AutoMirrored.Filled.Help, null, tint = Color.White) }
                IconButton(onClick = {}) { Icon(Icons.Default.Settings, null, tint = Color.White) }
                IconButton(onClick = {}) { Icon(Icons.Default.Lock, null, tint = Color.White) }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("الأقسام الرئيسية", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))

            // Modules Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                val menuItems = listOf(
                    MenuAction("المحصلات الشهرية", Icons.Default.DateRange, Color(0xFF1E3A8A), "students"),
                    MenuAction("محرر أتمتة", Icons.Default.SmartToy, Color(0xFF3B82F6), ""),
                    MenuAction("محرر إمتحانات", Icons.Default.Quiz, Color(0xFFF59E0B), ""),
                    MenuAction("الخطة اليومية", Icons.Default.Today, Color(0xFF10B981), ""),
                    MenuAction("الخطة الفصلية", Icons.Default.CalendarMonth, Color(0xFF4C1D95), ""),
                    MenuAction("غياب الطلاب", Icons.Default.NoAccounts, Color(0xFFEC4899), ""),
                    MenuAction("بطاقات التقدم", Icons.Default.AssignmentInd, Color(0xFF0EA5E9), "progress_card"),
                    MenuAction("بطائق طلابية", Icons.Default.Badge, Color(0xFF7E22CE), "students_list"),
                    MenuAction("النتائج النهائية", Icons.Default.WorkspacePremium, Color(0xFFBE123C), ""),
                    MenuAction("مذكرات رسمية", Icons.Default.Description, Color(0xFF64748B), ""),
                    MenuAction("محرر الكتب", Icons.Default.MenuBook, Color(0xFFF43F5E), ""),
                    MenuAction("الشهائد", Icons.Default.CardMembership, Color(0xFF0D9488), ""),
                    MenuAction("إضافي", Icons.Default.AddCircle, Color(0xFFb45309), ""),
                    MenuAction("مدارس اخرى", Icons.Default.Domain, Color(0xFF1D4ED8), ""),
                    MenuAction("المحاسب المدرسي", Icons.Default.Calculate, Color(0xFF047857), "")
                )
                
                items(menuItems.size) { index ->
                    val action = menuItems[index]
                    var isPressed by remember { mutableStateOf(false) }
                    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)
                    
                    Box(
                        modifier = Modifier
                            .aspectRatio(0.85f)
                            .graphicsLayer(scaleX = scale, scaleY = scale)
                            .glassMorphism(cornerRadius = 20.dp, intensity = 0.9f)
                            .clickable { 
                                isPressed = !isPressed 
                                if(action.route.isNotEmpty()) navController.navigate(action.route) 
                            }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(Brush.radialGradient(listOf(action.color, action.color.copy(alpha = 0.5f))), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(action.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(action.title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 2, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}

data class MenuAction(val title: String, val icon: ImageVector, val color: Color, val route: String)

