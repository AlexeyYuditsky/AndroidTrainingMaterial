package com.alexeyyuditsky.test.utils

class Event<T>(
    private var _value: T?
) {
    fun get(): T? = _value?.apply { _value = null }
}