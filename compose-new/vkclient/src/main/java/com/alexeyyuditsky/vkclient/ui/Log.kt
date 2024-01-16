package com.alexeyyuditsky.vkclient.ui

import android.util.Log

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}