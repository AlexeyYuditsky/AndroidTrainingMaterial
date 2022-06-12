package com.alexeyyuditsky.test.utils

class Event<T>(
    private var value: T? = null
) {
    fun get(): T? = value.apply { value = null }
}