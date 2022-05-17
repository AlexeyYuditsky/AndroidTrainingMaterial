package com.alexeyyuditsky.room

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.alexeyyuditsky.room.model.accounts.AccountsRepository
import com.alexeyyuditsky.room.model.accounts.room.RoomAccountsRepository
import com.alexeyyuditsky.room.model.boxes.BoxesRepository
import com.alexeyyuditsky.room.model.boxes.room.RoomBoxesRepository
import com.alexeyyuditsky.room.model.room.AppDatabase
import com.alexeyyuditsky.room.model.room.MIGRATION_2_3
import com.alexeyyuditsky.room.model.settings.AppSettings
import com.alexeyyuditsky.room.model.settings.SharedPreferencesAppSettings
import com.alexeyyuditsky.room.utils.security.DefaultSecurityUtilsImpl
import com.alexeyyuditsky.room.utils.security.SecurityUtils

object Repositories {

    private lateinit var applicationContext: Context

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("initial_database.db")
            .addMigrations(MIGRATION_2_3)
            .build()
    }

    private val appSettings: AppSettings by lazy { SharedPreferencesAppSettings(applicationContext) }

    val accountsRepository: AccountsRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao(), appSettings, securityUtils, ioDispatcher)
    }

    val boxesRepository: BoxesRepository by lazy {
        RoomBoxesRepository(accountsRepository, database.getBoxesDao(), ioDispatcher)
    }

    val securityUtils: SecurityUtils by lazy { DefaultSecurityUtilsImpl() }

    fun init(context: Context) {
        applicationContext = context
    }

}