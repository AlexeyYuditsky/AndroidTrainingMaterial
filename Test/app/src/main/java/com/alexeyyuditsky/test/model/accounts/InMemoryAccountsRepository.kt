package com.alexeyyuditsky.test.model.accounts

import com.alexeyyuditsky.test.model.accounts.entities.Account
import com.alexeyyuditsky.test.model.accounts.entities.AccountRecord
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

    override fun isSignedIn(): Boolean {
        return false
    }

}