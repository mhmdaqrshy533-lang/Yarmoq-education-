package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val gradeLevel: String,
    val attendanceStatus: String = "حاضر", // Default to present
    val score: String = "" // Added score for grading
)
