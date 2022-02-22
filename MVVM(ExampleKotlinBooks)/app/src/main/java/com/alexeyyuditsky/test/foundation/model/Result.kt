package com.alexeyyuditsky.test.foundation.model

sealed class Result<T>

sealed class FinalResult<T> : Result<T>()

class PendingResult<T> : Result<T>()

class ErrorResult<T>(
    val exception: Exception
) : FinalResult<T>()

class SuccessResult<T>(
    val data: T
) : FinalResult<T>()

fun <T> Result<T>?.takeSuccess(): T? {
    return if (this is SuccessResult)
        this.data
    else
        null
}