package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.data.Student
import com.example.ui.MainViewModel
import com.example.ui.theme.glassMorphism

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentManagementScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val students by viewModel.students.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(top = 32.dp)) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("المحصلات الشهرية", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchStudents(it)
            },
            placeholder = { Text("بحث بالاسم أو رقم الجلوس", color = Color.White.copy(alpha=0.5f)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).glassMorphism(intensity = 0.3f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Students Table Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp).glassMorphism(intensity = 0.5f).padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("م", color = Color.White, modifier = Modifier.weight(0.1f))
            Text("إسم الطالب رباعياً", color = Color.White, modifier = Modifier.weight(0.5f))
            Text("رقم الجلوس", color = Color.White, modifier = Modifier.weight(0.2f))
            Text("إجراء", color = Color.White, modifier = Modifier.weight(0.1f))
        }

        // Students List
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(students) { student ->
                Row(
                    modifier = Modifier.fillMaxWidth().glassMorphism(intensity = 0.3f).padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(student.id.toString(), color = Color.White, modifier = Modifier.weight(0.1f))
                    Text(student.fullName, color = Color.White, modifier = Modifier.weight(0.5f))
                    Text(student.seatNumber, color = Color.White, modifier = Modifier.weight(0.2f))
                    IconButton(onClick = { viewModel.deleteStudent(student.id) }, modifier = Modifier.weight(0.1f)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha=0.7f))
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier.padding(16.dp),
            containerColor = Color(0xFF6B21A8)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Student", tint = Color.White)
        }
    }

    if (showAddDialog) {
        var fullName by remember { mutableStateOf("") }
        var seatNumber by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("إضافة طالب جديد") },
            text = {
                Column {
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("إسم الطالب رباعياً") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = seatNumber,
                        onValueChange = { seatNumber = it },
                        label = { Text("رقم الجلوس") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.addStudent(Student(fullName = fullName, seatNumber = seatNumber, birthDate = "", governorate = "", district = "", school = ""))
                    showAddDialog = false
                }) {
                    Text("إضافة")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("إلغاء")
                }
            }
        )
    }
}
