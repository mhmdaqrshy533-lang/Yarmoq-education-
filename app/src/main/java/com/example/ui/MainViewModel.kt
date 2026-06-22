package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).studentDao()

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    init {
        loadStudents()
    }

    private fun loadStudents() {
        viewModelScope.launch {
            dao.getAllStudents().collect {
                _students.value = it
            }
        }
    }

    fun searchStudents(query: String) {
        viewModelScope.launch {
            dao.searchStudents(query).collect {
                _students.value = it
            }
        }
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            dao.insertStudent(student)
        }
    }

    fun deleteStudent(studentId: Int) {
        viewModelScope.launch {
            dao.deleteStudent(studentId)
        }
    }
}
