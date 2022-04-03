package com.alexeyyuditsky.test

import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import com.alexeyyuditsky.test.model.accounts.InMemoryAccountsRepository

object Repositories {

    val accountsRepository: AccountsRepository = InMemoryAccountsRepository()

}