package com.alexeyyuditsky.test.model.accounts

import com.alexeyyuditsky.test.model.accounts.entities.Account
import com.alexeyyuditsky.test.model.accounts.entities.SignUpData
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    suspend fun isSignedIn(): Boolean

    suspend fun signIn(email: String, password: String)

    suspend fun signUp(signUpData: SignUpData)

    fun getAccount(): Flow<Account?>

}