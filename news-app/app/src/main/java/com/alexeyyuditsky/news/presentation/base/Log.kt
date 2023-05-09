package com.alexeyyuditsky.news.presentation.base

import android.util.Log

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}