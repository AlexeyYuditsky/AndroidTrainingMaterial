package com.alexeyyuditsky.test.sqlite

import android.content.Context

object Repositories {

    private lateinit var applicationContext: Context

    val database by lazy {
        AppSQLiteHelper(applicationContext).writableDatabase
    }

    fun init(context: Context) {
        applicationContext = context
    }

}