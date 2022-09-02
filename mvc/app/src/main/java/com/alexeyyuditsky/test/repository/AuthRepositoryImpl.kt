package com.alexeyyuditsky.test.repository

import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {

    override suspend fun login(email: String, password: String): String {
        delay(1000)
        return ""
    }

}