package com.alexeyyuditsky.test.model.accounts.entities

import com.alexeyyuditsky.test.model.EmptyFieldException
import com.alexeyyuditsky.test.model.Field
import com.alexeyyuditsky.test.model.PasswordMismatchException

class SignUpData(
    val email: String,
    val username: String,
    val password: String,
    val repeatPassword: String
) {
    fun validate() {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (username.isBlank()) throw EmptyFieldException(Field.Username)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        if (repeatPassword.isBlank()) throw PasswordMismatchException()
    }
}