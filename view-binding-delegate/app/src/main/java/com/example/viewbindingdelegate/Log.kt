package com.example.viewbindingdelegate

import android.util.Log

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}