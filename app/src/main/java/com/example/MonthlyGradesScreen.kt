package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ui.MainViewModel
import com.example.ui.theme.glassMorphism

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyGradesScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val students by viewModel.students.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF3F4F6))) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.clickable { navController.popBackStack() }, tint = Color(0xFF1E3A8A))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("محصلات الصف : السادس", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("المادة : اللغة العربية", fontSize = 14.sp, color = Color.Gray)
            }
            Icon(Icons.Default.Print, contentDescription = "Print", tint = Color(0xFF6B21A8))
        }
        
        // Month Tabs
        Row(modifier = Modifier.fillMaxWidth().background(Color.White)) {
            TabItem("الشهر الثالث", false, Modifier.weight(1f))
            TabItem("الشهر الثاني", false, Modifier.weight(1f))
            TabItem("الشهر الأول", true, Modifier.weight(1f))
        }
        
        // Month Title Header
        Box(modifier = Modifier.fillMaxWidth().background(Color(0xFFEEF2FF)).padding(8.dp), contentAlignment = Alignment.Center) {
            Text("الشهر الأول", fontWeight = FontWeight.Bold, color = Color.Black)
        }

        // Table Header
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.White).border(1.dp, Color(0xFFE5E7EB)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TableCellHeader("المحصلة\n20", Modifier.weight(0.1f), bg = Color(0xFFFCE7F3))
            TableCellHeader("المجموع\n100", Modifier.weight(0.12f), bg = Color(0xFFDBEAFE))
            TableCellHeader("التحريري\n40", Modifier.weight(0.11f))
            TableCellHeader("الشفهي\n20", Modifier.weight(0.11f))
            TableCellHeader("المواظبة\n20", Modifier.weight(0.11f))
            TableCellHeader("الواجبات\n20", Modifier.weight(0.11f))
            TableCellHeader("إسم الطالب رباعياً", Modifier.weight(0.3f))
            TableCellHeader("م", Modifier.weight(0.04f))
        }

        // Table Content
        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
            if (students.isEmpty()) {
                val dummyStudents = listOf("أبرار دارس عبدالله محمد رضوان", "أديبة محمد محمد عبدالله محمود", "أربكان محمد رضوان أحمد رضوان", "أركان محمد سعد الحاج حسين المشيرعي", "أروى ماجد حيدر ناصر الذراع")
                itemsIndexed(dummyStudents) { index, name ->
                    GradeRow(index + 1, name, if(index == 0) "غ" else (12..15).random().toString())
                }
            } else {
                itemsIndexed(students) { index, student ->
                    GradeRow(index + 1, student.fullName, "15")
                }
            }
        }

        // Bottom Navigation Bar
        Row(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF6B21A8)).padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem("لغة الأرقام", Icons.Default.Settings)
            BottomNavItem("الطباعة", Icons.Default.Print)
            BottomNavItem("إدخال الدرجات", Icons.AutoMirrored.Filled.ArrowBack)
            BottomNavItem("الطلاب", Icons.AutoMirrored.Filled.ArrowBack, isSelected = true)
        }
    }
}

@Composable
fun GradeRow(index: Int, name: String, val1: String) {
    val bg = if (index % 2 == 0) Color(0xFFF9FAFB) else Color.White
    Row(
        modifier = Modifier.fillMaxWidth().background(bg).border(0.5.dp, Color(0xFFE5E7EB)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell("12", Modifier.weight(0.1f), bg = Color(0xFFFCE7F3))
        TableCell("60", Modifier.weight(0.12f), bg = Color(0xFFDBEAFE))
        TableCell("15", Modifier.weight(0.11f))
        TableCell("15", Modifier.weight(0.11f))
        TableCell("15", Modifier.weight(0.11f))
        TableCell(val1, Modifier.weight(0.11f), textColor = if(val1 == "غ") Color.Red else Color.Black)
        TableCell(name, Modifier.weight(0.3f), textAlign = TextAlign.Right)
        TableCell(index.toString(), Modifier.weight(0.04f), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TabItem(title: String, isSelected: Boolean, modifier: Modifier) {
    Column(
        modifier = modifier.clickable { }.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = if (isSelected) Color(0xFF6B21A8) else Color.Gray, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal)
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.height(3.dp).fillMaxWidth().background(Color(0xFF6B21A8), RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)))
        }
    }
}

@Composable
fun TableCellHeader(text: String, modifier: Modifier, bg: Color = Color.Transparent) {
    Box(
        modifier = modifier.fillMaxHeight().background(bg).padding(4.dp).border(0.5.dp, Color(0xFFE5E7EB)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black, textAlign = TextAlign.Center)
    }
}

@Composable
fun TableCell(text: String, modifier: Modifier, bg: Color = Color.Transparent, textColor: Color = Color.Black, textAlign: TextAlign = TextAlign.Center, fontWeight: FontWeight = FontWeight.Normal) {
    Box(
        modifier = modifier.fillMaxHeight().background(bg).padding(8.dp).border(0.5.dp, Color(0xFFE5E7EB)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 12.sp, color = textColor, textAlign = textAlign, fontWeight = fontWeight, maxLines = 1)
    }
}

@Composable
fun BottomNavItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isSelected: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { }) {
        Icon(icon, contentDescription = null, tint = if (isSelected) Color.White else Color(0xFFD8B4E2), modifier = Modifier.size(24.dp))
        Text(title, color = if (isSelected) Color.White else Color(0xFFD8B4E2), fontSize = 12.sp)
    }
}
