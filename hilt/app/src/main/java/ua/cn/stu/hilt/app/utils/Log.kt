package ua.cn.stu.hilt.app.utils

import android.util.Log

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}