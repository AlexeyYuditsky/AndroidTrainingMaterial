package com.alexeyyuditsky.test.model

import kotlinx.coroutines.delay

interface AuthRepository {

    suspend fun login(email: String, password: String): String

    class Base : AuthRepository {
        override suspend fun login(email: String, password: String): String {
            delay(1000)
            return ""
        }
    }

}