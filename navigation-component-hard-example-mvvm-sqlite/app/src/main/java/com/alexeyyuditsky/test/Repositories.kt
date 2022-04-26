package com.alexeyyuditsky.test

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import com.alexeyyuditsky.test.model.accounts.SQLiteAccountsRepository
import com.alexeyyuditsky.test.model.boxes.BoxesRepository
import com.alexeyyuditsky.test.model.boxes.SQLiteBoxesRepository
import com.alexeyyuditsky.test.model.settings.AppSettings
import com.alexeyyuditsky.test.model.settings.SharedPreferencesAppSettings
import com.alexeyyuditsky.test.model.sqlite.AppSQLiteHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Repositories {

    private lateinit var applicationContext: Context

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val database: SQLiteDatabase by lazy {
        AppSQLiteHelper(applicationContext).writableDatabase
    }

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(applicationContext)
    }

    val accountsRepository: AccountsRepository by lazy {
        SQLiteAccountsRepository(database, appSettings, ioDispatcher)
    }

    val boxesRepository: BoxesRepository by lazy {
        SQLiteBoxesRepository(database, accountsRepository, ioDispatcher)
    }

    fun init(context: Context) {
        applicationContext = context
    }

}