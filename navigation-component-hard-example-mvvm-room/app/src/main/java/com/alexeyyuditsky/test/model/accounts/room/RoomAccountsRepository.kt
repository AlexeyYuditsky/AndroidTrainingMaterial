package com.alexeyyuditsky.test.model.accounts.room

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import com.alexeyyuditsky.test.model.AccountAlreadyExistsException
import com.alexeyyuditsky.test.model.AuthException
import com.alexeyyuditsky.test.model.EmptyFieldException
import com.alexeyyuditsky.test.model.Field
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import com.alexeyyuditsky.test.model.accounts.entities.Account
import com.alexeyyuditsky.test.model.accounts.entities.SignUpData
import com.alexeyyuditsky.test.model.accounts.room.entities.AccountDbEntity
import com.alexeyyuditsky.test.model.accounts.room.entities.AccountSignInTuple
import com.alexeyyuditsky.test.model.accounts.room.entities.AccountUpdateUsernameTuple
import com.alexeyyuditsky.test.model.settings.AppSettings
import com.alexeyyuditsky.test.model.sqlite.AccountsTable
import com.alexeyyuditsky.test.model.sqlite.wrapSQLiteException
import com.alexeyyuditsky.test.utils.AsyncLoader
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class RoomAccountsRepository(
    private val accountsDao: AccountsDao,
    private val appSettings: AppSettings,
    private val ioDispatcher: CoroutineDispatcher
) : AccountsRepository {

    private val currentAccountIdFlow = AsyncLoader {
        MutableStateFlow(AccountId(appSettings.getCurrentAccountId()))
    }

    override suspend fun isSignedIn(): Boolean {
        delay(1000)
        return appSettings.getCurrentAccountId() != AppSettings.NO_ACCOUNT_ID
    }

    override suspend fun signIn(email: String, password: String) = wrapSQLiteException(ioDispatcher) {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)

        delay(1000)
        val accountId = findAccountByEmailAndPassword(email, password)
        appSettings.setCurrentAccountId(accountId)
        currentAccountIdFlow.get().value = AccountId(accountId)
    }

    override suspend fun signUp(signUpData: SignUpData) = wrapSQLiteException(ioDispatcher) {
        signUpData.validate()
        delay(1000)
        createAccount(signUpData)
    }

    override suspend fun getAccount(): Flow<Account?> {
        return currentAccountIdFlow.get()
            .flatMapLatest { accountId ->
                if (accountId.value == AppSettings.NO_ACCOUNT_ID) {
                    flowOf(null)
                } else {
                    getAccountById(accountId.value)
                }
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun logout() {
        appSettings.setCurrentAccountId(AppSettings.NO_ACCOUNT_ID)
        currentAccountIdFlow.get().value = AccountId(AppSettings.NO_ACCOUNT_ID)
    }

    override suspend fun updateAccountUsername(newUsername: String) = wrapSQLiteException(ioDispatcher) {
        if (newUsername.isBlank()) throw EmptyFieldException(Field.Username)

        delay(1000)
        val accountId = appSettings.getCurrentAccountId()
        updateUsernameForAccountId(accountId, newUsername)
        currentAccountIdFlow.get().value = AccountId(accountId)
    }

    private suspend fun updateUsernameForAccountId(accountId: Long, newUsername: String) {
        accountsDao.updateUsername(AccountUpdateUsernameTuple(accountId, newUsername))
    }

    private fun getAccountById(accountId: Long): Flow<Account?> {
        return accountsDao.getById(accountId).map { it?.toAccount() }
    }

    private suspend fun createAccount(signUpData: SignUpData) {
        try {
            val accountDbEntity = AccountDbEntity.fromSignUpData(signUpData)
            accountsDao.createAccount(accountDbEntity)
        } catch (e: SQLiteConstraintException) {
            val appExtension = AccountAlreadyExistsException()
            appExtension.initCause(e)
            throw appExtension
        }
    }

    private suspend fun findAccountByEmailAndPassword(email: String, password: String): Long {
        val tuple: AccountSignInTuple = accountsDao.findByEmail(email) ?: throw AuthException()
        if (tuple.password != password) throw AuthException()
        return tuple.id
    }

    private class AccountId(val value: Long)

}