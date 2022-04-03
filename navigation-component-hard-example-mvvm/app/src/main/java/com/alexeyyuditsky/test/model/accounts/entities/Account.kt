package com.alexeyyuditsky.test.model.accounts.entities

import java.text.SimpleDateFormat
import java.util.*

data class Account(
    val email: String,
    val username: String,
    val createdAt: String = getCurrentDate()
) {

    companion object {
        fun getCurrentDate(): String {
            return SimpleDateFormat("HH:mm, dd.MM.yyyy", Locale.US).format(Date())
        }
    }

}