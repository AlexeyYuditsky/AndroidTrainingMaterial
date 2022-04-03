package com.alexeyyuditsky.test.model.accounts

interface AccountsRepository {

    suspend fun isSignedIn(): Boolean

}