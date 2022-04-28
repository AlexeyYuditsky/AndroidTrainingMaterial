package com.alexeyyuditsky.foundation.model.tasks

import com.alexeyyuditsky.foundation.model.Repository

typealias TaskBody<T> = () -> T

interface TasksFactory : Repository{

    fun <T> async(body: TaskBody<T>): Task<T>

}