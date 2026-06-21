package com.example.ui.features.core

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WifiTethering
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
    var isSmartRulerEnabled by remember { mutableStateOf(false) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("محرر الامتحانات ومحرك الرسم") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = isSmartRulerEnabled, onCheckedChange = { isSmartRulerEnabled = it })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("المسطرة الذكية (Smart Ruler): ${if (isSmartRulerEnabled) "تعمل" else "متوقفة"}", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("معالج الرموز الديناميكي (Math & Physics) - جاري التحميل...", color = Color.Gray, style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.White)
                        .border(1.dp, Color.LightGray)
                ) {
                    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                        // Background watermark (Non-editable)
                        val paint = android.graphics.Paint().apply {
                            color = android.graphics.Color.argb(40, 200, 200, 200)
                            textSize = 40f
                        }
                        
                        drawContext.canvas.nativeCanvas.apply {
                            save()
                            rotate(-30f, size.width/2, size.height/2)
                            drawText("بواسطة محرر اليرموك الشامل @برمجة وتطوير المهندس سهيل الهزبري", 50f, size.height/2, paint)
                            restore()
                        }
                        
                        // Fake drawn shapes if smart ruler is enabled
                        if (isSmartRulerEnabled) {
                            drawLine(
                                color = Color.Black,
                                start = androidx.compose.ui.geometry.Offset(100f, 100f),
                                end = androidx.compose.ui.geometry.Offset(500f, 100f),
                                strokeWidth = 5f
                            )
                            drawContext.canvas.nativeCanvas.drawText("Vectorized Line (Smart Ruler)", 120f, 90f, android.graphics.Paint().apply { textSize = 20f; color = android.graphics.Color.BLUE })
                        }
                    }
                }
                
                Button(onClick = { /* Save canvas */ }, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                    Text("حفظ النموذج وتطبيق العلامة المائية السيادية")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleSheetScreen(viewModel: YarmoukViewModel, onNavigateBack: () -> Unit) {
    var numQuestions by remember { mutableStateOf("10") }
    var numOptions by remember { mutableStateOf("4") }
    
    val qCount = numQuestions.toIntOrNull() ?: 10
    val oCount = numOptions.toIntOrNull() ?: 4

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("محرر الأتمتة (البابل شيت)") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
                Text("محرك تصميم أوراق الأتمتة الديناميكي", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = numQuestions, 
                        onValueChange = { numQuestions = it }, 
                        label = { Text("عدد الأسئلة") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = numOptions, 
                        onValueChange = { numOptions = it }, 
                        label = { Text("عدد الخيارات (A,B,C,D)") },
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    LazyColumn(modifier = Modifier.padding(16.dp).fillMaxSize()) {
                        items((1..qCount).toList()) { questionNumber ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                            ) {
                                Text("$questionNumber.", modifier = Modifier.width(40.dp), fontWeight = FontWeight.Bold, color = Color.Black)
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    val letters = listOf("A", "B", "C", "D", "E", "F")
                                    for (i in 0 until java.lang.Math.min(oCount, letters.size)) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .background(Color.Transparent, shape = androidx.compose.foundation.shape.CircleShape)
                                                .border(2.dp, Color.Black, shape = androidx.compose.foundation.shape.CircleShape),
                                            contentAlignment = androidx.compose.ui.Alignment.Center
                                        ) {
                                            Text(letters[i], color = Color.Gray)
                                        }
                                    }
                                }
                            }
                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
                        }
                    }
                }
                
                Button(onClick = { /* Export to PDF */ }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    Text("تصدير ورقة الأتمتة PDF")
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
fun PlanReminderScreen(onNavigateBack: () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(topBar = { TopAppBar(title = { Text("تذكير الخطط (إشعارات صوتية)") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") } }) }) { p ->
            Column(Modifier.padding(p).padding(16.dp)) {
                Text("إعدادت المساعد الصوتي لتنبيه المعلم بجدول أعماله اليومي.", fontWeight = FontWeight.Bold)
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
