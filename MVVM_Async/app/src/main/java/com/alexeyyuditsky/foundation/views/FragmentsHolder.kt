package com.alexeyyuditsky.foundation.views

import com.alexeyyuditsky.foundation.ActivityScopeViewModel

/** Implement this interface in the activity. */
interface FragmentsHolder {

    /** Called when activity views should be re-drawn. */
    fun notifyScreenUpdates()

    /** Get the current implementations of dependencies from activity VM scope. */
    fun getActivityScopeViewModel(): ActivityScopeViewModel

}