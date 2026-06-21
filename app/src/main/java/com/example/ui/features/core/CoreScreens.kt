package com.example.ui.features.core

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ui.YarmoukViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamsScreen(viewModel: YarmoukViewModel, onNavigateBack: () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("هيكلية ورقة الاختبار (The Exam Engine)") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
                // Exam Paper Container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.White)
                        .border(2.dp, Color.Black) // Outer Border
                        .padding(2.dp)
                        .border(1.dp, Color.Black) // Inner Border
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Header
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                Text("الجمهورية اليمنية", fontWeight = FontWeight.Bold, color = Color.Black)
                                Text("وزارة التربية والتعليم", fontWeight = FontWeight.Bold, color = Color.Black)
                                Text("مكتب التربية والتعليم", color = Color.Black)
                                Text("المدرسة: .................", color = Color.Black)
                            }
                            
                            Box(modifier = Modifier.weight(1f).height(80.dp), contentAlignment = Alignment.Center) {
                                // Placeholder for Yemen Emblem
                                Icon(Icons.Filled.Star, contentDescription = "شعار الجمهورية", tint = Color.Gray, modifier = Modifier.size(48.dp))
                            }
                            
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                Text("اسم الطالب: .................", color = Color.Black)
                                Text("المادة: .................", color = Color.Black)
                                Text("رقم الجلوس: .................", color = Color.Black)
                            }
                        }
                        
                        Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))
                        Text("الأسئلة:", fontWeight = FontWeight.Bold, color = Color.Black)
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // 3-Column Table
                        Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray).border(1.dp, Color.Black)) {
                            Text("رقم السؤال", modifier = Modifier.weight(1f).border(1.dp, Color.Black).padding(8.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("السؤال", modifier = Modifier.weight(3f).border(1.dp, Color.Black).padding(8.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("الدرجة", modifier = Modifier.weight(1f).border(1.dp, Color.Black).padding(8.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                        
                        // Table Rows
                        val questions = listOf("عرّف ما يلي: ........................", "علل: ........................", "اشرح باختصار: ........................")
                        questions.forEachIndexed { index, q ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text("السؤال ${index + 1}", modifier = Modifier.weight(1f).border(1.dp, Color.Black).padding(16.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                Text(q, modifier = Modifier.weight(3f).border(1.dp, Color.Black).padding(16.dp), color = Color.Black)
                                Text("", modifier = Modifier.weight(1f).border(1.dp, Color.Black).padding(16.dp), color = Color.Black) // Empty for score
                            }
                        }
                    }
                }
                
                Button(onClick = { /* Export */ }, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                    Text("طباعة النموذج وتطبيق العلامة المائية")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleSheetScreen(viewModel: YarmoukViewModel, onNavigateBack: () -> Unit) {
    var numQuestions by remember { mutableStateOf("40") }
    
    val qCount = numQuestions.toIntOrNull() ?: 40

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("هيكلية ورقة الأتمتة (Matrix Cells)") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
                Text("محرك تصميم أوراق الأتمتة (نظام الخلايا لتطابق الأقطار)", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = numQuestions, 
                    onValueChange = { numQuestions = it }, 
                    label = { Text("عدد الأسئلة") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            // Two Blocks of Questions (1-20, 21-40)
                            val perColumn = java.lang.Math.ceil((qCount / 2.0)).toInt()
                            
                            // Block 1
                            Column(modifier = Modifier.weight(1f).border(1.dp, Color.Black)) {
                                Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray).padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("د", modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                    Text("ج", modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                    Text("ب", modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                    Text("أ", modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                    Text("الفقرة", modifier = Modifier.weight(1.5f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                }
                                Divider(color = Color.Black)
                                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                    items((1..perColumn).toList()) { index ->
                                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                            for(i in 0..3) {
                                                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                                                    Box(modifier = Modifier.size(24.dp).border(1.dp, Color.Black, androidx.compose.foundation.shape.CircleShape))
                                                }
                                            }
                                            Text(index.toString(), modifier = Modifier.weight(1.5f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                        }
                                        Divider(color = Color.LightGray)
                                    }
                                }
                            }
                            
                            Spacer(Modifier.width(16.dp))
                            
                            // Block 2
                            Column(modifier = Modifier.weight(1f).border(1.dp, Color.Black)) {
                                Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray).padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("د", modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                    Text("ج", modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                    Text("ب", modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                    Text("أ", modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                    Text("الفقرة", modifier = Modifier.weight(1.5f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                }
                                Divider(color = Color.Black)
                                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                    val startIdx = perColumn + 1
                                    items((startIdx..java.lang.Math.min(qCount, perColumn * 2)).toList()) { index ->
                                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                            for(i in 0..3) {
                                                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                                                    Box(modifier = Modifier.size(24.dp).border(1.dp, Color.Black, androidx.compose.foundation.shape.CircleShape))
                                                }
                                            }
                                            Text(index.toString(), modifier = Modifier.weight(1.5f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                        }
                                        Divider(color = Color.LightGray)
                                    }
                                }
                            }
                        }
                    }
                }
                
                Button(onClick = { /* Export to PDF */ }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    Text("تصدير ورقة الأتمتة (Scanner Ready)")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlansScreen(viewModel: YarmoukViewModel, isDaily: Boolean, onNavigateBack: () -> Unit) {
    val plans by viewModel.getPlans(if (isDaily) "Daily" else "Semester").collectAsState()
    var title by remember { mutableStateOf("") }
    
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(if (isDaily) "الخطة اليومية" else "الخطة الفصلية") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                if (isDaily) {
                    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("معدل الحصص السريع", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("توليد خطة درس مختصرة تعوض الوقت الفائت (في حال دخول المعلم متأخراً).", style = MaterialTheme.typography.bodySmall)
                            Button(onClick = { viewModel.addPlan("Daily", "خطة تعويضية سريعة - ${System.currentTimeMillis()}", "خطة مختصرة تركز على الأهداف الرئيسية", "2026") }, modifier = Modifier.padding(top=8.dp)) {
                                Text("تعويض حصة")
                            }
                        }
                    }
                }

                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("عنوان الخطة") }, modifier = Modifier.fillMaxWidth())
                Button(onClick = { viewModel.addPlan(if (isDaily) "Daily" else "Semester", title, "محتوى", "2026") }, modifier = Modifier.padding(top=8.dp)) {
                    Text("إضافة خطة")
                }
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(plans) { plan ->
                        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(plan.title, fontWeight = FontWeight.Bold)
                                if (plan.description.isNotEmpty()) {
                                    Text(plan.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(viewModel: YarmoukViewModel, onNavigateBack: () -> Unit) {
    val students by viewModel.students.collectAsState()
    
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("النتائج والمحصلات") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
                items(students) { student ->
                    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(student.name, fontWeight = FontWeight.Bold)
                            Text("النتيجة: ${student.score}")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemosScreen(viewModel: YarmoukViewModel, onNavigateBack: () -> Unit) {
    val memos by viewModel.memos.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val suggestions = com.example.utils.LinguisticEditorEngine.checkDocument(content)
    
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("مذكرات رسمية (مصحح لغوي)") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                
                Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.WifiTethering, contentDescription = "P2P", tint = Color(0xFF1565C0))
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text("خزانة المذكرات المشتركة", fontWeight = FontWeight.Bold, color = Color(0xFF1565C0))
                            Text("شارك المذكرات مشفرة مع المعلمين عبر الشبكة المحلية (بدون إنترنت).", style = MaterialTheme.typography.bodySmall, color = Color(0xFF1565C0))
                            Button(onClick = {}, modifier = Modifier.padding(top=8.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))) {
                                Text("إرسال الخزانة للمقترنين")
                            }
                        }
                    }
                }

                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("عنوان المذكرة") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("محتوى المذكرة") }, modifier = Modifier.fillMaxWidth().height(150.dp))
                
                if (suggestions.isNotEmpty()) {
                    Text("أخطاء إملائية مقترحة الدورية:", color = Color.Red, modifier = Modifier.padding(top=8.dp))
                    suggestions.forEach { s ->
                        Text("- ${s.word} -> ${s.suggestions.joinToString(",")}", color = Color.Red)
                    }
                }
                
                Button(onClick = { viewModel.addMemo(title, content, "2026") }, modifier = Modifier.padding(top=8.dp)) {
                    Text("حفظ المذكرة")
                }
                
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(memos) { memo ->
                        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(memo.title, fontWeight = FontWeight.Bold)
                                Text(memo.content)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountantScreen(viewModel: YarmoukViewModel, onNavigateBack: () -> Unit) {
    val expenses by viewModel.expenses.collectAsState()
    var amount by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("المحاسب المدرسي") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("المبلغ") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("البيان") }, modifier = Modifier.fillMaxWidth())
                
                Button(onClick = { viewModel.addExpense("Expense", amount.toDoubleOrNull() ?: 0.0, desc, "2026") }, modifier = Modifier.padding(top=8.dp)) {
                    Text("حفظ الدفتر")
                }
                
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(expenses) { exp ->
                        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                            Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(exp.description, fontWeight = FontWeight.Bold)
                                Text(exp.amount.toString(), color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolsScreen(onNavigateBack: () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(topBar = { TopAppBar(title = { Text("إدارة المدارس (Sync)") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") } }) }) { p ->
            Column(Modifier.padding(p).padding(16.dp)) { Text("نظام P2P Mesh للمزامنة عبر WiFi قيد التفعيل...") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolFingerprintScreen(onNavigateBack: () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(topBar = { TopAppBar(title = { Text("بصمة المدرسة (التقرير الشهري)") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") } }) }) { p ->
            Column(Modifier.padding(p).padding(16.dp)) {
                Text("تصدير التقرير الشهري لإدارة التربية أوتوماتيكياً...", fontWeight = FontWeight.Bold)
                Button(onClick = {}, modifier = Modifier.padding(top=16.dp)) { Text("إنشاء وتصدير (PDF/Excel)") }
                Spacer(Modifier.height(16.dp))
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Warning, "Warning", tint = Color.Red)
                        Spacer(Modifier.width(8.dp))
                        Text("تنبيه بصمة الكود: لم يتم أخذ نسخة احتياطية منذ 3 أيام. يرجى أخذ نسخة الآن.", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentPortfolioScreen(viewModel: YarmoukViewModel, onNavigateBack: () -> Unit) {
    val students by viewModel.students.collectAsState()
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(topBar = { TopAppBar(title = { Text("محفظة الطالب الرقمية") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") } }) }) { p ->
            LazyColumn(Modifier.padding(p).padding(16.dp)) {
                items(students) { s ->
                    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(s.name, fontWeight = FontWeight.Bold)
                            Text("السجل السلوكي/التراكمي", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(onNavigateBack: () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("هيكلية الجدول الزمني (The Official Schedule)") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.White)
                        .border(2.dp, Color.Black)
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Header
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                Text("الجمهورية اليمنية", fontWeight = FontWeight.Bold, color = Color.Black)
                                Text("وزارة التربية والتعليم", fontWeight = FontWeight.Bold, color = Color.Black)
                                Text("اللجنة العليا للاختبارات", fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                            
                            Box(modifier = Modifier.weight(1f).height(80.dp), contentAlignment = Alignment.Center) {
                                Icon(Icons.Filled.Star, contentDescription = "شعار الجمهورية", tint = Color.Gray, modifier = Modifier.size(64.dp))
                            }
                            
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                Text("بسم الله الرحمن الرحيم", fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }
                        
                        Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
                        
                        Text("جدول سير تنفيذ الاختبارات النهائية", modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("لشهادة الثانوية العامة (القسم العلمي) للعام الدراسي 2025/2026م", modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                        
                        // 5-Column Table Header
                        Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray).border(1.dp, Color.Black)) {
                            Text("مدة الإجابة", modifier = Modifier.weight(1f).border(1.dp, Color.Black).padding(8.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                            Column(modifier = Modifier.weight(1f).border(1.dp, Color.Black)) {
                                Text("زمن الإجابة", modifier = Modifier.fillMaxWidth().padding(4.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                                Divider(color = Color.Black)
                                Row {
                                    Text("إلى", modifier = Modifier.weight(1f).border(1.dp, Color.Black).padding(4.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                    Text("من", modifier = Modifier.weight(1f).border(1.dp, Color.Black).padding(4.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                }
                            }
                            Text("المادة الدراسية", modifier = Modifier.weight(1.5f).border(1.dp, Color.Black).padding(8.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("التاريخ", modifier = Modifier.weight(1.5f).border(1.dp, Color.Black).padding(8.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("اليوم", modifier = Modifier.weight(1f).border(1.dp, Color.Black).padding(8.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                        
                        // Sample Data Rows
                        val schedule = listOf(
                            Triple("الأحد", "2026/6/7م", "القرآن الكريم"),
                            Triple("الثلاثاء", "2026/6/9م", "التربية الإسلامية"),
                            Triple("الخميس", "2026/6/11م", "اللغة العربية")
                        )
                        
                        schedule.forEach { (day, date, subject) ->
                            Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Black)) {
                                Text("ثلاث ساعات", modifier = Modifier.weight(1f).border(1.dp, Color.Black).padding(12.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                Text("11:30", modifier = Modifier.weight(0.5f).border(1.dp, Color.Black).padding(12.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                Text("8:30", modifier = Modifier.weight(0.5f).border(1.dp, Color.Black).padding(12.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                Text(subject, modifier = Modifier.weight(1.5f).border(1.dp, Color.Black).padding(12.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text(date, modifier = Modifier.weight(1.5f).border(1.dp, Color.Black).padding(12.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Black)
                                Text(day, modifier = Modifier.weight(1f).border(1.dp, Color.Black).padding(12.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }
                    }
                }
                
                Button(onClick = { /* Export */ }, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                    Text("طباعة الجدول الزمني")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentCardsScreen(viewModel: YarmoukViewModel, onNavigateBack: () -> Unit) {
    val students by viewModel.students.collectAsState()
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(topBar = { TopAppBar(title = { Text("بطاقات طلابية") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") } }) }) { p ->
            LazyColumn(Modifier.padding(p).padding(16.dp)) {
                items(students) { student ->
                    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).background(Color(0xFF0F2A4A))) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("بطاقة طالب", color = Color.White)
                            Text(student.name, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(student.gradeLevel, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
