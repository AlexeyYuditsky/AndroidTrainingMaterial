package com.alexeyyuditsky.test

import android.content.Context
import androidx.room.Room
import com.alexeyyuditsky.test.model.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Repository {

    private lateinit var applicationContext: Context

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db").build()
    }

    fun init(context: Context) {
        applicationContext = context
    }

}