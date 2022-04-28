package com.alexeyyuditsky.test

// класс который используется для одиночных событий
class Event<T>(private val value: T) {

    private var handled: Boolean = false

    fun getValue(): T? {
        if (handled) return null
        handled = true
        return value
    }

}