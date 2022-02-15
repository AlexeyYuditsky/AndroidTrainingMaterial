package com.alexeyyuditsky.test.foundation.uiactions

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/** Android implementation of [UIActions]. Displaying simple toast message and getting string from resources. */
class AndroidUIActions(
    private val appContext: Context
) : UIActions {

    override fun toast(message: String) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int, vararg args: Any): String {
        return appContext.getString(messageRes, *args)
    }

}