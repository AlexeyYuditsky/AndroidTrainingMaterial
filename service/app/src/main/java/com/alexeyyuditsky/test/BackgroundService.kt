package com.alexeyyuditsky.test

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class BackgroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MyLog", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyLog", "onStartCommand")
        someTask()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLog", "onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("MyLog", "onBind")
        return null
    }

    private fun someTask() {
        Thread {
            repeat(5) {
                Log.d("MyLog", it.toString())
                Thread.sleep(1000)
            }
            stopSelf()
        }.start()
    }

}