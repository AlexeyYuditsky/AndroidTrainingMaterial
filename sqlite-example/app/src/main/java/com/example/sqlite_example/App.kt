package com.example.sqlite_example

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.init(applicationContext)
    }

}