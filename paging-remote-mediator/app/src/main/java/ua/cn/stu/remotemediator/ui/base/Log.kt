package ua.cn.stu.remotemediator.ui.base

import android.util.Log

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}