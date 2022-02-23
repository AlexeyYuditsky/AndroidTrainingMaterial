package com.alexeyyuditsky.simplemvvm

import android.app.Application
import com.alexeyyuditsky.foundation.BaseApplication
import com.alexeyyuditsky.foundation.model.tasks.SimpleTasksFactory
import com.alexeyyuditsky.simplemvvm.model.colors.InMemoryColorsRepository

class App : Application(), BaseApplication {

    private val tasksFactory = SimpleTasksFactory()

    override val repositories = listOf(
        tasksFactory,
        InMemoryColorsRepository(tasksFactory),
    )

}