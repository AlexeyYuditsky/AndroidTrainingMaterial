package com.alexeyyuditsky.test.model.accounts

interface AccountsRepository {

    suspend fun isSignedIn(): Boolean

    suspend fun signIn(email: String, password: String)

}