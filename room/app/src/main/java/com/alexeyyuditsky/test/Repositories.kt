package com.alexeyyuditsky.test

import android.content.Context
import androidx.room.Room
import com.alexeyyuditsky.test.room.AppDataBase

object Repositories {

    private lateinit var applicationContext: Context

    val database: AppDataBase by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, "database.db")
            .createFromAsset("initial_database.db")
            .build()
    }

    fun init(context: Context) {
        applicationContext = context
    }

}