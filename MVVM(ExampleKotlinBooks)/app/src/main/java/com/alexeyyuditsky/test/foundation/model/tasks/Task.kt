package com.alexeyyuditsky.test.foundation.model.tasks

import com.alexeyyuditsky.test.foundation.model.FinalResult

typealias TaskListener<T> = (FinalResult<T>) -> Unit

interface Task<T> {

    fun await(): T

    fun enqueue(listener: TaskListener<T>)

    fun cancel()

}