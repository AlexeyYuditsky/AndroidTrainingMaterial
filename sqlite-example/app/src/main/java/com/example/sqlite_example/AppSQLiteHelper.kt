package com.example.sqlite_example

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppSQLiteHelper(
    private val applicationContext: Context
) : SQLiteOpenHelper(applicationContext, "database.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        /*db.execSQL(
            """
            CREATE TABLE "users"(
            	"id" INTEGER PRIMARY KEY AUTOINCREMENT,
            	"name" TEXT NOT NULL COLLATE NOCASE,
            	"age" INTEGER NOT NULL,
            	"created_at" TEXT NOT NULL)
            """.trimIndent()
        )

        db.execSQL(
            """
            INSERT INTO "users"
                ("name", "age", "created_at")
            VALUES
                ("alex", "26", "16.06.2023"),
                ("ivan", "28", "15.06.2023")
            """.trimIndent()
        )*/

        /*applicationContext.assets.open("db_init")
            .bufferedReader()
            .use { it.readText() }
            .split(';')
            .forEach { db.execSQL(it) }*/
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

}