package com.alexeyyuditsky.test.model

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexeyyuditsky.test.model.employees.repositories.room.EmployeesDao
import com.alexeyyuditsky.test.model.employees.repositories.room.EmployeeDbEntity

@Database(
    version = 2,
    entities = [
        EmployeeDbEntity::class
    ],
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2
        )
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getEmployeesDao(): EmployeesDao

}