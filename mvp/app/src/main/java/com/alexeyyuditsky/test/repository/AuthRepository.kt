package com.alexeyyuditsky.test.repository

import kotlinx.coroutines.Deferred

interface AuthRepository {

    suspend fun login(email: String, password: String): Deferred<String>

}