package com.alexeyyuditsky.composenew

import android.util.Log

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}