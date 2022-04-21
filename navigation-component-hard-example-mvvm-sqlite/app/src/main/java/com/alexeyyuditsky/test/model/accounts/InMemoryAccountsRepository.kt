package com.alexeyyuditsky.test.model.accounts

import com.alexeyyuditsky.test.model.AccountAlreadyExistsException
import com.alexeyyuditsky.test.model.AuthException
import com.alexeyyuditsky.test.model.EmptyFieldException
import com.alexeyyuditsky.test.model.Field
import com.alexeyyuditsky.test.model.accounts.entities.Account
import com.alexeyyuditsky.test.model.accounts.entities.SignUpData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryAccountsRepository : AccountsRepository {

    private val currentAccountFlow = MutableStateFlow<Account?>(null)

    private val accounts = mutableListOf(
        AccountRecord(
            account = Account(
                id = 0,
                email = "admin",
                username = "admin"
            ),
            password = "123"
        )
    )

    init {
        currentAccountFlow.value = accounts[0].account
    }

    override suspend fun isSignedIn(): Boolean {
        delay(1000)
        return currentAccountFlow.value != null
    }

    override suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)

        delay(1000)

        val accountRecord = getAccountRecordByEmail(email)
        if (accountRecord != null && accountRecord.password == password) {
            currentAccountFlow.value = accountRecord.account
        } else {
            throw AuthException()
        }
    }

    override suspend fun signUp(signUpData: SignUpData) {
        signUpData.validate()

        delay(1000)

        val accountRecord = getAccountRecordByEmail(signUpData.email)
        if (accountRecord != null) throw AccountAlreadyExistsException()
        val newAccount = Account(
            id = 0,
            email = signUpData.email,
            username = signUpData.username
        )
        accounts.add(
            AccountRecord(
                account = newAccount,
                password = signUpData.password
            )
        )
    }

    private fun getAccountRecordByEmail(email: String): AccountRecord? {
        return accounts.firstOrNull { it.account.email == email }
    }

    override fun getAccount(): Flow<Account?> = currentAccountFlow

    override fun logout() {
        currentAccountFlow.value = null
    }

    override suspend fun updateAccountUsername(newUsername: String) {
        if (newUsername.isBlank()) throw EmptyFieldException(Field.Username)

        delay(1000)
        val currentAccount = currentAccountFlow.value ?: throw AuthException()

        val updatedAccount = currentAccount.copy(username = newUsername)
        currentAccountFlow.value = updatedAccount

        val currentRecord = getAccountRecordByEmail(currentAccount.email) ?: throw AuthException()
        currentRecord.account = updatedAccount
    }

    data class AccountRecord(
        var account: Account,
        val password: String
    )

}