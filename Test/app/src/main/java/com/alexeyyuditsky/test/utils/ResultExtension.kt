package com.alexeyyuditsky.test.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun <T> Fragment.publishResult(key: String, result: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> Fragment.listenResult(key: String, listener: (T) -> Unit) {
    val liveData = findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
    liveData?.observe(viewLifecycleOwner) { result ->
        if (result == null) return@observe
        listener(result)
        liveData.value = null
    }
}