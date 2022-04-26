package com.alexeyyuditsky.test.model.accounts

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.core.content.contentValuesOf
import com.alexeyyuditsky.test.model.AccountAlreadyExistsException
import com.alexeyyuditsky.test.model.AuthException
import com.alexeyyuditsky.test.model.EmptyFieldException
import com.alexeyyuditsky.test.model.Field
import com.alexeyyuditsky.test.model.accounts.entities.Account
import com.alexeyyuditsky.test.model.accounts.entities.SignUpData
import com.alexeyyuditsky.test.model.settings.AppSettings
import com.alexeyyuditsky.test.model.sqlite.AccountsTable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class SQLiteAccountsRepository(
    private val db: SQLiteDatabase,
    private val appSettings: AppSettings
) : AccountsRepository {

    private val currentAccountIdFlow = MutableStateFlow(AccountId(appSettings.getCurrentAccountId()))

    override suspend fun isSignedIn(): Boolean {
        delay(1000)
        return appSettings.getCurrentAccountId() != AppSettings.NO_ACCOUNT_ID
    }

    override suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        delay(1000)
        val accountId = findAccountByEmailAndPassword(email, password)
        appSettings.setCurrentAccountId(accountId)
        currentAccountIdFlow.value = AccountId(accountId)
    }

    override suspend fun signUp(signUpData: SignUpData) {
        signUpData.validate()
        delay(1000)
        createAccount(signUpData)
    }

    override fun getAccount(): Flow<Account?> {
        return currentAccountIdFlow.map { accountId ->
            getAccountById(accountId.value)
        }
    }

    override fun logout() {
        appSettings.setCurrentAccountId(AppSettings.NO_ACCOUNT_ID)
        currentAccountIdFlow.value = AccountId(AppSettings.NO_ACCOUNT_ID)
    }

    override suspend fun updateAccountUsername(newUsername: String) {
        if (newUsername.isBlank()) throw EmptyFieldException(Field.Username)
        delay(1000)
        val accountId = appSettings.getCurrentAccountId()
        updateUsernameForAccountId(accountId, newUsername)
        currentAccountIdFlow.value = AccountId(accountId)
    }

    private fun updateUsernameForAccountId(accountId: Long, newUsername: String) {
        val sql = "UPDATE ${AccountsTable.TABLE_NAME} " +
                "SET ${AccountsTable.COLUMN_USERNAME} = ? " +
                "WHERE ${AccountsTable.COLUMN_ID} = ?"
        val cursor = db.rawQuery(sql, arrayOf(newUsername, accountId.toString()))
        cursor.moveToFirst()
        cursor.close()
    }

    private fun getAccountById(accountId: Long): Account? {
        if (accountId == AppSettings.NO_ACCOUNT_ID) return null

        val sql = "SELECT * FROM ${AccountsTable.TABLE_NAME} " +
                "WHERE ${AccountsTable.COLUMN_ID} = ?"
        val cursor = db.rawQuery(sql, arrayOf(accountId.toString()))
        return cursor.use {
            if (cursor.count == 0) return@use null
            cursor.moveToFirst()
            return@use Account(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(AccountsTable.COLUMN_ID)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(AccountsTable.COLUMN_EMAIL)),
                username = cursor.getString(cursor.getColumnIndexOrThrow(AccountsTable.COLUMN_USERNAME)),
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(AccountsTable.COLUMN_CREATED_AT))
            )
        }
    }

    private fun createAccount(signUpData: SignUpData) {
        try {
            val sql = "INSERT INTO ${AccountsTable.TABLE_NAME} " +
                    "(${AccountsTable.COLUMN_EMAIL}, ${AccountsTable.COLUMN_USERNAME}, " +
                    "${AccountsTable.COLUMN_PASSWORD}, ${AccountsTable.COLUMN_CREATED_AT}) " +
                    "VALUES (?, ?, ?, ?)"
            val cursor = db.rawQuery(
                sql,
                arrayOf(signUpData.email, signUpData.username, signUpData.password, signUpData.createdAt)
            )
            cursor.moveToFirst()
            cursor.close()
        } catch (e: SQLiteConstraintException) {
            val appExtension = AccountAlreadyExistsException()
            appExtension.initCause(e)
            throw appExtension
        }
    }

    private fun findAccountByEmailAndPassword(email: String, password: String): Long {
        val cursor = db.rawQuery(
            "SELECT ${AccountsTable.COLUMN_ID}, ${AccountsTable.COLUMN_PASSWORD} " +
                    "FROM ${AccountsTable.TABLE_NAME} " +
                    "WHERE ${AccountsTable.COLUMN_EMAIL} = ?",
            arrayOf(email)
        )
        return cursor.use {
            if (cursor.count == 0) throw AuthException()
            cursor.moveToFirst()

            val accountPassword = cursor.getString(cursor.getColumnIndexOrThrow(AccountsTable.COLUMN_PASSWORD))
            if (accountPassword != password) throw AuthException()

            cursor.getLong(cursor.getColumnIndexOrThrow(AccountsTable.COLUMN_ID))
        }
    }

    private class AccountId(val value: Long)

}