package com.alexeyyuditsky.test.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppSQLiteHelper(
    private val applicationContext: Context
) : SQLiteOpenHelper(applicationContext, "database.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val sqlQueriesList = applicationContext.assets.open("db_init.sql").bufferedReader().use { file ->
            file.readText().split(';').map { it.trim() }
        }
        sqlQueriesList.forEach {
            db.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}