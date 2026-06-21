package com.example.ui.features.attendance

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.data.local.entity.StudentEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    onNavigateBack: () -> Unit
) {
    // Dummy Data for demonstration
    val dummyStudents = remember {
        mutableStateListOf(
            StudentEntity(1, "أحمد محمد علي", "الصف الأول"),
            StudentEntity(2, "خالد عبد الله محمود", "الصف الأول"),
            StudentEntity(3, "يوسف حسن مصطفى", "الصف الأول"),
            StudentEntity(4, "عمر ابراهيم خليل", "الصف الأول"),
            StudentEntity(5, "علي عبد الرحمن سعد", "الصف الأول")
        )
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("الغياب والحضور السريع", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "رجوع")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /* Save to DB */ onNavigateBack() },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text("حفظ", modifier = Modifier.padding(horizontal = 16.dp), fontWeight = FontWeight.Bold)
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    "المسح السريع للغياب: اضغط على اسم الطالب لتحويله إلى غائب.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(dummyStudents) { student ->
                        val isPresent = student.attendanceStatus == "حاضر"
                        val backgroundColor = if (isPresent) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                        val contentColor = if (isPresent) Color(0xFF2E7D32) else Color(0xFFC62828)
                        val icon = if (isPresent) Icons.Filled.CheckCircle else Icons.Filled.Cancel

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(backgroundColor)
                                .clickable {
                                    val index = dummyStudents.indexOf(student)
                                    dummyStudents[index] = student.copy(
                                        attendanceStatus = if (isPresent) "غائب" else "حاضر"
                                    )
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = student.name,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = contentColor
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (isPresent) "حاضر" else "غائب",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = contentColor,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Icon(icon, contentDescription = null, tint = contentColor)
                            }
                        }
                    }
                }
            }
        }
    }
}
