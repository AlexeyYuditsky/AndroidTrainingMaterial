package com.alexeyyuditsky.test.model.accounts

interface AccountsRepository {

    fun isSignedIn(): Boolean

}