package com.alexeyyuditsky.test.app

import android.app.Application
import com.alexeyyuditsky.test.app.model.InMemoryBooksRepository
import com.alexeyyuditsky.test.foundation.BaseApplication
import com.alexeyyuditsky.test.foundation.model.Repository
import com.alexeyyuditsky.test.foundation.model.tasks.SimpleTaskFactory
import com.alexeyyuditsky.test.foundation.model.tasks.SimpleTaskFactory.*

class App : Application(), BaseApplication {

    private val tasksFactory = SimpleTaskFactory()

    override val repositories = listOf(
        tasksFactory,
        InMemoryBooksRepository(tasksFactory),
    )

}