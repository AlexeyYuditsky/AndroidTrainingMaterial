package com.alexeyyuditsky.test.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexeyyuditsky.test.model.employees.room.entities.EmployeeDbEntity
import com.alexeyyuditsky.test.model.employees.room.EmployeesDao

@Database(
    version = 1,
    entities = [
        EmployeeDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getEmployeesDao(): EmployeesDao

}