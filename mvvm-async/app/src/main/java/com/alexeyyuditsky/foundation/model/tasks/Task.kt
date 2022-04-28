package com.alexeyyuditsky.foundation.model.tasks

import com.alexeyyuditsky.foundation.model.FinalResult

typealias TaskListener<T> = (FinalResult<T>) -> Unit

interface Task<T> {

    fun await(): T

    /**
     * Listeners are called in main thread
     */
    fun enqueue(listener: TaskListener<T>)

    fun cancel()

}