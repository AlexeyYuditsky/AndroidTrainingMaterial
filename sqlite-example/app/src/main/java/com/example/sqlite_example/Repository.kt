package com.example.sqlite_example

import android.content.Context
import android.database.sqlite.SQLiteDatabase

object Repository {

    private lateinit var appContext: Context

    val database: SQLiteDatabase by lazy { AppSQLiteHelper(appContext).writableDatabase }

    fun init(applicationContext: Context) {
        appContext = applicationContext
    }

}