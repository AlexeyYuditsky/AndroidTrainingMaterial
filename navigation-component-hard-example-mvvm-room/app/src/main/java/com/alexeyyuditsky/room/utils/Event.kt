package com.alexeyyuditsky.room.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Event<T>(
    private var _value: T?
) {
    fun get(): T? = _value?.apply { _value = null }
}

/**
 * Convert mutable live-data into non-mutable live-data.
 */
fun <T> MutableLiveData<T>.share(): LiveData<T> = this

// type aliases for live-data instances which contain events
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>
typealias LiveEvent<T> = LiveData<Event<T>>
typealias EventListener<T> = (T) -> Unit

fun <T> MutableLiveEvent<T>.publishEvent(value: T) {
    this.value = Event(value)
}

fun <T> LiveEvent<T>.observeEvent(lifecycleOwner: LifecycleOwner, listener: EventListener<T>) {
    this.observe(lifecycleOwner) {
        it?.get()?.let { value ->
            listener(value)
        }
    }
}

// unit events:
typealias UnitLiveEvent = LiveEvent<Unit>
typealias UnitEventListener = () -> Unit

fun UnitLiveEvent.observeEvent(lifecycleOwner: LifecycleOwner, listener: UnitEventListener) {
    observeEvent(lifecycleOwner) { _ ->
        listener()
    }
}