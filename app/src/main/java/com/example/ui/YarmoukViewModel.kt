package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.local.YarmoukDatabase
import com.example.data.local.entity.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class YarmoukViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        YarmoukDatabase::class.java,
        "yarmouk_db"
    ).fallbackToDestructiveMigration().build()

    private val dao = db.coreDao()

    val students = dao.getAllStudents().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val memos = dao.getAllMemos().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val expenses = dao.getAllExpenses().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    
    fun getPlans(type: String) = dao.getPlans(type).stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        // Pre-populate if empty
        viewModelScope.launch {
            students.collect { list ->
                if (list.isEmpty()) {
                    dao.insertStudent(StudentEntity(name = "أحمد خالد", gradeLevel = "الأول الثانوي", attendanceStatus = "حاضر", score = "95"))
                    dao.insertStudent(StudentEntity(name = "سالم عمر", gradeLevel = "الثاني الثانوي", attendanceStatus = "غائب", score = "80"))
                    dao.insertStudent(StudentEntity(name = "محمد علي", gradeLevel = "الثالث الثانوي", attendanceStatus = "حاضر", score = "100"))
                }
            }
        }
    }

    fun updateStudentAttendance(student: StudentEntity, isPresent: Boolean) {
        viewModelScope.launch {
            dao.updateStudent(student.copy(attendanceStatus = if (isPresent) "حاضر" else "غائب"))
        }
    }
    
    fun updateStudentScore(student: StudentEntity, score: String) {
        viewModelScope.launch {
            dao.updateStudent(student.copy(score = score))
        }
    }

    fun addMemo(title: String, content: String, date: String) {
        viewModelScope.launch {
            dao.insertMemo(MemoEntity(title = title, content = content, date = date))
        }
    }

    fun addExpense(type: String, amount: Double, description: String, date: String) {
        viewModelScope.launch {
            dao.insertExpense(ExpenseEntity(type = type, amount = amount, description = description, date = date))
        }
    }
    
    fun addPlan(type: String, title: String, description: String, date: String) {
        viewModelScope.launch {
            dao.insertPlan(PlanEntity(type = type, title = title, description = description, date = date))
        }
    }
}
