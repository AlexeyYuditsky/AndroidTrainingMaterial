package com.alexeyyuditsky.test

import android.util.Log

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}