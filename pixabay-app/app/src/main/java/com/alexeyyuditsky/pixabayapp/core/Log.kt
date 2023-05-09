package com.alexeyyuditsky.pixabayapp.core

import android.util.Log

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}