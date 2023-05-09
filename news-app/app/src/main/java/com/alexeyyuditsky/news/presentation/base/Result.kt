package com.alexeyyuditsky.news.presentation.base

sealed class Result<T> {

    class Success<T>(
        val data: T
    ) : Result<T>()

    class Error<T>(
        val error: String
    ) : Result<T>()

    class Loading<T> : Result<T>()

    class Empty<T> : Result<T>()

}