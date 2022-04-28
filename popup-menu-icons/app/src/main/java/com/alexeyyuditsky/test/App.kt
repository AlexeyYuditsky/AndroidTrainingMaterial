package com.alexeyyuditsky.test

import android.app.Application
import com.alexeyyuditsky.test.model.UsersService

class App : Application() {
    val usersService = UsersService()
}