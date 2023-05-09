package com.alexeyyuditsky.room.model.room

import android.database.sqlite.SQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import com.alexeyyuditsky.room.model.StorageException

/**
 * Converts any [SQLiteException] into in-app [StorageException]
 */
suspend fun wrapSQLiteException(dispatcher: CoroutineDispatcher, block: suspend CoroutineScope.() -> Unit) {
    try {
        withContext(dispatcher, block)
    } catch (e: SQLiteException) {
        val appException = StorageException()
        appException.initCause(e)
        throw appException
    }
}