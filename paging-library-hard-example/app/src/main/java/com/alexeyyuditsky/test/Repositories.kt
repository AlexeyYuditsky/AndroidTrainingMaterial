package com.alexeyyuditsky.test

import android.content.Context
import androidx.room.Room
import com.alexeyyuditsky.test.model.room.AppDatabase
import com.alexeyyuditsky.test.model.employees.EmployeesRepository
import com.alexeyyuditsky.test.model.employees.room.RoomEmployeesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Repositories {

    private lateinit var applicationContext: Context

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db").build()
    }

    val employeesRepository: EmployeesRepository by lazy {
        RoomEmployeesRepository(database.getEmployeesDao(), ioDispatcher)
    }

    fun init(context: Context) {
        applicationContext = context
    }

}