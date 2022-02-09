package com.alexeyyuditsky.foundation

import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.foundation.navigator.IntermediateNavigator
import com.alexeyyuditsky.foundation.navigator.Navigator
import com.alexeyyuditsky.foundation.uiactions.UIActions

const val ARG_SCREEN = "ARG_SCREEN"

/** Implementation of [Navigator] and [UIActions].
 * It is based on activity view-model because instances of [Navigator] and [UIActions]
 * should be available from fragments' view-models (usually they are passed to the view-model constructor). */
class ActivityScopeViewModel(
    val uiActions: UIActions,
    val navigator: IntermediateNavigator
) : ViewModel(), Navigator by navigator, UIActions by uiActions {

    override fun onCleared() {
        super.onCleared()
        navigator.clear()
    }

}