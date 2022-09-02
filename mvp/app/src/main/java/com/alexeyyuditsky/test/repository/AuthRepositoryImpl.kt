package com.alexeyyuditsky.test.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {

    override suspend fun login(email: String, password: String): String {
        return withContext(Dispatchers.Default) {
            delay(1000)
            ""
        }
    }

}