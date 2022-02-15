package com.alexeyyuditsky.test.foundation

import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.test.foundation.navigator.IntermediateNavigator
import com.alexeyyuditsky.test.foundation.navigator.Navigator
import com.alexeyyuditsky.test.foundation.uiactions.UIActions

/** Implementation of [Navigator] and [UIActions].
 * It is based on activity view-model because instances of [Navigator] and [UIActions]
 * should be available from fragments' view-models (usually they are passed to the view-model constructor). */
class ActivityScopeViewModel(
    val uiActions: UIActions,
    val navigator: IntermediateNavigator,
) : ViewModel(), UIActions by uiActions, Navigator by navigator {

    override fun onCleared() {
        super.onCleared()
        navigator.clear()
    }

}