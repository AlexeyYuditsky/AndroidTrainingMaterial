package com.alexeyyuditsky.test.foundation.sideeffects.resources.plugin

import android.content.Context
import com.alexeyyuditsky.test.foundation.sideeffects.SideEffectMediator
import com.alexeyyuditsky.test.foundation.sideeffects.resources.Resources

class ResourcesSideEffectMediator(
    private val appContext: Context
) : SideEffectMediator<Nothing>(), Resources {

    override fun getString(resId: Int, vararg args: Any): String {
        return appContext.getString(resId, *args)
    }

}