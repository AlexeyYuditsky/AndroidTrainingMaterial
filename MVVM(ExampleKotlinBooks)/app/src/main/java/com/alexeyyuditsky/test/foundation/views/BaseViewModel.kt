package com.alexeyyuditsky.test.foundation.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.test.foundation.model.PendingResult
import com.alexeyyuditsky.test.foundation.model.Result
import com.alexeyyuditsky.test.foundation.model.tasks.Task
import com.alexeyyuditsky.test.foundation.model.tasks.TaskListener

typealias MutableLiveResult<T> = MutableLiveData<Result<T>>
typealias LiveResult<T> = LiveData<Result<T>>

/** Base class for all view-models. */
open class BaseViewModel : ViewModel() {

    private val tasks = mutableListOf<Task<*>>()

    override fun onCleared() {
        super.onCleared()
        tasks.forEach { it.cancel() }
        tasks.clear()
    }

    /** Override this method in child classes if you want to listen for results from other screens */
    open fun onResult(result: Any) {}

    fun <T> Task<T>.safeEnqueue(listener: TaskListener<T>? = null) {
        tasks.add(this)
        this.enqueue {
            tasks.remove(this)
            listener?.invoke(it)
        }
    }

    fun <T> Task<T>.into(liveResult: MutableLiveResult<T>) {
        liveResult.value = PendingResult()
        this.safeEnqueue {
            liveResult.value = it
        }
    }

}