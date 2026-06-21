package com.example.ui.features.grading

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Warning
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
import com.example.data.local.entity.StudentEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradingScreen(
    viewModel: com.example.ui.YarmoukViewModel,
    onNavigateBack: () -> Unit
) {
    val students by viewModel.students.collectAsState()

    var selectedIndex by remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("سجل الدرجات السريع", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "رجوع")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Voice Recognition Stub */ }) {
                            Icon(Icons.Filled.Mic, contentDescription = "الرصد الصوتي", tint = MaterialTheme.colorScheme.primary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { /* PDF Generation Stub */ },
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Print, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("طباعة كشف الرصد", fontWeight = FontWeight.Bold)
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Students List
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(students) { index, student ->
                        val isSelected = index == selectedIndex
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedIndex = index }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = student.name,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal),
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = student.score.ifEmpty { "---" },
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = if (student.score.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        }
                    }
                }

                if (selectedIndex < students.size) {
                    val currentStudent = students[selectedIndex]
                    val scoreVal = currentStudent.score.toIntOrNull() ?: 0
                    if (scoreVal > 100) {
                        Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Warning, contentDescription = "Error", tint = Color.Red)
                                Spacer(Modifier.width(8.dp))
                                Column {
                                    Text("تنبيه الكنترول ⚠️", fontWeight = FontWeight.Bold, color = Color.Red)
                                    Text("المجموع ($scoreVal) يتجاوز النهاية العظمى (100). يرجى المراجعة.", color = Color.Red, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }

                // Custom Numeric Keyboard
                CustomNumericKeyboard(
                    onNumberClick = { num ->
                        if (selectedIndex < students.size) {
                            val currentScore = students[selectedIndex].score
                            if (currentScore.length < 3) {
                                viewModel.updateStudentScore(students[selectedIndex], currentScore + num)
                            }
                        }
                    },
                    onDeleteClick = {
                        if (selectedIndex < students.size) {
                            val currentScore = students[selectedIndex].score
                            if (currentScore.isNotEmpty()) {
                                viewModel.updateStudentScore(students[selectedIndex], currentScore.dropLast(1))
                            }
                        }
                    },
                    onNextClick = {
                        if (selectedIndex < students.size - 1) {
                            selectedIndex++
                            coroutineScope.launch {
                                listState.animateScrollToItem(selectedIndex)
                            }
                        }
                    },
                    onSpecialClick = { action ->
                        if (selectedIndex < students.size) {
                            viewModel.updateStudentScore(students[selectedIndex], action)
                            if (selectedIndex < students.size - 1) {
                                selectedIndex++
                                coroutineScope.launch {
                                    listState.animateScrollToItem(selectedIndex)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CustomNumericKeyboard(
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onNextClick: () -> Unit,
    onSpecialClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp)
    ) {
        val keys = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("⌫", "0", "إدخال")
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KeyboardButton("غائب", modifier = Modifier.weight(1f), color = Color(0xFFE53935), textColor = Color.White) { onSpecialClick("غائب") }
            KeyboardButton("مؤجل", modifier = Modifier.weight(1f), color = Color(0xFFFB8C00), textColor = Color.White) { onSpecialClick("مؤجل") }
        }

        for (row in keys) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (key in row) {
                    val isAction = key == "⌫" || key == "إدخال"
                    val actionColor = if (key == "إدخال") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    
                    KeyboardButton(
                        text = key,
                        modifier = Modifier.weight(1f),
                        color = if (isAction) actionColor else MaterialTheme.colorScheme.surface,
                        textColor = if (isAction) Color.White else MaterialTheme.colorScheme.onSurface,
                        onClick = {
                            when (key) {
                                "⌫" -> onDeleteClick()
                                "إدخال" -> onNextClick()
                                else -> onNumberClick(key)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun KeyboardButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(56.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
