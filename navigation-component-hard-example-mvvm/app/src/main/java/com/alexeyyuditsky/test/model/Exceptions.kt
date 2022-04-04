package com.alexeyyuditsky.test.model

abstract class AppException : RuntimeException()

class EmptyFieldException(
    val field: Field
) : AppException()

class AuthException : AppException()

class PasswordMismatchException : AppException()