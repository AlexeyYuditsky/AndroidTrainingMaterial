package com.alexeyyuditsky.test.repository

interface AuthRepository {

    suspend fun login(email: String, password: String): String

}