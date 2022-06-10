package com.alexeyyuditsky.test.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexeyyuditsky.test.model.EmployeeDbEntity
import com.alexeyyuditsky.test.model.EmployeesDao

@Database(
    version = 1,
    entities = [
        EmployeeDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getEmployeesDao(): EmployeesDao

}