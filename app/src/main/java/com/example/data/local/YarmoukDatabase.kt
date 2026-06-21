package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.StudentEntity

@Database(entities = [StudentEntity::class], version = 1, exportSchema = false)
abstract class YarmoukDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
}
