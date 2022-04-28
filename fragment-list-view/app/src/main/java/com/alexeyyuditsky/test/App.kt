package com.alexeyyuditsky.test

import android.app.Application
import com.alexeyyuditsky.test.model.CatsService

class App : Application() {

    val catsService = CatsService()

}