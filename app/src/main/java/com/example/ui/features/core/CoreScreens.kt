package com.example.ui.features.core

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ui.YarmoukViewModel
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamsScreen(viewModel: YarmoukViewModel, onNavigateBack: () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("محرر الامتحانات الذكي") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                Text("محرك تصميم الامتحانات - قيد التطوير الداخلي")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleSheetScreen(viewModel: YarmoukViewModel, onNavigateBack: () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("محرر الأتمتة (البابل شيت)") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, "رجوع") }
                })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                Text("محرك التعرف على الأنماط والأتمتة مدمج هنا.")
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
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("عنوان الخطة") }, modifier = Modifier.fillMaxWidth())
                Button(onClick = { viewModel.addPlan(if (isDaily) "Daily" else "Semester", title, "محتوى", "2026") }, modifier = Modifier.padding(top=8.dp)) {
                    Text("إضافة خطة")
                }
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(plans) { plan ->
                        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                            Text(plan.title, modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
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
