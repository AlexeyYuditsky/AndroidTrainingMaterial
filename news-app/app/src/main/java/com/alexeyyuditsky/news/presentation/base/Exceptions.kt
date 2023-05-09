package com.alexeyyuditsky.news.presentation.base

open class AppException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class ConnectionException(
    cause: Throwable
) : AppException(cause = cause)

class ParseBackendResponseException(
    cause: Throwable
) : AppException(cause = cause)

class BackendException(
    val code: Int,
    message: String
) : AppException(message)