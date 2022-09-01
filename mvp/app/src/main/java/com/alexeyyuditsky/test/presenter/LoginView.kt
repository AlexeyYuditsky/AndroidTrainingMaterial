package com.alexeyyuditsky.test.presenter

import androidx.annotation.StringRes

interface LoginView {

    fun showSuccess()

    fun showError(message: String)

    fun showError(@StringRes message: Int)

}