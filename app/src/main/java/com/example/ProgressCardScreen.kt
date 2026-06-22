package com.example

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressCardScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf("بطاقة تقدم عادي") }
    val tabs = listOf("بطاقة تقدم Lux", "بطاقة تقدم عادي", "رقم جلوس Lux", "رقم جلوس")

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFEDE9F6))) { // Light purple background
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6B21A8))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val context = androidx.compose.ui.platform.LocalContext.current
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.clickable { navController.popBackStack() }, tint = Color.White)
            Text("الصف : لتاسع | 2025-2026", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Row {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White, modifier = Modifier.padding(end = 12.dp))
                Icon(Icons.Default.Print, contentDescription = "Print", tint = Color.White, modifier = Modifier.clickable {
                    com.example.utils.PdfGenerator.generateProgressCardPdf(context, "محمد نبيل عبدالقادر سعيد عبدالاله", "142578")
                })
            }
        }

        // Tabs
        LazyRow(
            modifier = Modifier.fillMaxWidth().background(Color.White).padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            items(tabs.size) { index ->
                val tab = tabs[index]
                val isSelected = tab == selectedTab
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) Color(0xFFE11D48) else Color(0xFFF3F4F6))
                        .clickable { selectedTab = tab }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(tab, color = if (isSelected) Color.White else Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
        
        // Student Info Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF9333EA))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("الصف : التاسع", color = Color.White, fontSize = 12.sp, modifier = Modifier.background(Color.White, RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 2.dp))
            Text("الطالب : محمد نبيل عبدالقادر سعيد عبدالاله", color = Color.White, fontWeight = FontWeight.Bold)
            Text("142578", color = Color.White, fontSize = 12.sp, modifier = Modifier.background(Color(0xFF6B21A8), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 2.dp))
        }

        // Card Container
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.TopCenter
        ) {
            CardTemplate()
        }
    }
}

@Composable
fun CardTemplate() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(2.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        // Card Header
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text("محافظة", fontSize = 10.sp, color = Color.Gray)
                Text("تعز", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Text("مديرية", fontSize = 10.sp, color = Color.Gray)
                Text("مشرعة وحدنان", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Text("مدرسة", fontSize = 10.sp, color = Color.Gray)
                Text("شعب المجيرين", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(2f)) {
                // Republic of Yemen Logo placeholder
                Box(modifier = Modifier.size(60.dp).background(Color(0xFFF3F4F6), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                    Text("🦅", fontSize = 32.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("بطاقة التقدم لاختبارات شهادة", fontWeight = FontWeight.Bold)
                Text("إتمام التعليم الأساسي", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("العام الدراسي 2025 / 2026", fontSize = 12.sp)
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text("الجمهورية اليمنية", fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Text("وزارة التربية والتعليم", fontSize = 10.sp)
                Text("قطاع المناهج والتوجيه", fontSize = 10.sp)
                Text("الإدارة العامة للاختبارات", fontSize = 10.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        // Section 1
        SectionTitle("أولاً: بيانات أساسية للطالب / الطالبة")
        
        // Names Table
        Column(modifier = Modifier.fillMaxWidth().border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(8.dp)).padding(1.dp)) {
            Text("الاسم بالعربية (رباعياً مع اللقب)", modifier = Modifier.fillMaxWidth().background(Color(0xFFF3F4F6)).padding(4.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Row(modifier = Modifier.fillMaxWidth()) {
                DataCell("اللقب", "الصبري", Modifier.weight(1f))
                DataCell("الرابع", "علي", Modifier.weight(1f))
                DataCell("الثالث", "احمد", Modifier.weight(1f))
                DataCell("الثاني", "عبدالله", Modifier.weight(1f))
                DataCell("الأول", "محمد", Modifier.weight(1f))
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(modifier = Modifier.weight(1f).border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(8.dp)).padding(1.dp)) {
                Text("بيانات محل وتاريخ الميلاد من واقع البطاقة العائلية للأب", modifier = Modifier.fillMaxWidth().background(Color(0xFFF3F4F6)).padding(4.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Row(modifier = Modifier.fillMaxWidth()) {
                    DataCell("تاريخ الميلاد", "01/01/2009", Modifier.weight(1f))
                    DataCell("المديرية", "مشرعة وحدنان", Modifier.weight(1f))
                    DataCell("المحافظة", "تعز", Modifier.weight(1f))
                }
            }
            Column(modifier = Modifier.weight(0.6f).border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(8.dp)).padding(1.dp)) {
                Text("بيانات الشخصية", modifier = Modifier.fillMaxWidth().background(Color(0xFFF3F4F6)).padding(4.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Row(modifier = Modifier.fillMaxWidth()) {
                    DataCell("الجنسية", "يمني", Modifier.weight(1f))
                    DataCell("الجنس", "ذكر", Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section 2
        SectionTitle("ثانياً: بيانات خاصة بحالة التقدم")
        
        Column(modifier = Modifier.fillMaxWidth().border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(8.dp)).padding(1.dp)) {
            Text("أ - الحالات المتقدمة لأول مرة", modifier = Modifier.fillMaxWidth().background(Color(0xFFF3F4F6)).padding(4.dp), textAlign = TextAlign.Right, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFF9FAFB)).border(1.dp, Color(0xFFE5E7EB))) {
                Text("الصف", modifier = Modifier.weight(1f).padding(4.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Text("المدرسة", modifier = Modifier.weight(1f).padding(4.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Text("العام الدراسي", modifier = Modifier.weight(1f).padding(4.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Text("المديرية", modifier = Modifier.weight(1f).padding(4.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }
            HistoryRow("السادس اساسي", "شعيب المجيرين", "2022/2023", "مشرعة وحدنان")
            HistoryRow("السابع اساسي", "شعيب المجيرين", "2023/2024", "مشرعة وحدنان")
            HistoryRow("الثامن اساسي", "شعيب المجيرين", "2024/2025", "مشرعة وحدنان")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Text("مدير المدرسة", fontWeight = FontWeight.Bold)
            Text("مدير مكتب التربية بالمديرية", fontWeight = FontWeight.Bold)
            Text("يعتمد / مدير مكتب التربية بالمحافظة", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Color(0xFFD97706),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun DataCell(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.border(0.5.dp, Color(0xFFE5E7EB)).padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 10.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun HistoryRow(col1: String, col2: String, col3: String, col4: String) {
    Row(modifier = Modifier.fillMaxWidth().border(0.5.dp, Color(0xFFE5E7EB))) {
        Text(col1, modifier = Modifier.weight(1f).padding(4.dp), textAlign = TextAlign.Center, fontSize = 10.sp)
        Text(col2, modifier = Modifier.weight(1f).padding(4.dp), textAlign = TextAlign.Center, fontSize = 10.sp)
        Text(col3, modifier = Modifier.weight(1f).padding(4.dp), textAlign = TextAlign.Center, fontSize = 10.sp)
        Text(col4, modifier = Modifier.weight(1f).padding(4.dp), textAlign = TextAlign.Center, fontSize = 10.sp)
    }
}
