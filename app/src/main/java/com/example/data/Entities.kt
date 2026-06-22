package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val seatNumber: String,
    val fullName: String,
    val birthDate: String,
    val governorate: String,
    val district: String,
    val school: String,
    val yearS6: String = "",
    val yearS7: String = "",
    val yearS8: String = ""
)

@Entity(tableName = "monthly_grades")
data class MonthlyGrade(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: Int,
    val subject: String,
    val month: Int,
    val homework: Int,    // الواجبات
    val attendance: Int,  // المواظبة
    val oral: Int,        // الشفهي
    val written: Int,     // التحريري
    val total: Int,       // المجموع
    val result: Int       // المحصلة
)
