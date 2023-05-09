package com.alexeyyuditsky.room.utils

import android.text.Editable

fun Editable?.toCharArray(): CharArray {
    if (this == null) return CharArray(0)
    return CharArray(length) { this[it] }
}