package com.alexeyyuditsky.test.app

import com.alexeyyuditsky.test.app.model.InMemoryBooksRepository
import com.alexeyyuditsky.test.foundation.SingletonScopeDependencies
import com.alexeyyuditsky.test.foundation.model.coroutines.IoDispatcher

object InitializerDependencies {

    fun initDependencies() = SingletonScopeDependencies.init {
        return@init listOf(InMemoryBooksRepository(IoDispatcher()))
    }

}