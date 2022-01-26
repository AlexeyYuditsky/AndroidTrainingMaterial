package com.alexeyyuditsky.test.interfaces

import androidx.annotation.StringRes
import com.alexeyyuditsky.test.model.User

interface Navigator {

    fun showDetails(user: User)

    fun goBack()

    fun toast(@StringRes messageRes: Int)

}