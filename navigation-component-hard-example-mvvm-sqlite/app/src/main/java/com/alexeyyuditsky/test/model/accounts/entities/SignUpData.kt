package com.alexeyyuditsky.test.model.accounts.entities

import com.alexeyyuditsky.test.model.EmptyFieldException
import com.alexeyyuditsky.test.model.Field
import com.alexeyyuditsky.test.model.PasswordMismatchException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fields that should be provided during creating a new account.
 */
class SignUpData(
    val email: String,
    val username: String,
    val password: String,
    val repeatPassword: String,
    val createdAt: String = getCurrentDate()
) {

    fun validate() {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (username.isBlank()) throw EmptyFieldException(Field.Username)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        if (repeatPassword != password) throw PasswordMismatchException()
    }

    companion object {
        fun getCurrentDate(): String {
            return SimpleDateFormat("dd.MM.yyyy", Locale.US).format(Date())
        }
    }

}