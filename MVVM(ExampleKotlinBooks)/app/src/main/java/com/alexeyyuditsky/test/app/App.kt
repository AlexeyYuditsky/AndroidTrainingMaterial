package com.alexeyyuditsky.test.app

import android.app.Application
import com.alexeyyuditsky.test.app.model.InMemoryBooksRepository
import com.alexeyyuditsky.test.foundation.BaseApplication
import com.alexeyyuditsky.test.foundation.model.Repository

class App : Application(), BaseApplication {

    override val repositories = listOf<Repository>(
        InMemoryBooksRepository(),
    )

}