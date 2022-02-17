package com.alexeyyuditsky.test.foundation.model

sealed class Result<T>

class PendingResult<T> : Result<T>()

class ErrorResult<T>(
    val exception: Exception
) : Result<T>()

class SuccessResult<T>(
    val data: T
) : Result<T>()

fun <T> Result<T>?.takeSuccess(): T? {
    return if (this is SuccessResult)
        this.data
    else
        null
}