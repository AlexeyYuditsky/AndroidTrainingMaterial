package com.alexeyyuditsky.test

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

typealias ResultsListener<T> = (T) -> Unit

fun <T> Fragment.publishResult(key: String, result: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> Fragment.resultListener(key: String, listener: ResultsListener<T>) {
    val liveData = findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
    liveData?.observe(viewLifecycleOwner) { result ->
        if (liveData.value == null) return@observe
        listener(result)
        liveData.value = null
    }
}