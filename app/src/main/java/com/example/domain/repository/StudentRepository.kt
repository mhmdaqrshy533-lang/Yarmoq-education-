package com.example.domain.repository

import com.example.data.local.StudentDao
import com.example.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

class StudentRepository(private val studentDao: StudentDao) {
    val allStudents: Flow<List<StudentEntity>> = studentDao.getAllStudents()

    suspend fun insert(student: StudentEntity) {
        studentDao.insertStudent(student)
    }
}
