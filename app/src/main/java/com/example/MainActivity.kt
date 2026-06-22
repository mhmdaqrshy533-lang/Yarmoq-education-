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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
            
            // Header
            Row(
                modifier = Modifier.fillMaxWidth().glassMorphism(intensity = 0.5f).padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("حقيبة المعلم العربي", fontSize = 14.sp, color = Color.White.copy(alpha=0.7f))
                    Text("المحرر المدرسي الشامل", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("للإصدار 40.1 Pro", fontSize = 12.sp, color = Color.White.copy(alpha=0.5f))
                }
                Box(
                    modifier = Modifier.size(48.dp).glassMorphism(cornerRadius = 100.dp, intensity = 1.5f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick actions
            Row(
                modifier = Modifier.fillMaxWidth().glassMorphism(intensity = 0.5f).padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.Red)
                Icon(Icons.Default.Send, contentDescription = null, tint = Color.Blue)
                Icon(Icons.Default.Phone, contentDescription = null, tint = Color.Green)
                Icon(Icons.Default.Help, contentDescription = null, tint = Color.White)
                Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
                Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("الأقسام الرئيسية", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))

            // Modules Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                val menuItems = listOf(
                    MenuAction("المحصلات الشهرية", Icons.Default.EventNote, Color(0xFF8B5CF6), "students"),
                    MenuAction("محرر أتمتة", Icons.Default.FactCheck, Color(0xFF3B82F6), ""),
                    MenuAction("محرر إمتحانات", Icons.Default.Description, Color(0xFFF59E0B), ""),
                    MenuAction("حضور وغياب", Icons.Default.CoPresent, Color(0xFF10B981), ""),
                    MenuAction("النتائج الشهرية", Icons.Default.Assessment, Color(0xFFEC4899), ""),
                    MenuAction("النتائج النهائية", Icons.Default.WorkspacePremium, Color(0xFF64748B), ""),
                    MenuAction("بطاقات التقدم", Icons.Default.Badge, Color(0xFF0EA5E9), ""),
                    MenuAction("سجل الطلاب", Icons.Default.RecentActors, Color(0xFFF43F5E), "students_list"),
                    MenuAction("الشهادات", Icons.Default.CardMembership, Color(0xFF8B5CF6), "")
                )
                
                items(menuItems.size) { index ->
                    val action = menuItems[index]
                    Box(
                        modifier = Modifier
                            .aspectRatio(0.85f)
                            .glassMorphism(cornerRadius = 16.dp, intensity = 0.8f)
                            .clickable { if(action.route.isNotEmpty()) navController.navigate(action.route) }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(Brush.linearGradient(listOf(action.color, action.color.copy(alpha = 0.6f))), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(action.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(action.title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium, maxLines = 2)
                        }
                    }
                }
            }
        }
    }
}

data class MenuAction(val title: String, val icon: ImageVector, val color: Color, val route: String)

