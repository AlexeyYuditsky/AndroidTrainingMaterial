package com.alexeyyuditsky.test.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexeyyuditsky.test.model.accounts.room.AccountsDao
import com.alexeyyuditsky.test.model.accounts.room.entities.AccountDbEntity
import com.alexeyyuditsky.test.model.boxes.room.entities.AccountBoxSettingsDbEntity
import com.alexeyyuditsky.test.model.boxes.room.entities.BoxDbEntity

@Database(
    version = 1,
    entities = [
        AccountDbEntity::class,
        BoxDbEntity::class,
        AccountBoxSettingsDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

}