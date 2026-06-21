package com.example.ui.features.certificates

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.YarmoukAccentNeo
import com.example.ui.theme.YarmoukSurfaceVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CertificateBuilderScreen(viewModel: com.example.ui.YarmoukViewModel, onNavigateBack: () -> Unit) {
    val students by viewModel.students.collectAsState()
    val firstStudent = students.firstOrNull()
    
    var showLogo by remember { mutableStateOf(true) }
    var showSeal by remember { mutableStateOf(true) }
    var showDate by remember { mutableStateOf(true) }
    var showRank by remember { mutableStateOf(true) }
    var studentName by remember { mutableStateOf(firstStudent?.name ?: "اسم الطالب الافتراضي") }
    var gradeText by remember { mutableStateOf(firstStudent?.score ?: "ممتاز") }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("منشئ الشهائد الذكي", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "رجوع")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Export PDF */ }) {
                            Icon(Icons.Filled.PictureAsPdf, contentDescription = "تصدير PDF", tint = YarmoukAccentNeo)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Live Preview Area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White) // Paper color
                        .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CertificatePreview(
                        showLogo = showLogo,
                        showSeal = showSeal,
                        showDate = showDate,
                        showRank = showRank,
                        studentName = studentName,
                        gradeText = gradeText
                    )
                }

                // Configuration Panel
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
                            Icon(Icons.Filled.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("إعدادات القالب الذكية", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        }

                        OutlinedTextField(
                            value = studentName,
                            onValueChange = { studentName = it },
                            label = { Text("اسم الطالب للتجربة") },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            singleLine = true
                        )

                        ConfigSwitch("عرض الشعار الرسمي (الوزارة)", showLogo) { showLogo = it }
                        ConfigSwitch("إضافة الختم الرقمي (Digital Seal)", showSeal) { showSeal = it }
                        ConfigSwitch("عرض تاريخ الإصدار", showDate) { showDate = it }
                        ConfigSwitch("عرض الترتيب على مستوى الصف", showRank) { showRank = it }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { /* Generate Batch */ },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Filled.Check, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("اعتماد القالب وتوليد الدفعة", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConfigSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = YarmoukAccentNeo,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = YarmoukSurfaceVariant
            )
        )
    }
}

@Composable
fun CertificatePreview(
    showLogo: Boolean,
    showSeal: Boolean,
    showDate: Boolean,
    showRank: Boolean,
    studentName: String,
    gradeText: String
) {
    // Simulating the Certificate Layout
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                if (showLogo) {
                    Box(modifier = Modifier.size(60.dp).background(Color.LightGray, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                        Text("شعار", color = Color.DarkGray, fontSize = 12.sp)
                    }
                } else {
                    Spacer(modifier = Modifier.size(60.dp))
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("الجمهورية اليمنية", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("وزارة التربية والتعليم", fontSize = 12.sp, color = Color.DarkGray)
                }
                
                Spacer(modifier = Modifier.size(60.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            Text("شـهـادة تـفـوق", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color(0xFFB8860B), textAlign = TextAlign.Center)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("يسر إدارة المدرسة أن تمنح الطالب(ة):", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(studentName, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F2A4A))
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("هذه الشهادة تقديراً لجهوده وتفوقه بتقدير ($gradeText).", fontSize = 16.sp, color = Color.Black, textAlign = TextAlign.Center)

            if (showRank) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("وحصوله على الترتيب: (الأول)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB8860B))
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("مدير المدرسة", fontSize = 14.sp, color = Color.Black)
                    if (showSeal) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.size(50.dp).border(2.dp, Color.Red, RoundedCornerShape(25.dp)), contentAlignment = Alignment.Center) {
                            Text("ختم", color = Color.Red, fontSize = 12.sp)
                        }
                    }
                }

                if (showDate) {
                    Text("بتاريخ: 2026/06/20", fontSize = 12.sp, color = Color.DarkGray)
                }
            }
        }
    }
}
