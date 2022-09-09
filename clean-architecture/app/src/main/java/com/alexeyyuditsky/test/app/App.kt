package com.alexeyyuditsky.test.app

import android.app.Application
import com.alexeyyuditsky.test.di.appModule
import com.alexeyyuditsky.test.di.dataModule
import com.alexeyyuditsky.test.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }

}