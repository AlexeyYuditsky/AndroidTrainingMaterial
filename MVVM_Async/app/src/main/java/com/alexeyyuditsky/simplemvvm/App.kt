package com.alexeyyuditsky.simplemvvm

import android.app.Application
import com.alexeyyuditsky.foundation.BaseApplication
import com.alexeyyuditsky.foundation.model.Repository
import com.alexeyyuditsky.simplemvvm.model.colors.InMemoryColorsRepository

/** Here we store instances of model layer classes. */
class App : Application(), BaseApplication {

    /** Place your repositories here, now we have only 1 repository */
    override val repositories = listOf<Repository>(
        InMemoryColorsRepository(),

        )

}