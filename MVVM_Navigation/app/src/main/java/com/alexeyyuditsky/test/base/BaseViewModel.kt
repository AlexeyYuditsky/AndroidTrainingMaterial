package com.alexeyyuditsky.test.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() { // базовый класс для всех viewModel

    open fun onResult(result: Any) {}

}