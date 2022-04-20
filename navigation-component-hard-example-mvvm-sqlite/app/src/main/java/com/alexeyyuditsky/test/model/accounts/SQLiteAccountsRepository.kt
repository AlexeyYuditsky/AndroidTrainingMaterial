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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class SQLiteAccountsRepository(
    private val db: SQLiteDatabase,
    private val appSettings: AppSettings,
    /* private val ioDispatcher: CoroutineDispatcher*/
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
        val currentAccountId = currentAccountIdFlow.value
        val account = Account(
            id = 1,
            email = "admin",
            username = "admin"
        )
        return flowOf(account)
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccountUsername(newUsername: String) {
        TODO("Not yet implemented")
    }

    private fun createAccount(signUpData: SignUpData) {
        try {
            db.insertOrThrow(
                AccountsTable.TABLE_NAME,
                null,
                contentValuesOf(
                    AccountsTable.COLUMN_EMAIL to signUpData.email,
                    AccountsTable.COLUMN_USERNAME to signUpData.username,
                    AccountsTable.COLUMN_PASSWORD to signUpData.password,
                    AccountsTable.COLUMN_CREATED_AT to signUpData.createdAt
                )
            )
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
                    "WHERE email = ?",
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