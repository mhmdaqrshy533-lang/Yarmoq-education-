package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.data.Student
import com.example.ui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentsListScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val students by viewModel.students.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // App Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.clickable { navController.popBackStack() }, tint = Color.Gray)
            Spacer(modifier = Modifier.width(16.dp))
            Text("عرض سجلات الطلاب", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
        
        // Search Bar & Add Button
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /* Navigate to add */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("إضافة")
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("بحث بالاسم أو رقم الجلوس", textAlign = TextAlign.Right) },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.LightGray)
            )
        }
        
        Text("السجلات: ${students.size}", modifier = Modifier.padding(16.dp), color = Color.Gray, fontSize = 12.sp)

        // List
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (students.isEmpty()) {
                val dummy = listOf(
                    Student(0, "2025 / 2026", "محمد عبدالله احمد علي الصبري", "01/01/2009", "تعز", "مشرعة وحدنان", "شعيب المجيرين"),
                    Student(1, "2025 / 2026", "محمد نبيل عبدالقادر سعيد عبدالاله", "01/01/2009", "تعز", "مشرعة وحدنان", "شعيب المجيرين")
                )
                items(dummy) { student -> StudentCard(student, dummy.indexOf(student)+1) }
            } else {
                items(students) { student -> StudentCard(student, students.indexOf(student)+1) }
            }
        }
    }
}

@Composable
fun StudentCard(student: Student, index: Int) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("ذكر", color = Color(0xFF8B5CF6), fontSize = 12.sp, modifier = Modifier.background(Color(0xFFF3E8FF), RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(student.fullName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(index.toString(), color = Color.Red)
                    }
                    Text("${student.governorate} • ${student.district} • ${student.birthDate}", color = Color.Gray, fontSize = 12.sp)
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEE2E2)), contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp), shape = RoundedCornerShape(4.dp)) { Text("حذف", color = Color.Red, fontSize = 12.sp) }
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECFDF5)), contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp), shape = RoundedCornerShape(4.dp)) { Text("تعديل", color = Color(0xFF10B981), fontSize = 12.sp) }
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6)), contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp), shape = RoundedCornerShape(4.dp)) { Text("إخفاء", color = Color.Gray, fontSize = 12.sp) }
                }
                Text("معهد: --", color = Color(0xFF3B82F6), fontSize = 12.sp, modifier = Modifier.background(Color(0xFFDBEAFE), RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
            }
        }
    }
}
