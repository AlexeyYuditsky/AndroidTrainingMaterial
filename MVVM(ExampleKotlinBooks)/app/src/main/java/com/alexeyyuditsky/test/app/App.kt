package com.alexeyyuditsky.test.app

import android.app.Application
import com.alexeyyuditsky.test.app.model.InMemoryBooksRepository
import com.alexeyyuditsky.test.foundation.model.coroutines.IoDispatcher
import com.alexeyyuditsky.test.foundation.model.coroutines.DefaultDispatcher
import com.alexeyyuditsky.test.foundation.BaseApplication

/**
 * Here we store instances of model layer classes.
 */
class App : Application(), BaseApplication {

    private val ioDispatcher = IoDispatcher() // for IO operations
    private val defaultDispatcher = DefaultDispatcher() // for CPU-intensive operations

    /**
     * Place your singleton scope dependencies here
     */
    override val singletonScopeDependencies = listOf(
        InMemoryBooksRepository(ioDispatcher),
    )

}