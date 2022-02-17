package com.alexeyyuditsky.test.foundation.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.test.foundation.model.Result

typealias MutableLiveResult<T> = MutableLiveData<Result<T>>
typealias LiveResult<T> = LiveData<Result<T>>
typealias MediatorLiveResult<T> = MediatorLiveData<Result<T>>


/** Base class for all view-models. */
open class BaseViewModel : ViewModel() {

    /** Override this method in child classes if you want to listen for results from other screens */
    open fun onResult(result: Any) {}

}