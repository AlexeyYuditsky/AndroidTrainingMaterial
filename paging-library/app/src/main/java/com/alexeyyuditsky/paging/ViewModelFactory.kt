package com.alexeyyuditsky.paging

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner

typealias ViewModelCreator<VM> = () -> VM

class ViewModelFactory<VM : ViewModel>(
    private val viewModelCreator: ViewModelCreator<VM>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return viewModelCreator() as T
    }
}

inline fun <reified VM : ViewModel> ComponentActivity.viewModelCreator(noinline creator: ViewModelCreator<VM>): Lazy<VM> {
    return viewModels { ViewModelFactory(creator) }
}

