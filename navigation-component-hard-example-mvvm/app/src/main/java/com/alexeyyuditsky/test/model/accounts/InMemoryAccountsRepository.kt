package com.alexeyyuditsky.test.model.accounts

import com.alexeyyuditsky.test.model.accounts.entities.Account
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryAccountsRepository : AccountsRepository {

    private val currentAccountFlow = MutableStateFlow<Account?>(null)

    private val accounts = mutableListOf(
        AccountRecord(
            account = Account(
                email = "admin@gmail.com",
                username = "admin"
            ),
            password = "123"
        )
    )

    init {
        currentAccountFlow.value = accounts[0].account
    }

    override suspend fun isSignedIn(): Boolean {
        delay(2000)
        return false /* currentAccountFlow.value != null*/
    }

    private class AccountRecord(
        val account: Account,
        val password: String
    )

}