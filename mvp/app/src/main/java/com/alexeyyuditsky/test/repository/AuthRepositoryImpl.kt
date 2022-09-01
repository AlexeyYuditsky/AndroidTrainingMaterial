package com.alexeyyuditsky.test.repository

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {

    override suspend fun login(email: String, password: String):Deferred<String> {
        return GlobalScope.async {
            delay(1000)
            ""
        }
    }

}