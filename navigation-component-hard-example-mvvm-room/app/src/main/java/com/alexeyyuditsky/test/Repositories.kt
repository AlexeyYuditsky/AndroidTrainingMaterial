package com.alexeyyuditsky.test

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import com.alexeyyuditsky.test.model.accounts.room.RoomAccountsRepository
import com.alexeyyuditsky.test.model.boxes.BoxesRepository
import com.alexeyyuditsky.test.model.boxes.room.RoomBoxesRepository
import com.alexeyyuditsky.test.model.room.AppDatabase
import com.alexeyyuditsky.test.model.settings.AppSettings
import com.alexeyyuditsky.test.model.settings.SharedPreferencesAppSettings
import com.alexeyyuditsky.test.model.sqlite.AppSQLiteHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Repositories {

    private lateinit var applicationContext: Context

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("initial_database.db")
            .build()
    }

    private val database1: SQLiteDatabase by lazy {
        AppSQLiteHelper(applicationContext).writableDatabase
    }

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(applicationContext)
    }

    val accountsRepository: AccountsRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao(), appSettings, ioDispatcher)
    }

    val boxesRepository: BoxesRepository by lazy {
        RoomBoxesRepository(database1, accountsRepository, ioDispatcher)
    }

    fun init(context: Context) {
        applicationContext = context
    }

}