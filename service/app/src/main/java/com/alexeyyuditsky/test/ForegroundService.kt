package com.alexeyyuditsky.test

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi

class ForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MyLog", "onCreate")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyLog", "onStartCommand")

        val CHANNELID = "Foreground Service ID"
        val channel = NotificationChannel(CHANNELID, CHANNELID, NotificationManager.IMPORTANCE_LOW)

        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification: Notification.Builder = Notification.Builder(this, CHANNELID)
            .setContentText("Service is running")
            .setContentTitle("Service enabled")
            .setSmallIcon(R.drawable.ic_launcher_background)

        startForeground(1001, notification.build())

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
        }.start()
    }

}