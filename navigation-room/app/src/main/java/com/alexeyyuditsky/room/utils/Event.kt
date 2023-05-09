package com.alexeyyuditsky.room.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Event<T>(
    private var value: T? = null
) {
    fun get(): T? = value.apply { value = null }
}

typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

fun <T> MutableLiveData<T>.share(): LiveData<T> = this

fun <T> MutableLiveEvent<T>.publishEvent(value: T) {
    this.value = Event(value)
}

fun <T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, listener: (T) -> Unit) {
    this.observe(lifecycleOwner) {
        it?.get()?.let { value ->
            listener(value)
        }
    }
}

fun LiveData<Event<Unit>>.observeEvent(lifecycleOwner: LifecycleOwner, listener: () -> Unit) {
    observeEvent(lifecycleOwner) { _ ->
        listener()
    }
}