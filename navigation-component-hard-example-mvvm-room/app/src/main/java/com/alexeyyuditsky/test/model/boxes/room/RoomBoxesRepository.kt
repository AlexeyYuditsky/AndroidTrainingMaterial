package com.alexeyyuditsky.test.model.boxes.room

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import com.alexeyyuditsky.test.model.boxes.BoxesRepository
import com.alexeyyuditsky.test.model.boxes.entities.Box
import com.alexeyyuditsky.test.model.sqlite.AccountsBoxesSettingsTable
import com.alexeyyuditsky.test.model.sqlite.BoxesTable
import com.alexeyyuditsky.test.model.sqlite.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class RoomBoxesRepository(
    private val db: SQLiteDatabase,
    private val accountsRepository: AccountsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BoxesRepository {

    private val reconstructFlow = MutableSharedFlow<Unit>(replay = 1).also { it.tryEmit(Unit) }

    override suspend fun getBoxes(onlyActive: Boolean): Flow<List<Box>> {
        return combine(accountsRepository.getAccount(), reconstructFlow) { account, _ ->
            queryBoxes(onlyActive, account?.id)
        }.flowOn(ioDispatcher)
    }

    override suspend fun activateBox(box: Box) = wrapSQLiteException(ioDispatcher) {
        setActivateFlagForBox(box, true)
    }

    override suspend fun deactivateBox(box: Box) = wrapSQLiteException(ioDispatcher) {
        setActivateFlagForBox(box, false)
    }

    private suspend fun setActivateFlagForBox(box: Box, isActive: Boolean) {
        val account = accountsRepository.getAccount().first()!!
        saveActiveFlag(account.id, box.id, isActive)
        reconstructFlow.emit(Unit)
    }

    private fun saveActiveFlag(accountId: Long, boxId: Long, isActive: Boolean) {
        val sql = "REPLACE INTO ${AccountsBoxesSettingsTable.TABLE_NAME} " +
                "(${AccountsBoxesSettingsTable.COLUMN_ACCOUNT_ID}, " +
                "${AccountsBoxesSettingsTable.COLUMN_BOX_ID}, " +
                "${AccountsBoxesSettingsTable.COLUMN_IS_ACTIVE}) " +
                "VALUES (?, ?, ?)"
        val cursor = db.rawQuery(sql, arrayOf(accountId.toString(), boxId.toString(), if (isActive) "1" else "0"))
        cursor.moveToFirst()
        cursor.close()
    }

    private fun queryBoxes(onlyActive: Boolean, accountId: Long?): List<Box> {
        if (accountId == null) return emptyList()

        val cursor = queryBoxes(onlyActive, accountId)
        return cursor.use {
            val list = mutableListOf<Box>()
            while (cursor.moveToNext()) {
                list.add(parseBox(cursor))
            }
            return@use list
        }
    }

    private fun parseBox(cursor: Cursor): Box {
        return Box(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(BoxesTable.COLUMN_ID)),
            colorName = cursor.getString(cursor.getColumnIndexOrThrow(BoxesTable.COLUMN_COLOR_NAME)),
            colorValue = Color.parseColor(cursor.getString(cursor.getColumnIndexOrThrow(BoxesTable.COLUMN_COLOR_VALUE)))
        )
    }

    private fun queryBoxes(onlyActive: Boolean, accountId: Long): Cursor {
        return if (onlyActive) {
            val sql = "SELECT ${BoxesTable.TABLE_NAME}.* " +
                    "FROM ${BoxesTable.TABLE_NAME} " +
                    "LEFT JOIN ${AccountsBoxesSettingsTable.TABLE_NAME} " +
                    "ON ${BoxesTable.COLUMN_ID} = ${AccountsBoxesSettingsTable.COLUMN_BOX_ID} " +
                    "AND ${AccountsBoxesSettingsTable.COLUMN_ACCOUNT_ID} = ? " +
                    "WHERE ${AccountsBoxesSettingsTable.COLUMN_IS_ACTIVE} IS NULL " +
                    "OR ${AccountsBoxesSettingsTable.COLUMN_IS_ACTIVE} = 1"
            db.rawQuery(sql, arrayOf(accountId.toString()))
        } else {
            val sql = "SELECT * FROM ${BoxesTable.TABLE_NAME}"
            db.rawQuery(sql, null)
        }
    }

}