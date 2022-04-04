package com.alexeyyuditsky.test.model.accounts

import android.util.Log
import com.alexeyyuditsky.test.model.AuthException
import com.alexeyyuditsky.test.model.EmptyFieldException
import com.alexeyyuditsky.test.model.Field
import com.alexeyyuditsky.test.model.accounts.entities.Account
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryAccountsRepository : AccountsRepository {

    private val currentAccountFlow = MutableStateFlow<Account?>(null)

    private val accounts = mutableListOf(
        AccountRecord(
            account = Account(
                email = "admin",
                username = "admin"
            ),
            password = "123"
        )
    )

    init {
        //currentAccountFlow.value = accounts[0].account
    }

    override suspend fun isSignedIn(): Boolean {
        delay(1000)
        return currentAccountFlow.value != null
    }

    override suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)

        delay(1000)

        val accountRecord = getAccountRecordByEmail(email, password)
        accountRecord?.let {
            currentAccountFlow.value = accountRecord.account
        } ?: throw AuthException()
    }

    private fun getAccountRecordByEmail(email: String, password: String): AccountRecord? {
        return accounts.firstOrNull { it.account.email == email && it.password == password }
    }

    class AccountRecord(
        val account: Account,
        val password: String
    )

}