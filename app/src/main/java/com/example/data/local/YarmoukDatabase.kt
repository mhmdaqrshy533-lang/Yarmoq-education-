package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.*

@Database(entities = [StudentEntity::class, MemoEntity::class, ExpenseEntity::class, PlanEntity::class], version = 2, exportSchema = false)
abstract class YarmoukDatabase : RoomDatabase() {
    abstract fun coreDao(): CoreDao
}
