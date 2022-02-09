package com.alexeyyuditsky.foundation.uiactions

import androidx.annotation.StringRes

/** Common actions that can be performed in the view-model */
interface UIActions {

    /** Display a simple toast message. */
    fun toast(message: String)

    /** Get string resource content by its identifier. */
    fun getString(@StringRes messageRes: Int, vararg args: Any): String

}