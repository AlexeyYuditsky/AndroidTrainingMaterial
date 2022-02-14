package com.alexeyyuditsky.test.foundation.views

import com.alexeyyuditsky.test.foundation.ActivityScopeViewModel

/** Implement this interface in the activity. */
interface FragmentsHolder {

    /** Called when activity views should be re-drawn. */
    fun notifyScreenUpdates()

    /** Get the current implementations of dependencies from activity VM scope. */
    fun getActivityScopeViewModel(): ActivityScopeViewModel

}